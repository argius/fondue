package fondue.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class HelpCommand {

    private Properties props;

    private HelpCommand() {
        this.props = new Properties();
        try (InputStream is = getClass().getResourceAsStream("messages.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    void runCommand() {
        System.out.println(props.getProperty("help.usage"));
    }

    public static void main(String[] args) {
        HelpCommand cmd = new HelpCommand();
        cmd.runCommand();
    }
}
