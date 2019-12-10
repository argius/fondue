package fondue.gen;

import static fondue.gen.GeneratorUtils.convertToPluralForm;
import static fondue.gen.GeneratorUtils.formatPathPatternString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;

public final class GeneratorUtilsTest {

    @Test
    public void testConvertToPluralForm() {
        assertEquals("knives", convertToPluralForm("knife"));
        assertEquals("leaves", convertToPluralForm("leaf"));
        assertEquals("boxes", convertToPluralForm("box"));
        assertEquals("watches", convertToPluralForm("watch"));
        assertEquals("addresses", convertToPluralForm("address"));
        assertEquals("countries", convertToPluralForm("country"));
        assertEquals("properties", convertToPluralForm("property"));
        assertEquals("properties", convertToPluralForm("property"));
        assertEquals("ways", convertToPluralForm("way"));
        assertEquals("monkeys", convertToPluralForm("monkey"));
        assertEquals("toys", convertToPluralForm("toy"));
        assertEquals("children", convertToPluralForm("child"));
        assertEquals("mice", convertToPluralForm("mouse"));
        assertEquals("men", convertToPluralForm("man"));
        assertEquals("women", convertToPluralForm("woman"));
    }

    @Test
    public void testFormatPathPatternString() {
        final String yyyyMMdd = FastDateFormat.getInstance("yyyyMMdd").format(System.currentTimeMillis());
        assertEquals("x_" + yyyyMMdd, formatPathPatternString("%1$s_%2$tY%2$tm%2$td", "x"));
        assertEquals("_aB,#_", formatPathPatternString("_%1$s,%3$d_", "aB").replaceFirst("\\d+", "#"));
        assertEquals("x_" + yyyyMMdd, formatPathPatternString("{0}_{1,Date,yyyyMMdd}", "x"));
        assertEquals("_Az_", formatPathPatternString("_{0}_", "Az"));
        assertEquals("_Az.#_", formatPathPatternString("_{0}.{2}_", "Az").replaceFirst("\\d+", "#"));
        // irregular patterns
        assertEquals("1%s9", formatPathPatternString("1%s9", "Az"));
        assertEquals("z$w", formatPathPatternString("z$w", "Az"));
        assertEquals("1t3", formatPathPatternString("1t3", "Az"));
        assertEquals("1t3{0", formatPathPatternString("1t3{0", "Az"));
        assertEquals("1}t30", formatPathPatternString("1}t30", "Az"));
        assertEquals("", formatPathPatternString("", ""));
    }

    @Test
    public void testIsValidPackageName() {
        assertFalse(GeneratorUtils.isValidPackageName(null));
        assertTrue(GeneratorUtils.isValidPackageName("net.argius.webapp"));
        assertFalse(GeneratorUtils.isValidPackageName(".app"));
        assertFalse(GeneratorUtils.isValidPackageName("0app"));
        assertTrue(GeneratorUtils.isValidPackageName("app0"));
        assertTrue(GeneratorUtils.isValidPackageName("app._myPackage"));
    }
}
