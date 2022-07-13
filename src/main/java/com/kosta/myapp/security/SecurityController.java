package com.kosta.myapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosta.myapp.vo.MemberDTO;

//config에 요청이있다면 RequestMapping은 필수
@Controller
public class SecurityController {

	@Autowired
	MemberService mservice;
	
	
	@RequestMapping("/hello/test")
	public String hello() {
		return "hello/hello1";
	}
	@RequestMapping("/admin/ad1")
	public void ad1() {		 
	}
	@RequestMapping("/manager/m1")
	public void m1() {		 
	}
	
	//필수 
	@GetMapping("/auth/login")
	public void login() {
		System.out.println("/auth/login   Get");
	}
	//불필요 
//	@PostMapping("/auth/login")
//	public void loginPost() {
//		System.out.println("/auth/login   post");
//	}
	@GetMapping("/auth/login2")
	public void login2() {
		
	}
	@PostMapping("/auth/login2")
	public void loginPost2() {
		System.out.println("/auth/login2  post");
		
		
		
		
		
	}
	@GetMapping("/logout")
	public void logout() {
		
	}
	// 
	@RequestMapping("/auth/loginSuccess")
	public void loginSuccess() {
		System.out.println("/auth/loginSuccess");
	}
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		System.out.println("/auth/accessDenied");
		return "auth/accessDenied";
	}
	@GetMapping("/auth/join")
	public String join() {
	  return "auth/joinForm";	
	}
	@PostMapping("/auth/joinProc")
	public String register(@ModelAttribute MemberDTO member) {
		System.out.println("===회원가입하기====");
		mservice.joinUser(member);
	  return "redirect:/auth/login";	
	}
}
