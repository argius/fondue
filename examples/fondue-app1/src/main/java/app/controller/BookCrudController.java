package app.controller;

import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import fondue.fw.ControllerBase;
import fondue.fw.MessageType;
import fondue.fw.PA;
import fondue.fw.PP;
import app.model.Book;
import app.service.BookCrudService;

@Controller
@RequestMapping("books")
public final class BookCrudController implements ControllerBase {

    private static final Logger log = LoggerFactory.getLogger(BookCrudController.class);

    private static final String RESOURCES = "books";

    @Autowired
    protected MessageSource msg;

    @Autowired
    private BookCrudService service;

    @GetMapping("list")
    public String getAllResources(@PA PP page, Model model) {
        page.setCountPerPage(3);
        model.addAttribute(RESOURCES, service.getBooks(page));
        page.validateCurrentPage();
        return templatePath(RESOURCES, "list");
    }

    @GetMapping("get/{id}")
    public String getResource(@PathVariable long id, Model model) {
        model.addAttribute("item", service.getBook(id));
        return templatePath(RESOURCES, "detail");
    }

    @GetMapping("new")
    public String showCreateForm(Model model) {
        model.addAttribute("form", new BookForm());
        return templatePath(RESOURCES, "edit");
    }

    @PostMapping("create")
    public String requestCreateResource(@ModelAttribute("form") @Valid BookForm form, BindingResult bindingResult,
                                        Model model, RedirectAttributes ra, Locale locale) {
        log.debug("form: {}", form);
        if (bindingResult.hasErrors()) {
            addErrorViewMessage(model, message(msg, locale, "message.validationError"));
            return templatePath(RESOURCES, "edit");
        }
        Book o = service.create(form.toModel());
        addRedirectMessage(ra, MessageType.SUCCESS, message(msg, locale, "message.created", o.getId()));
        return redirectPath(RESOURCES, "list");
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable long id, Model model) {
        BookForm form = new BookForm();
        form.applyModel(service.getBook(id));
        model.addAttribute("form", form);
        return templatePath(RESOURCES, "edit");
    }

    @PostMapping("update/{id}")
    public String requestUpdateResource(@PathVariable long id, @ModelAttribute("form") @Valid BookForm form, BindingResult bindingResult,
                                        Model model, RedirectAttributes ra, Locale locale) {
        log.debug("form: {}", form);
        if (bindingResult.hasErrors()) {
            addErrorViewMessage(model, message(msg, locale, "message.validationError"));
            return templatePath(RESOURCES, "edit");
        }
        Book o = form.toModel();
        o.setId(id);
        service.update(o);
        addRedirectMessage(ra, MessageType.SUCCESS, message(msg, locale, "message.updated", id));
        return redirectPath(RESOURCES, "get", String.valueOf(id));
    }

    @GetMapping("delete/{id}")
    public String requestDeleteResource(@PathVariable long id, RedirectAttributes ra, Locale locale) {
        service.delete(id);
        addRedirectMessage(ra, MessageType.SUCCESS, message(msg, locale, "message.deleted", id));
        return redirectPath(RESOURCES, "list");
    }

    // for API
    @GetMapping()
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Book> list(@RequestBody Book body) {
        return service.getBooks();
    }

    // for API
    @GetMapping("{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Book get(@PathVariable long id) {
        return service.getBook(id);
    }

    // for API
    @PostMapping()
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Book post(@RequestBody Book body) {
        return service.create(body);
    }

    // for API
    @PutMapping("{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Book put(@PathVariable long id, @RequestBody Book body) {
        body.setId(id);
        service.update(body);
        return service.getBook(id);
    }

    // for API
    @DeleteMapping("{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
