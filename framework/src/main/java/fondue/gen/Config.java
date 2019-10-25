package fondue.gen;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public final class Config {

    private String appName;
    private String rootPkg;
    private PreparationMode preparationMode;
    private BackupSettings backupSettings;
    private List<Func> funcs;

    public enum PreparationMode {
        BACKUP, OVERWRITE, NONE;
    }

    public Config() {
        this.appName = "";
        this.rootPkg = "";
        this.preparationMode = PreparationMode.NONE;
        this.funcs = new ArrayList<>();
    }

    public void validate() {
        checkInput("appName", appName, true);
        checkInput("rootPkg", rootPkg, true);
        for (Func func : funcs) {
            checkInput("func.name", func.name, true);
            checkInput("func.resourceName", func.resourceName, false);
            checkInput("func.groupBy", func.groupBy, false);
        }
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        checkInput("appName", appName, true);
        this.appName = appName;
    }

    public String getRootPkg() {
        return rootPkg;
    }

    public void setRootPkg(String rootPkg) {
        checkInput("rootPkg", rootPkg, true);
        this.rootPkg = rootPkg;
    }

    public PreparationMode getPreparationMode() {
        return preparationMode;
    }

    public void setPreparationMode(PreparationMode preparationMode) {
        this.preparationMode = preparationMode;
    }

    public BackupSettings getBackupSettings() {
        return backupSettings;
    }

    public void setBackupSettings(BackupSettings backupSettings) {
        this.backupSettings = backupSettings;
    }

    public List<Func> getFuncs() {
        return funcs;
    }

    public void setFuncs(List<Func> funcs) {
        this.funcs = funcs;
    }

    @Override
    public String toString() {
        return String.format("Config(appName=%s, rootPkg=%s, preparationMode=%s, backupSettings=%s, funcs=%s)", appName,
                rootPkg, preparationMode, backupSettings, funcs);
    }

    public static final class BackupSettings {

        private String directory;
        private String subDirectoryPattern;
        private String fileNamePattern;
        private Path directoryPath;

        private void updateDirectory() {
            Path dir = Paths.get(StringUtils.defaultIfBlank(directory, "./"));
            String sub = GeneratorUtils.formatPathPatternString(subDirectoryPattern, "", System.currentTimeMillis());
            this.directoryPath = dir.resolve(sub);
        }

        public Path getDirectoryPath() {
            return directoryPath;
        }

        public String getDirectory() {
            return directory;
        }

        public void setDirectory(String directory) {
            this.directory = directory;
            updateDirectory();
        }

        public String getSubDirectoryPattern() {
            return subDirectoryPattern;
        }

        public void setSubDirectoryPattern(String subDirectoryPattern) {
            this.subDirectoryPattern = subDirectoryPattern;
            updateDirectory();
        }

        public String getFileNamePattern() {
            return fileNamePattern;
        }

        public void setFileNamePattern(String fileNamePattern) {
            this.fileNamePattern = fileNamePattern;
        }

        @Override
        public String toString() {
            return String.format("BackupSettings(directory=%s, subDirectoryPattern=%s)", directory,
                    subDirectoryPattern);
        }
    }

    public static final class Func {

        private String name;
        private String resourceName;
        private String groupBy;
        private List<Validation> validations;

        public Func() {
            this.name = "";
            this.resourceName = "";
            this.groupBy = "";
            this.validations = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            checkInput("func.name", name, true);
            this.name = name;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = StringUtils.defaultString(resourceName);
        }

        public String getGroupBy() {
            return groupBy;
        }

        public void setGroupBy(String groupBy) {
            this.groupBy = StringUtils.defaultString(groupBy);
        }

        public List<Validation> getValidations() {
            return validations;
        }

        public void setValidations(List<Validation> validations) {
            this.validations = validations;
        }

        @Override
        public String toString() {
            return String.format("Func(name=%s, resourceName=%s, groupBy=%s)", name, resourceName, groupBy);
        }
    }

    public static final class Validation {

        private String field;
        private String type;
        private String appendingAttributes;

        public Validation() {
            this.field = "";
            this.type = "";
            this.appendingAttributes = "";
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAppendingAttributes() {
            return appendingAttributes;
        }

        public void setAppendingAttributes(String appendingAttributes) {
            this.appendingAttributes = appendingAttributes;
        }

        @Override
        public String toString() {
            return String.format("Validation(field=%s, type=%s, appendingAttributes=%s)", field, type,
                    appendingAttributes);
        }
    }

    static void checkInput(String key, String value, boolean mandatory) {
        if (value == null) {
            throw new IllegalArgumentException("null value: key=" + key);
        }
        if (mandatory && StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("mandatory item: key=" + key);
        }
    }
}
