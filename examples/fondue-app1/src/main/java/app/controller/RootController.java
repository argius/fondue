package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public final class RootController {

    @GetMapping("/")
    public String goToTopPage() {
        return "index";
    }
}
