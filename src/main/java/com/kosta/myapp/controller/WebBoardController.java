package com.kosta.myapp.controller;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.kosta.myapp.repository.WebBoardRepository;
import com.kosta.myapp.repository.WebReplyRepository;
import com.kosta.myapp.security.MemberService;
import com.kosta.myapp.vo.PageMaker;
import com.kosta.myapp.vo.PageVO;
import com.kosta.myapp.vo.relation.WebBoard;
import com.querydsl.core.types.Predicate;

//@CrossOrigin //포트번호가 다른 서버로 갈 수 있도록
@Controller
@RequestMapping("/board/*")
public class WebBoardController {

	@Autowired
	WebBoardRepository bRepo;
	
	@Autowired
	WebReplyRepository rRepo;
	
	@Autowired
	MemberService mservice;
	
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
	
//	@GetMapping("/webboard/boardlist")    
//	public void selectAll(Model model, Principal principal, Authentication authentication ) {
//		System.out.println(“방법1:" + principal);
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//		System.out.println(＂방법2:" + userDetails);
//	
//		Object principal2 = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
//		UserDetails userDetails2 = (UserDetails)principal2; 
//		System.out.println(＂방법3:" + userDetails2);
//	
//		String mid = principal.getName();
//		UserDetails userDetails3 =mservice.loadUserByUsername(mid);
//		System.out.println(＂방법4:" +userDetails3);
//	}

	@GetMapping("/webBoardList.go")
	public String boardList(@ModelAttribute PageVO pageVO, Model model, 
							HttpServletRequest request, Principal principal, 
							Authentication authentication, HttpSession session) {
		//-----------security test----------------
		System.out.println("방법1 : " + principal);
		System.out.println("방법2 : " + authentication.getPrincipal());
		System.out.println("방법3 : " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		System.out.println("방법4 : " + mservice.loadUserByUsername(principal.getName()));
		System.out.println("방법5 : " + session.getAttribute("user"));
		//----------------------------------------
		
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