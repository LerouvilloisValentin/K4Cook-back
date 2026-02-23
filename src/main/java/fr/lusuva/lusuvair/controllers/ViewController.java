package fr.k4cook.K4Cook.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The ViewController class handles web requests and returns the name of the view to be rendered.
 */
@Controller
public class ViewController {
    
    /**
     * Handles HTTP GET requests for the root URL ("/").
     *
     * @return the name of the view to be rendered, which is "index"
     */
    @GetMapping("/")
    public String getView() {
        return "index";
    }
}
