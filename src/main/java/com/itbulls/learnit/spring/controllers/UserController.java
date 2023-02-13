package com.itbulls.learnit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itbulls.learnit.spring.model.UserModel;
import com.itbulls.learnit.spring.model.UserModelValid;
import com.itbulls.learnit.spring.persistence.entities.User;
import com.itbulls.learnit.spring.persistence.repositories.RoleRepository;
import com.itbulls.learnit.spring.persistence.repositories.UserRepository;
import com.itbulls.learnit.spring.security.SetupDataLoader;

import java.util.Arrays;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;

	
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
	
	// ======= USER REGISTRATION for Spring Security Demo
	
	@RequestMapping("/user-registration-form-security-demo")
	public String userReegistrationSecurityDemo(Model model) {
		model.addAttribute("user", new UserModelValid());
		return "userRegistrationSecurityDemo";
	}
	
	@RequestMapping("/create-user-security-demo")
	public String registerNewUserAccount(@Valid @ModelAttribute("user") UserModel userModel, 
			BindingResult br) {
		
		if (userRepository.findByEmail(userModel.getEmail()) != null) {
	        throw new RuntimeException
	          ("User with such email already exists: " + userModel.getEmail());
	    }
	    User user = new User();

	    user.setFirstName(userModel.getFirstName());
	    user.setLastName(userModel.getLastName());
	    user.setPassword(passwordEncoder.encode(userModel.getPassword()));
	    user.setEmail(userModel.getEmail());
	    user.setEnabled(true);
	    user.setRoles(Arrays.asList(roleRepository.findByName(SetupDataLoader.ROLE_USER)));
	    userRepository.save(user);
	    return "redirect:/test/login_page";
	}
}
