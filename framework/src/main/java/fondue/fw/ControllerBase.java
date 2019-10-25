package fondue.fw;

import java.util.Locale;
import org.springframework.context.MessageSource;

public interface ControllerBase extends ViewMessageAddable {

    default String templatePath(String... parts) {
        return String.join("/", parts);
    }

    default String redirectPath(String... parts) {
        return "redirect:/" + String.join("/", parts);
    }

    default String message(MessageSource m, Locale locale, String format, Object... args) {
        return m.getMessage(format, args, locale);
    }
}
