package com.kosta.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kosta.myapp.repository.PDSBoardRepository;

@Controller
public class JSPTestController {

	@Autowired
	PDSBoardRepository repo;

	@GetMapping(value = "/hello")
	public String test1(Model model) {
		
		model.addAttribute("blist", repo.findAll());
		return "1";
	}
}
