package fondue.fw;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ViewMessageAddable {

    default void addErrorViewMessage(Model model, String message) {
        addViewMessage(model, MessageType.ERROR, message);
    }

    default void addWarningViewMessage(Model model, String message) {
        addViewMessage(model, MessageType.WARNING, message);
    }

    default void addSuccessViewMessage(Model model, String message) {
        addViewMessage(model, MessageType.SUCCESS, message);
    }

    default void addNoticeViewMessage(Model model, String message) {
        addViewMessage(model, MessageType.NOTICE, message);
    }

    default void addViewMessage(Model model, MessageType type, String message) {
        final String key = "messages";
        try {
            List<Object> a;
            if (model.containsAttribute(key)) {
                Object v = model.asMap().get(key);
                if (v instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Object> aa = (List<Object>) v;
                    a = aa;
                } else {
                    a = new ArrayList<>();
                    model.addAttribute(key, a);
                    a.add(v);
                }
            } else {
                a = new ArrayList<>();
                model.addAttribute(key, a);
            }
            a.add(new ViewMessage(type, message));
        } catch (Exception e) {
            model.addAttribute(key, e.toString());
        }
    }

    default void addRedirectMessage(RedirectAttributes ra, MessageType type, String message) {
        final String key = "messages";
        try {
            List<Object> a;
            if (ra.getFlashAttributes().containsKey(key)) {
                Object v = ra.getFlashAttributes().get(key);
                if (v instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Object> aa = (List<Object>) v;
                    a = aa;
                } else {
                    a = new ArrayList<>();
                    ra.addFlashAttribute(key, a);
                    a.add(v);
                }
            } else {
                a = new ArrayList<>();
                ra.addFlashAttribute(key, a);
            }
            a.add(new ViewMessage(type, message));
        } catch (Exception e) {
            ra.addFlashAttribute(key, e.toString());
        }
    }
}
