package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springTest.DuplicateMemberException;
import springTest.MemberRegisterService;
import springTest.RegisterRequest;

@Controller
public class RegisterController {
	@Autowired
	private MemberRegisterService memberRegisterService;
	
	@RequestMapping("/register/step1")
	public String handleStep1() {
		return "register/step1";
	}
	@PostMapping("/register/step2")
	public String handleStep2(@RequestParam(value = "agree" ,defaultValue = "false") boolean agree,  Model model) {
		if (!agree) {
			return "register/step1";
		}
		model.addAttribute("formData", new RegisterRequest());
		return "register/step2";
	}
	
	@PostMapping("/register/step3")
	public String handleStep3(@ModelAttribute("formData") RegisterRequest regReq) {
		try {
			memberRegisterService.regist(regReq);
			return "/register/step3";
		} catch (DuplicateMemberException ex) {
			// TODO: handle exception
			return "/register/step2";
		}
		
	}
}
