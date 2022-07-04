package com.kosta.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosta.myapp.repository.WebBoardRepository;
import com.kosta.myapp.repository.WebReplyRepository;

@Controller
@RequestMapping("/board/*")
public class WebBoardController {

	@Autowired
	WebBoardRepository bRepo;
	
	@Autowired
	WebReplyRepository rRepo;
	
	@GetMapping("/webBoardList.do")
	public String boardList(Model model) {
		model.addAttribute("boardList", bRepo.findAll());
		return "board/boardList";
	}
	
	@GetMapping("/replyByBoard.do")
	public String replyByBoard(Long bno, Model model) {
		bRepo.findById(bno).ifPresent(board->{
			model.addAttribute("board", board);		
			model.addAttribute("replyList", rRepo.getRepliesOfBoard(board));		
		});
			
		return "board/replyByBoard";
	}
}