package fondue.gen;

@Deprecated
public final class ModelFromMyBatisGenerator {

    private ModelFromMyBatisGenerator() {
    }

    public static void generate() {
        final String configPath = "config/MyBatisGeneratorConfig.xml";
        final String[] cmdArgs = { "-configfile", configPath, "-overwrite" };
        System.out.println("fondue genModel: Generating Model files by MyBatis Generator ...");
        org.mybatis.generator.api.ShellRunner.main(cmdArgs);
        System.out.println("fondue genModel: Finished.");
    }
}
