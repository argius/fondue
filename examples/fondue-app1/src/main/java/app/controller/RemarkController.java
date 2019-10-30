package app.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import app.model.Remark;
import app.service.RemarkService;
import fondue.fw.ControllerBase;
import fondue.fw.PA;
import fondue.fw.PP;

// This is not a generted file.
@Controller
@RequestMapping("remarks")
public final class RemarkController implements ControllerBase {

    private static final Logger log = LoggerFactory.getLogger(RemarkController.class);

    private static final String RESOURCES = "remarks";

    @Autowired
    protected MessageSource msg;

    @Autowired
    private RemarkService service;

    @GetMapping("timeline")
    public String showTimeline(@PA PP page, Model model) {
        return templatePath(RESOURCES, "timeline");
    }

    // for API
    @GetMapping("timeline/latest")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Remark> getLatestUpdated() {
        // latest 10
        List<Remark> a = service.getLatestUpdate(10);
        Collections.reverse(a);
        return a;
    }

    // for API
    @GetMapping("timeline/updated/{t}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Remark> getUpdated(@PathVariable long t) {
        log.debug("t=" + t + "/ " + new Date(t));
        return service.getUpdatedAfter(t);
    }
}
