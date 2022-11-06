package com.example.turnitup.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class UploadFrontEndController {

    @GetMapping(value = "upload")
    ModelAndView fileUpload() {
        return new ModelAndView("index.html");
    }
}
