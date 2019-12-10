package fondue.fw;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.springframework.ui.Model;

public final class ViewMessageAddableTest {

    @Test
    public void testAddErrorViewMessage() {
        Model m = null;
        V v = new V();
        v.addWarningViewMessage(m, "WARNING:");
        v.addSuccessViewMessage(m, "ok");
        assertEquals(2, v.list.size());
        assertEquals(MessageType.WARNING, v.list.get(0).getLeft());
        assertEquals("WARNING:", v.list.get(0).getRight());
        assertEquals(MessageType.SUCCESS, v.list.get(1).getLeft());
        assertEquals("ok", v.list.get(1).getRight());
        v.addNoticeViewMessage(m, "hello");
        v.addErrorViewMessage(m, "error occurred");
        assertEquals(4, v.list.size());
        assertEquals(MessageType.WARNING, v.list.get(0).getLeft());
        assertEquals("WARNING:", v.list.get(0).getRight());
        assertEquals(MessageType.SUCCESS, v.list.get(1).getLeft());
        assertEquals("ok", v.list.get(1).getRight());
        assertEquals(MessageType.NOTICE, v.list.get(2).getLeft());
        assertEquals("hello", v.list.get(2).getRight());
        assertEquals(MessageType.ERROR, v.list.get(3).getLeft());
        assertEquals("error occurred", v.list.get(3).getRight());
    }

    @Test
    public void testAddWarningViewMessage() {
        // omit
    }

    @Test
    public void testAddSuccessViewMessage() {
        // omit
    }

    @Test
    public void testAddNoticeViewMessage() {
        // omit
    }

    @Test
    public void testAddViewMessage() {
    }

    @Test
    public void testAddRedirectMessage() {
    }

    public static final class V implements ViewMessageAddable {

        List<Pair<MessageType, String>> list = new ArrayList<>();

        @Override
        public void addViewMessage(Model model, MessageType type, String message) {
            list.add(Pair.of(type, message));
        }
    }
}
