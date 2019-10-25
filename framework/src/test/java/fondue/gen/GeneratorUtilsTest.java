package fondue.gen;

import static fondue.gen.GeneratorUtils.formatPathPatternString;
import static org.junit.Assert.assertEquals;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;

public final class GeneratorUtilsTest {

    static String f(String s) {
        return GeneratorUtils.convertToPluralForm(s);
    }

    @Test
    public void testConvertToPluralForm() {
        assertEquals("knives", f("knife"));
        assertEquals("leaves", f("leaf"));
        assertEquals("boxes", f("box"));
        assertEquals("watches", f("watch"));
        assertEquals("addresses", f("address"));
        assertEquals("countries", f("country"));
        assertEquals("properties", f("property"));
        assertEquals("properties", f("property"));
        assertEquals("ways", f("way"));
        assertEquals("monkeys", f("monkey"));
        assertEquals("toys", f("toy"));
    }

    @Test
    public void testGenerateFormattedPathString() {
        final String yyyyMMdd = FastDateFormat.getInstance("yyyyMMdd").format(System.currentTimeMillis());
        assertEquals("x_" + yyyyMMdd, formatPathPatternString("%1$s_%2$tY%2$tm%2$td", "x"));
        assertEquals("x_" + yyyyMMdd, formatPathPatternString("{0}_{1,Date,yyyyMMdd}", "x"));
    }
}
