package com.kosta.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosta.myapp.repository.WebBoardRepository;
import com.kosta.myapp.repository.WebReplyRepository;
import com.kosta.myapp.vo.PageMaker;
import com.kosta.myapp.vo.PageVO;
import com.kosta.myapp.vo.relation.WebBoard;
import com.querydsl.core.types.Predicate;

@Controller
@RequestMapping("/board/*")
public class WebBoardController {

	@Autowired
	WebBoardRepository bRepo;
	
	@Autowired
	WebReplyRepository rRepo;
	
	@PostMapping("/delete.go")
	public String deletePost(WebBoard board) {
		bRepo.deleteById(board.getBno());
		
		return "redirect:/board/webBoardList.go";
	}
	
	@PostMapping("/modify.go")
	public String modifyPost(@ModelAttribute PageVO pageVO, WebBoard board) {
		bRepo.findById(board.getBno()).ifPresentOrElse(b->{
			b.setContent(board.getContent());
			b.setTitle(board.getTitle());
			
			bRepo.save(b);
		}, ()->{System.out.println("[알림] XXXXX");});
		
		return "redirect:/board/webBoardList.go";
	}
	
	@GetMapping("/modify.go")
	public void modify(@ModelAttribute PageVO pageVO, Long bno, Model model) {
		model.addAttribute("board", bRepo.findById(bno).get());	
	}
	
	@PostMapping("/register.go")
	public String registerPost(WebBoard board) {
		bRepo.save(board);
		
		return "redirect:/board/webBoardList.go";
	}
	
	@GetMapping("/register.go")
	public void register() {
		//board/register.html 이 default
	}
	
	@GetMapping("/view.go")
	public String boardView(@ModelAttribute PageVO pageVO, Long bno, Model model) {
		model.addAttribute("board", bRepo.findById(bno).get());		

		return "board/boardView";
	}
	
	@GetMapping("/webBoardList.go")
	public String boardList(@ModelAttribute PageVO pageVO, Model model) {
		Pageable page = pageVO.makePaging(0, "bno"); //bno로 desc sort
		Predicate predicate =  bRepo.makePredicate(pageVO.getType(), pageVO.getKeyword());
		
		Page<WebBoard> blist = bRepo.findAll(predicate, page);
		
		PageMaker<WebBoard> pgmaker = new PageMaker<>(blist);
		model.addAttribute("boardList", pgmaker);
		//model.addAttribute("pageVO", pageVO);
		
//		System.out.println(blist.getNumber());
//		System.out.println(blist.getSize());
//		System.out.println(blist.getTotalPages());
//		System.out.println(blist.getContent());

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