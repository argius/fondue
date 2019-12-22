package fondue.fw;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import org.junit.Test;

public final class FormMethodsTest {

    static final Class<?> C = Object.class;

    @Test
    public void testConvertFrom() {
        Form f = new Form();
        assertEquals("", f.convertFrom(C, null));
        assertEquals("1297", f.convertFrom(C, 1297));
        assertEquals("Az*00", f.convertFrom(C, new StringBuffer("Az").append("*00")));
        assertEquals("3.8", f.convertFrom(C, BigDecimal.valueOf(3.8d)));
        assertEquals("webapp test", f.convertFrom(C, "webapp test"));
        assertEquals("20100725061130", f.convertFrom(C, new Date(1280005890000L)));
        assertEquals("Optional[X]", f.convertFrom(C, Optional.of("X")));
    }

    @Test
    public void testConvertTo() {
        Form f = new Form();
        assertNull(f.convertTo(C, null));
        assertEquals("AZ", f.convertTo(String.class, "AZ"));
        assertEquals(Short.valueOf((short) 98), f.convertTo(Short.class, "98"));
        assertEquals(null, f.convertTo(Short.class, " "));
        assertEquals(null, f.convertTo(Short.class, "X"));
        assertEquals(Integer.valueOf(135), f.convertTo(Integer.class, "135"));
        assertEquals(null, f.convertTo(Integer.class, " "));
        assertEquals(null, f.convertTo(Integer.class, "X"));
        assertEquals(Long.valueOf(99999999999L), f.convertTo(Long.class, "99999999999"));
        assertEquals(null, f.convertTo(Long.class, " "));
        assertEquals(Timestamp.valueOf("2019-12-22 12:34:56"), f.convertTo(Timestamp.class, "20191222123456"));
        assertEquals(null, f.convertTo(Timestamp.class, " "));
        assertEquals(null, f.convertTo(Class.class, " "));
    }

    @Test
    public void testToString() {
        Form f = new Form();
        String x = f.toString().replaceFirst("Form\\@[0-9a-f]+", "#");
        assertEquals("fondue.fw.FormMethodsTest$#[id=,disabled=]", x);
    }

    public static final class Form implements FormMethods {
        private String id;
        private String disabled;
        public Form() {
            this.id = "";
            this.disabled = "";
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getDisabled() {
            return disabled;
        }
        public void setDisabled(String disabled) {
            this.disabled = disabled;
        }
        @Override
        public String toString() {
            return toString(this);
        }
    }

}
