package com.kosta.myapp.controller;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kosta.myapp.repository.FreeBoardRepository;
import com.kosta.myapp.vo.CarVO;

@Controller
public class ThymeleafTestController {

	@Autowired
	FreeBoardRepository bRefo;
	
	@GetMapping("/sample1")
	public String test1(Model model) {
		model.addAttribute("score", 100);
		model.addAttribute("myname", "ja0");
		
		CarVO car = CarVO.builder()
						.carNo(1234L)
						.model("AA")
						.build();
		model.addAttribute("car", car);
		
		return "thymeleaf1"; //templates/thymeleaf1.html
	}
	
	@GetMapping("/sample2")
	public String test2(Model model, HttpSession session) {
		model.addAttribute("boardList", bRefo.findAll());
		model.addAttribute("loginUser", "ja0");
		model.addAttribute("loginUser2", "Kim");
		model.addAttribute("salary", 12345678912345F);
		
		session.setAttribute("loginUser3", "admin");
		return "thymeleaf2"; 
	}
	
	@GetMapping("/sample3")
	public String test3(Model model) {
		model.addAttribute("now", new Date());
		model.addAttribute("salary", 12345678.77F);
		model.addAttribute("message", "thyme leaf 연습");
		model.addAttribute("fruits", Arrays.asList("사과","바나나","수박"));
		
		return "thymeleaf3"; 
	}
	
	@GetMapping("/sample4")
	public String test4() {
		
		return "thymeleaf4_inherit"; 
	}
}
