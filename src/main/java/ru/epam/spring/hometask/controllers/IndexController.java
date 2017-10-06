package ru.epam.spring.hometask.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by Dmitrii_Topolnik on 10/4/2017.
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String getIndexPage(Model model){
        model.addAttribute("msg","hello world!");
        return "index";
    }
}
