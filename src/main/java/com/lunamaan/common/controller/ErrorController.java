package com.lunamaan.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

	@RequestMapping("/{code}")
	public String errPage(@PathVariable("code") String code, Model model){
		model.addAttribute("code", code);
		
		return "common/error";
	}
	
}
