package fondue.fw;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

public final class ViewMessageAddableTest {

    @Test
    public void testAddErrorViewMessage() {
        Model m = null;
        MockViewMessageAddable v = new MockViewMessageAddable();
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
        V v = new V();
        Model m = new ExtendedModelMap();
        Supplier<Object> f = () -> m.getAttribute("messages");
        v.addViewMessage(m, MessageType.SUCCESS, "succeeded");
        assertEquals(ViewMessage.ofSuccess("succeeded"), at(0, f.get()));
        assertEquals(1, size(f.get()));
        v.addViewMessage(m, MessageType.ERROR, "error occurred");
        assertEquals(ViewMessage.ofSuccess("succeeded"), at(0, f.get()));
        assertEquals(ViewMessage.ofError("error occurred"), at(1, f.get()));
        assertEquals(2, size(f.get()));
        m.asMap().put("messages", "000");
        v.addViewMessage(m, MessageType.NOTICE, "notify");
        assertEquals("000", at(0, f.get()));
        assertEquals(ViewMessage.ofNotice("notify"), at(1, f.get()));
        assertEquals(2, size(f.get()));
        v.addViewMessage(m, MessageType.WARNING, "warn");
        assertEquals(ViewMessage.ofWarning("warn"), at(2, f.get()));
        assertEquals(3, size(f.get()));
        // unexpected
        Model m0 = new ExtendedModelMap();
        m0.asMap().put("messages", Collections.emptyList());
        v.addViewMessage(m0, MessageType.SUCCESS, "succeeded");
        assertEquals("java.lang.UnsupportedOperationException", at(0, m0.getAttribute("messages")));
    }

    @Test
    public void testAddRedirectMessage() {
        V v = new V();
        RedirectAttributes m = new RedirectAttributesModelMap();
        Supplier<Object> f = () -> m.getFlashAttributes().get("messages");
        v.addRedirectMessage(m, MessageType.SUCCESS, "succeeded");
        assertEquals(ViewMessage.ofSuccess("succeeded"), at(0, f.get()));
        assertEquals(1, size(f.get()));
        v.addRedirectMessage(m, MessageType.ERROR, "error occurred");
        assertEquals(ViewMessage.ofSuccess("succeeded"), at(0, f.get()));
        assertEquals(ViewMessage.ofError("error occurred"), at(1, f.get()));
        assertEquals(2, size(f.get()));
        m.addFlashAttribute("messages", "000");
        v.addRedirectMessage(m, MessageType.NOTICE, "notify");
        assertEquals("000", at(0, f.get()));
        assertEquals(ViewMessage.ofNotice("notify"), at(1, f.get()));
        assertEquals(2, size(f.get()));
        v.addRedirectMessage(m, MessageType.WARNING, "warn");
        assertEquals(ViewMessage.ofWarning("warn"), at(2, f.get()));
        assertEquals(3, size(f.get()));
        // unexpected
        RedirectAttributes m0 = new RedirectAttributesModelMap();
        m0.addFlashAttribute("messages", Collections.emptyList());
        v.addRedirectMessage(m0, MessageType.SUCCESS, "succeeded");
        assertEquals("java.lang.UnsupportedOperationException", at(0, m0.getFlashAttributes().get("messages")));
    }

    static Object at(int index, Object o) {
        if (o instanceof List) {
            List<?> a = (List<?>) o;
            return a.get(index);
        }
        return o;
    }

    static int size(Object o) {
        if (o instanceof List) {
            List<?> a = (List<?>) o;
            return a.size();
        }
        return 1;
    }

    public static final class MockViewMessageAddable implements ViewMessageAddable {

        List<Pair<MessageType, String>> list = new ArrayList<>();

        @Override
        public void addViewMessage(Model model, MessageType type, String message) {
            list.add(Pair.of(type, message));
        }
    }

    public static final class V implements ViewMessageAddable {
        //
    }
}
