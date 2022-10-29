package com.itbulls.learnit.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itbulls.learnit.spring.model.UserModel;
import com.itbulls.learnit.spring.model.UserModelValid;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Controller
@RequestMapping("/test")
public class UserController {

	
	@RequestMapping("/user-registration-form")
	public String userReegistrationForm(Model model) {
		model.addAttribute("user", new UserModel());
		return "userRegistrationForm";
	}
	
	@RequestMapping("/create-user")
	public String createUser(@ModelAttribute("user") UserModel u) {
		// store user into the database or perform other business valuable operation
		System.out.println(u);
		return "registrationConfirmation";
	}
	
	
	@RequestMapping("/user-registration-form-with-validation")
	public String userReegistrationFormWithValidation(Model model) {
		model.addAttribute("user", new UserModelValid());
		return "userRegistrationFormWithValidation";
	}
	
	@RequestMapping("/create-user-wtih-validation")
	public String createUserWithValidation(@Valid @ModelAttribute("user") UserModelValid u, 
			BindingResult br) {
		System.out.println("Are there any data binding errors? " + br.hasErrors());
		
		
//		// ================= EXAMPLE of BindingResult API ====================
//		
//		List<FieldError> fieldErrors = br.getFieldErrors();
//		for (FieldError fieldError : fieldErrors) {
//			System.out.println(fieldError.getDefaultMessage());
//		}
//		
//		// ================= EXAMPLE of Programmatic way to validate object ====================
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		Validator validator = factory.getValidator();
//		Set<ConstraintViolation<UserModelValid>> violations = validator.validate(u);
//		for (ConstraintViolation<UserModelValid> constraintViolation : violations) {
//			System.out.println(constraintViolation.getMessage());
//		}
		
		
		if (br.hasErrors()) {
			return "userRegistrationFormWithValidation";
		} else {
			return "registrationConfirmation";
		}
		
	}
}
