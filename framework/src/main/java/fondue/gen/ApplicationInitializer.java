package fondue.gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.helpers.NOPLogger;
import minestra.file.PathString;
import minestra.text.LetterCaseConverter;

public final class ApplicationInitializer {

    private static final String DEFAULT_ROOT_PKG = "app";
 
    private static Properties msg = initMsg();

    private Config config;
    private Path rootDir;

    public static void main(String[] args) {
        if (args.length >= 1) {
            Optional<String> optAppName = Optional.of(args[0]);
            String arg1 = (args.length >= 2) ? args[1] : "";
            generate(optAppName, Paths.get(arg1));
        } else {
            generate(Optional.empty(), Paths.get(""));
        }
    }

    private static Properties initMsg() {
        Properties props = new Properties();
        Class<?> c = ApplicationInitializer.class;
        try (InputStream is = c.getResourceAsStream("messages.properties")) {
            if (is != null) {
                props.load(is);
            }
            return props;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public ApplicationInitializer(String appName) {
        this(appName, Paths.get(""));
    }

    public ApplicationInitializer(String appName, Path rootDir) {
        this.config = new Config();
        this.rootDir = rootDir;
        config.setAppName(appName);
        config.setRootPkg(getRootPkgFromTaskCaller());
    }

    static String getRootPkgFromTaskCaller() {
        final String key = "fondue.rootPkg";
        final String x = Optional.ofNullable(System.getenv(key)).orElseGet(() -> System.getProperty(key, ""));
        if (StringUtils.isNotBlank(x)) {
            if (GeneratorUtils.isValidPackageName(x)) {
                return x;
            } else {
                throw new IllegalArgumentException("rootPkg=" + x);
            }
        }
        return DEFAULT_ROOT_PKG;
    }

    public static void generate(Optional<String> optAppName, Path rootDir) {
        try {
            System.out.println("fondue init: Initialize project folder ...");
            final String appName;
            if (optAppName.isPresent()) {
                appName = optAppName.get();
            } else {
                appName = PathString.name(rootDir.toAbsolutePath().getParent());
            }
            ApplicationInitializer initializer = new ApplicationInitializer(appName);
            initializer.execute();
            System.out.printf("%n%nfondue init: This project folder initialized.%n");
            System.out.println(msg.get("initApp.preparingGuide"));
            System.out.println("fondue init: Finished.");
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println();
            System.out.println("fondue init: failed to initialize project folder.");
            System.exit(-1);
        }
    }

    public void execute() throws IOException {
        // check whether config folder is empty
        Path configDir = rootDir.resolve("config");
        if (!Files.exists(configDir)) {
            Files.createDirectory(configDir);
        }
        if (Files.list(configDir).filter(x -> !x.startsWith(".")).count() > 0) {
            throw new IllegalStateException("folder 'config' is not empty");
        }
        System.out.println("  create directories");
        for (String dir : Arrays.asList("src/main/java", "src/main/resources", "src/test/java", "src/test/resources")) {
            Path path = rootDir.resolve(dir);
            if (!Files.exists(path)) {
                System.out.print("  >>> ");
                Path p = Files.createDirectories(path);
                System.out.println("dir: " + p.toAbsolutePath());
            }
        }
        System.out.println("  create config files");
        VelocityContext ctx = createVelocityContext(config);
        for (String typeId : Arrays.asList("gen-yaml", "MyBatisGeneratorConfig", "Application.java", "Config.java")) {
            System.out.print("  >>> ");
            File f = generateFile(ctx, typeId);
            System.out.println("file: " + f.getAbsolutePath());
        }
        final Class<?> c = getClass();
        List<Throwable> errors = new ArrayList<>();
        System.out.println("  create java and resource files");
        Stream.of("src/main/resources/application.yml", "src/main/resources/messages.properties",
                "src/main/resources/templates/layout.html", "src/main/resources/templates/pagination.html",
                "src/main/resources/templates/fragments.html", "src/main/resources/static/css/style.css",
                "src/main/resources/static/js/common.js").forEach(x -> {
                    System.out.print("  >>> ");
                    Path path = Paths.get(x);
                    String name = PathString.name(path);
                    String additionalExtension = (name.endsWith(".java")) ? ".txt" : "";
                    try (InputStream is = c.getResourceAsStream("/fondue/init/" + name + additionalExtension)) {
                        if (is == null) {
                            System.out.println("resouce not found: file=" + name);
                        } else {
                            GeneratorUtils.makeDirectoriesIfParentNotExists(path);
                            Files.copy(is, path);
                            System.out.println("file: " + path.toAbsolutePath());
                        }
                    } catch (Throwable th) {
                        errors.add(th);
                    }
                });
        if (!errors.isEmpty()) {
            String s = errors.stream().map(Object::toString).collect(Collectors.joining(", "));
            throw new RuntimeException(s);
        }
    }

    File generateFile(VelocityContext ctx, String templateTypeId) {
        final File f;
        switch (templateTypeId) {
        case "gen-yaml":
            f = new File("config", "fondue-gen.yml");
            break;
        case "MyBatisGeneratorConfig":
            f = new File("config", "MyBatisGeneratorConfig.xml");
            break;
        case "Application.java":
        case "Config.java":
            File dir = new File("src/main/java", config.getRootPkg().replace('.', '/'));
            f = new File(dir, templateTypeId);
            break;
        default:
            throw new IllegalArgumentException("typeId: " + templateTypeId);
        }
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        Velocity.setProperty(VelocityEngine.RUNTIME_LOG_INSTANCE, NOPLogger.NOP_LOGGER);
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

    static VelocityContext createVelocityContext(Config config) {
        VelocityContext ctx = new VelocityContext();
        ctx.put("config", config);
        ctx.put("caseCtrl", LetterCaseConverter.class);
        return ctx;
    }
}
