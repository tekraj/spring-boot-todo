package com.tekraj.todo.controller;

import java.util.Map;

import org.springframework.boot.web.servlet.error.ErrorAttributes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.ui.Model;


@Controller
public class HomeController {
	  private final ErrorAttributes errorAttributes;

	    public HomeController(ErrorAttributes errorAttributes) {
	        this.errorAttributes = errorAttributes;
	    }
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/error")
    public String handleError(WebRequest webRequest, Model model) {
        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(webRequest, null);
        model.addAttribute("status", errorDetails.get("status"));
        model.addAttribute("error", errorDetails.get("error"));
        model.addAttribute("message", errorDetails.get("message"));
        return "error"; 
    }
}