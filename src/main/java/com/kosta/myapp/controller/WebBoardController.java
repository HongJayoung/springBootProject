package com.kosta.myapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

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
	public String deletePost(PageVO pageVO,WebBoard board, RedirectAttributes attr) {
		bRepo.deleteById(board.getBno());
		
		attr.addFlashAttribute("msg", "DELETE SECCESS!");
		attr.addFlashAttribute("pageVO", pageVO);
		return "redirect:/board/webBoardList.go";
	}
	
	@PostMapping("/modify.go")
	public String modifyPost(@ModelAttribute PageVO pageVO, WebBoard board, RedirectAttributes attr) {
		bRepo.findById(board.getBno()).ifPresentOrElse(b->{
			b.setContent(board.getContent());
			b.setTitle(board.getTitle());
			
			WebBoard result = bRepo.save(b);
			attr.addFlashAttribute("msg", result!=null?"UPDATE SECCESS!":"UPDATE FAIL!");
		}, ()->{System.out.println("[알림] XXXXX");});
		
		attr.addFlashAttribute("pageVO", pageVO);
		return "redirect:/board/webBoardList.go";
	}
	
	@GetMapping("/modify.go")
	public void modify(@ModelAttribute PageVO pageVO, Long bno, Model model) {
		model.addAttribute("board", bRepo.findById(bno).get());	
	}
	
	@PostMapping("/register.go")
	public String registerPost(WebBoard board, RedirectAttributes attr) {
		WebBoard result = bRepo.save(board);
		attr.addFlashAttribute("msg", result!=null?"INSERT SECCESS!":"INSERT FAIL!");
		
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
	public String boardList(@ModelAttribute PageVO pageVO, Model model, HttpServletRequest request) {
		Map<String, Object> map = (Map<String, Object>) RequestContextUtils.getInputFlashMap(request);
		if(map != null) {
			String msg = (String)map.get("msg");
			//pageVO = (PageVO) map.get("pageVO");
			
			model.addAttribute("msg", msg);
		}
		//if(pageVO == null) pageVO = new PageVO();
		
		Pageable page = pageVO.makePaging(0, "bno"); //bno로 desc sort
		Predicate predicate =  bRepo.makePredicate(pageVO.getType(), pageVO.getKeyword());
		
		Page<WebBoard> blist = bRepo.findAll(predicate, page);
		
		PageMaker<WebBoard> pgmaker = new PageMaker<>(blist);
		model.addAttribute("boardList", pgmaker);
		//model.addAttribute("pageVO", pageVO);

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