package fondue.gen;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

public final class GeneratorUtils {

    private static final AtomicInteger seq = new AtomicInteger(0);

    private GeneratorUtils() {
    }

    static Config loadConfig() {
        try {
            Yaml yaml = new Yaml();
            Config config;
            try (InputStream is = new FileInputStream("config/fondue-gen.yml")) {
                config = yaml.loadAs(is, Config.class);
                config.validate();
            }
            System.out.println("Dump Config: " + config);
            return config;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @SafeVarargs
    static <T> T select(Predicate<T> pred, Supplier<T> defaultsSupplier, Supplier<T>... suppliers) {
        for (Supplier<T> supplier : suppliers) {
            T v = supplier.get();
            if (pred.test(v)) {
                return v;
            }
        }
        return defaultsSupplier.get();
    }

    /**
     * Formats path pattern.
     * @param ptn format string
     * @param p1 parameter1 (file name)
     * @return formatted string
     */
    static String formatPathPatternString(String ptn, String p1) {
        return formatPathPatternString(ptn, p1, System.currentTimeMillis());
    }

    /**
     * Formats path pattern.
     * @param ptn format string
     * @param p1 parameter1 (file name)
     * @param p2 timestamp (millis)
     * @return formatted string
     */
    static String formatPathPatternString(String ptn, String p1, long p2) {
        if (StringUtils.isNotBlank(ptn)) {
            if (ptn.contains("$") && ptn.contains("%")) {
                int p3 = ptn.contains("3$") ? getNextSequencialNumberInProcess() : 0;
                return String.format(ptn, p1, p2, p3);
            }
            if (ptn.contains("{") && ptn.contains("}")) {
                int p3 = ptn.contains("{2") ? getNextSequencialNumberInProcess() : 0;
                return MessageFormat.format(ptn, p1, p2, p3);
            }
            return ptn;
        }
        return "";
    }

    /**
     * Returns a generated sequencial number.
     * @return generated sequencial number
     */
    static int getNextSequencialNumberInProcess() {
        return seq.incrementAndGet();
    }

    /**
     * Converts the singular form of the word to plural form.
     * Not supported for some illegular pattarns.
     * @param word singular form of the word
     * @return plural form of the word
     */
    static String convertToPluralForm(String word) {
        if (word.matches(".*(s|x|z|sh|ch)")) {
            return word + "es";
        } else if (word.matches(".*[^aiueo]y")) {
            return word.replaceFirst("y$", "ies");
        }

        // irregular patterns
        if (word.equals("child")) {
            return "children";
        }
        if (word.equals("mouse")) {
            return "mice";
        }
        if (word.equals("man")) {
            return "men";
        }
        if (word.equals("woman")) {
            return "women";
        }
        if (word.equals("knife")) {
            return "knives";
        }
        if (word.equals("leaf")) {
            return "leaves";
        }
        return word + "s";
    }
}
