package controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
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
	public String form(LoginRequest loginRequest, @CookieValue(value = "REMEMBER", required = false)Cookie rCookie) {
		if (rCookie != null) {
			loginRequest.setEmail(rCookie.getValue());
			loginRequest.setRememberEmail(true);
		}
		return "login/loginForm";
	}

	@PostMapping
	public String submit(LoginRequest loginRequest, Errors errors, HttpSession session, HttpServletResponse response) {
		if (errors.hasErrors()) {
			return "login/loginForm";
		}
		try {
			AuthInfo authInfo = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
			session.setAttribute("authInfo", authInfo);
			
			Cookie rememberCookie = new Cookie("REMEMBER", loginRequest.getEmail());
			rememberCookie.setPath("/");
			if (loginRequest.isRememberEmail()) {
				rememberCookie.setMaxAge(60 * 60 * 24 * 30);
			}else {
				rememberCookie.setMaxAge(0);
			}
			response.addCookie(rememberCookie);
				
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
