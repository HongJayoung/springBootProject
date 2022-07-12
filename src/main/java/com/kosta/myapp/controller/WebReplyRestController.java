package com.kosta.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.myapp.repository.WebBoardRepository;
import com.kosta.myapp.repository.WebReplyRepository;
import com.kosta.myapp.vo.relation.WebBoard;
import com.kosta.myapp.vo.relation.WebReply;

@RestController
@RequestMapping("/replies/*")
public class WebReplyRestController {
	
	@Autowired
	WebReplyRepository rRepo;
	
	@Autowired
	WebBoardRepository bRepo;
	
	@GetMapping("/{bno}")
	public List<WebReply> replyList(@PathVariable Long bno) {
		//방법1
		//title is marked non-null but is null
		WebBoard board = WebBoard.builder().bno(bno).title("").build();
		return rRepo.findByBoard(board);
		
		//방법2
		//return rRepo.getRepliesOfBoard2(bno);
	}
	
	@PostMapping("/{bno}")
	public List<WebReply> insert(@PathVariable Long bno, @RequestBody WebReply reply) {
		WebBoard board = bRepo.findById(bno).get();
		reply.setBoard(board);
		rRepo.save(reply);
		
		return rRepo.getRepliesOfBoard2(bno);
	}
	
	@PutMapping("/{bno}")
	public List<WebReply> update(@PathVariable Long bno, @RequestBody WebReply reply) {
		WebBoard board = bRepo.findById(bno).get();
		reply.setBoard(board);
		rRepo.save(reply);
		
		return rRepo.getRepliesOfBoard2(bno);
	}
	
	@Transactional
	@DeleteMapping("/{bno}/{rno}")
	public List<WebReply> remove(@PathVariable Long bno, @PathVariable Long rno) {
		rRepo.replyDelete(rno);
		
		return rRepo.getRepliesOfBoard2(bno);
	}
}
