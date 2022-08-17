package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.MemberDao;
import springTest.Member;
import springTest.UserListRequest;

@Controller
public class MemberListController {
	
	@Autowired
	private MemberDao memberDao;
	
	@GetMapping("/members")
	public String listView(@ModelAttribute("cmd") UserListRequest userListRequest) {
		return "member/memberList";
	}
	@PostMapping("/members")
	public String listSerch(@ModelAttribute("cmd") UserListRequest userListRequest, Errors errors, Model model) {
		if (errors.hasErrors()) {
			return "member/memberList";
		}
		if (userListRequest.getFrom() != null && userListRequest.getTo() != null) {
			List<Member> members = memberDao.selectByRegDate(
					userListRequest.getFrom(), userListRequest.getTo());
			model.addAttribute("members", members);
		}
		return "member/memberList";
	}
}
