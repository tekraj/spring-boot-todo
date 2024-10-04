package com.tekraj.schoolmanagement.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	
	
	@GetMapping({"","/"})
	
	public String dashboard(Model model) {
		model.addAttribute("title","Admin");
		return "admin/index";
	}


    
}
