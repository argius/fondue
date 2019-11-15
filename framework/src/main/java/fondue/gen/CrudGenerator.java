package fondue.gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import fondue.gen.Config.BackupSettings;
import fondue.gen.Config.Func;
import fondue.gen.Config.Validation;
import minestra.text.LetterCaseConverter;

public final class CrudGenerator {

    private Config config;

    public CrudGenerator(Config config) {
        this.config = config;
    }

    public static void generateAll() {
        System.out.println("fondue genCrud: Generating Crud files ...");
        CrudGenerator gen = new CrudGenerator(GeneratorUtils.loadConfig());
        gen.generateCruds();
        System.out.println("fondue genCrud: Generated Cruds completed.");
    }

    public void generateCruds() {
        String[] templateTypeIds = { "controller", "form", "service", "serviceImpl", "list.html", "detail.html",
                "edit.html" };
        for (Func func : config.getFuncs()) {
            System.out.println("  func: " + func.getName());
            CrudBean cb = createCrudBean(func);
            VelocityContext ctx = createVelocityContext(cb);
            ctx.put("config", config);
            System.out.println("    start gen");
            for (String templateTypeId : templateTypeIds) {
                System.out.print(">>> ");
                File f = generateFile(cb, templateTypeId, ctx);
                System.out.println("file: " + f.getAbsolutePath());
            }
            System.out.println("    end gen");
        }
    }

    File generateFile(CrudBean cb, String templateTypeId, VelocityContext ctx) {
        PackageLocation rootPkg = new PackageLocation(config.getRootPkg());
        Function<String, File> javaFileResolver = subPackage -> {
            PackageLocation pkg = rootPkg.append(subPackage);
            final String target;
            switch (templateTypeId) {
            case "controller":
            case "service":
            case "serviceImpl":
                target = LetterCaseConverter.toPascalCase(cb.getResourceId() + "Crud");
                break;
            case "form":
                target = LetterCaseConverter.toPascalCase(cb.getResourceId());
                break;
            default:
                target = cb.getEntitiesId();
            }
            String fileName = target + LetterCaseConverter.toPascalCase(templateTypeId) + ".java";
            return new File(new File("src/main/java", pkg.toPath()), fileName);
        };
        final File f;
        switch (templateTypeId) {
        case "controller":
        case "form": {
            f = javaFileResolver.apply("controller");
        }
            break;
        case "service":
        case "serviceImpl": {
            f = javaFileResolver.apply("service");
        }
            break;
        case "list.html":
        case "detail.html":
        case "edit.html":
            f = new File("src/main/resources/" + "templates/" + cb.getResourcesId(), templateTypeId);
            break;
        default:
            throw new IllegalArgumentException("typeId: " + templateTypeId);
        }
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        if (f.exists()) {
            switch (config.getPreparationMode()) {
            case BACKUP:
                backupFile(config.getBackupSettings(), f);
                break;
            case NONE:
                System.out.print("  Skipped generating ");
                return f;
            default:
            }
        }
        Properties props = new Properties();
        props.setProperty(VelocityEngine.RESOURCE_LOADER, "class");
        props.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngine ve = new VelocityEngine(props);
        try (PrintWriter out = new PrintWriter(f)) {
            ve.getTemplate("/fondue/gen/" + templateTypeId + ".vm").merge(ctx, out);
            out.flush();
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
        return f;
    }

    private boolean backupFile(BackupSettings settings, File f) {
        if (settings == null) {
            throw new IllegalStateException("BackupSettings is null");
        }
        Path directoryPath = settings.getDirectoryPath();
        String fileName = GeneratorUtils.formatPathPatternString(settings.getFileNamePattern(), f.getName());
        Path p = directoryPath.resolve(fileName);
        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            Files.move(f.toPath(), p);
        } catch (IOException e) {
            System.out.printf("Warning:[%s]", e);
            return false;
        }
        System.out.print("backup " + (f.exists() ? "[NG]" : "[OK]") + " ");
        return true;
    }

    static VelocityContext createVelocityContext(CrudBean cb) {
        VelocityContext ctx = new VelocityContext();
        ctx.put("bean", cb);
        ctx.put("rootPkg", cb.getRootPkg());
        ctx.put("entity", cb.getEntityId());
        ctx.put("entities", cb.getEntitiesId());
        ctx.put("entityFqcn", cb.getEntityFqcn());
        ctx.put("resource", cb.getResourceId());
        ctx.put("resources", cb.getResourcesId());
        ctx.put("resourceClassName", LetterCaseConverter.toPascalCase(cb.getResourceId()));
        ctx.put("resourcesClassName", LetterCaseConverter.toPascalCase(cb.getResourcesId()));
        ctx.put("items", cb.getItems());
        ctx.put("caseCtrl", LetterCaseConverter.class);
        ctx.put("validationBinder", new ValidationBinder(cb.getValidations()));
        return ctx;
    }

    CrudBean createCrudBean(Func func) {
        MyBatisMapperXmlBean bean = loadMyBatisMapperXmlBean(func);
        List<MyBatisMapperXmlBean.Result> items = new ArrayList<>();
        items.add(bean.getResultMap().getId());
        items.addAll(bean.getResultMap().getResults());
        final String entityName = LetterCaseConverter.capitalize(func.getName());
        final String resourceId = LetterCaseConverter.toCamelCase(entityName);
        final String resourcesId = GeneratorUtils.select(StringUtils::isNotBlank, func::getResourceName,
                () -> GeneratorUtils.convertToPluralForm(resourceId));
        CrudBean cb = new CrudBean();
        cb.setRootPkg(config.getRootPkg());
        cb.setEntityId(entityName);
        cb.setEntitiesId(GeneratorUtils.convertToPluralForm(entityName));
        cb.setResourceId(resourceId);
        cb.setResourcesId(resourcesId);
        cb.setItems(items);
        cb.setEntityFqcn(bean.getResultMap().getType());
        cb.setValidations(func.getValidations());
        return cb;
    }

    MyBatisMapperXmlBean loadMyBatisMapperXmlBean(Func func) {
        final String entityName = LetterCaseConverter.capitalize(func.getName());
        PackageLocation rootPkg = new PackageLocation(config.getRootPkg());
        final PackageLocation pkg = rootPkg.append("dao");
        final File xmlFile = new File("src/main/resources/" + pkg.toPath(), entityName + "Mapper.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            JAXBContext jc = JAXBContext.newInstance(MyBatisMapperXmlBean.class);
            Unmarshaller u = jc.createUnmarshaller();
            JAXBElement<MyBatisMapperXmlBean> elem = u.unmarshal(doc, MyBatisMapperXmlBean.class);
            return elem.getValue();
        } catch (ParserConfigurationException | SAXException | JAXBException e) {
            throw new RuntimeException("Parsing XML Error", e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * This class provides string representation of Bean Validation's Constraints for Form template.
     */
    public static final class ValidationBinder {

        private Map<String, List<Validation>> map;

        public ValidationBinder(List<Validation> validations) {
            Map<String, List<Validation>> m = validations.stream().collect(Collectors.groupingBy(Validation::getField));
            this.map = m;
        }

        public List<String> getValidationAnnotations(String field) {
            if (map.containsKey(field)) {
                List<Validation> a = map.get(field);
                return a.stream()
                        .map(x -> String.format("@%1$s(message = \"{%2$s.%1$s}\"%3$s%4$s)", x.getType(), x.getField(),
                                StringUtils.isBlank(x.getAppendingAttributes()) ? "" : ", ",
                                x.getAppendingAttributes()))
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        }

        public boolean isEmpty() {
            return map.isEmpty();
        }
    }
}
