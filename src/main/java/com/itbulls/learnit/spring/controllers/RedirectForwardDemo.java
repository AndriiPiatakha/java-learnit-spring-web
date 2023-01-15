package com.itbulls.learnit.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/test")
public class RedirectForwardDemo {

	@RequestMapping("/redirect-prefix")
	public String redirectWithPrefix() {
		return "redirect:/test/redirect-demo";
	}

	@RequestMapping("/redirect-demo")
	public String redirecDemo() {
		return "redirect-demo";
	}

	@RequestMapping("/redirect-google")
	public String redirectWithPrefixToAbsoluteUrl() {
		return "redirect:https://google.com";
	}

	@RequestMapping("/redirect-model")
	public ModelAndView redirectModel(ModelMap modelMap) {
		modelMap.addAttribute("testParam", "Test parameter");
		return new ModelAndView("redirect:/test/redirect-demo", modelMap);
	}

	@RequestMapping("/redirect-with-redirectview")
	public RedirectView redirectWithRedirectView(RedirectAttributes attributes) {
		attributes.addAttribute("testParam", "Test Parameter");
		return new RedirectView("/test/redirect-demo");
	}
	
	@RequestMapping("/redirect-with-flashattr")
	public String redirectWithFlashAttr(RedirectAttributes attributes) {
		attributes.addFlashAttribute("flashAttr", "Flash Attribute");
		attributes.addAttribute("testParam", "Test Parameter");
		return "redirect:/test/redirect-flash";
	}
	
	@RequestMapping("/redirect-flash")
	public String redirectFlash(Model model) {
		System.out.println(model.getAttribute("flashAttr"));
		return "redirect-demo";
	}
	
	@RequestMapping("/forward")
	public String forwardDemo(ModelMap modelMap) {
		modelMap.addAttribute("testParam", "Test parameter");
		return "forward:/test/redirect-demo";
	}
	
}
