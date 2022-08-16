package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import service.AuthService;
import springTest.AuthInfo;
import springTest.LoginRequest;
import springTest.WrongIdPasswordException;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private AuthService authService;

	@GetMapping
	public String form(LoginRequest loginRequest) {
		return "login/loginForm";
	}

	@PostMapping
	public String submit(LoginRequest loginRequest, Errors errors, HttpSession session) {
		if (errors.hasErrors()) {
			return "login/loginForm";
		}
		try {
			AuthInfo authInfo = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
			session.setAttribute("authInfo", authInfo);
			return "login/loginSuccess";
		} catch (WrongIdPasswordException e) {
			// TODO: handle exception
			errors.reject("idPasswordNotMatching");
			return "login/loginForm";
		}
	}

	@InitBinder
	void initBinder(WebDataBinder binder) {
		binder.setValidator(new LoginCommandValidator());
	}
}
