package com.kosta.myapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.FetchType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.kosta.myapp.repository.FreeBoardRepliesRepository;
import com.kosta.myapp.repository.FreeBoardRepository;
import com.kosta.myapp.vo.relation.FreeBoard;
import com.kosta.myapp.vo.relation.FreeBoardReply;

import lombok.extern.java.Log;

@SpringBootTest
@Log
@Commit //@Transactional //fetch = FetchType.LAZY인 경우 필수
public class FreeBoardReplyTest {
	
	@Autowired
	FreeBoardRepository bRepo;
	
	@Autowired
	FreeBoardRepliesRepository rRepo;
	
	@Test
	public void getCountReply() {
		//List<Object[]> result = bRepo.getCountReply();
		List<Object[]> result = bRepo.getCountReply2();
		
		result.forEach(a->{
			log.info(Arrays.toString(a));
		});
	}
	
	//@Test
	public void findByBnoGreaterThan() {
		Pageable page = PageRequest.of(0, 5);
		Page<FreeBoard>  result = bRepo.findByBnoGreaterThan(270L, page);
		
		log.info(result.getNumber()+"");
		log.info(result.getNumberOfElements()+"");
		log.info(result.getTotalElements()+"");
		log.info(result.getTotalPages()+"");
		
		result.getContent().forEach(b->{
			log.info(b.toString());
		});
	}
	
	//@Test
	public void getReplyCount() {
		bRepo.findById(288L).ifPresentOrElse(board->{
			log.info("댓글수 : "+ board.getReplies().size());
		}, ()->{
			System.out.println("[알림] XXXXX");
		});
	}
	
	//@Test
	public void insertReply2() {
		bRepo.findById(288L).ifPresentOrElse(board->{
			FreeBoardReply reply = FreeBoardReply.builder()
					.reply("목요일!")
					.replyer("11")
					.board(board)
					.build();
			
			rRepo.save(reply);
		}, ()->{
			System.out.println("[알림] XXXXX");
		});
	}
	
	//@Test
	public void selectAll() {
		bRepo.findAll().forEach(board->{
			System.out.println(board.toString());
		});
	}
	
	//@Transactional //fetch = FetchType.LAZY인 경우 필수
	//@Test
	public void insertReply() {
		bRepo.findById(288L).ifPresentOrElse(board->{
			List<FreeBoardReply> replies = board.getReplies();
			if(replies == null) replies = new ArrayList<>();
			
			FreeBoardReply reply1 = FreeBoardReply.builder()
												.reply("!!!!!!!")
												.replyer("123")
												.board(board)
												.build();
			FreeBoardReply reply2 = FreeBoardReply.builder()
												.reply("$$$$$$")
												.replyer("123")
												.board(board)
												.build();
			replies.add(reply1);
			replies.add(reply2);
			bRepo.save(board);
		}, ()->{
			System.out.println("[알림] XXXXX");
		});
	}
	
	//@Test
	public void insertBoard() {
		IntStream.rangeClosed(1, 10).forEach(b->{
			FreeBoard board = FreeBoard.builder()
					.title("title"+b)
					.content("content"+b)
					.writer("Kim")
					.build();
			
			bRepo.save(board);
		});
	}
	
	//@Test
	public void insert() {
		//board, reply 입력
		IntStream.rangeClosed(1, 10).forEach(b->{
			FreeBoard board = FreeBoard.builder()
										.title("제목"+b)
										.content("내용"+b)
										.writer("ja0")
										.build();
			
			List<FreeBoardReply> replies = new ArrayList<>();
			IntStream.rangeClosed(1, 5).forEach(r->{
				FreeBoardReply reply = FreeBoardReply.builder()
													.reply("댓글"+b+" => "+r)
													.replyer("작성자"+r)
													.board(board) //어떤 게시글의 댓글인지
													.build();
				replies.add(reply);
			});
			
			board.setReplies(replies);
			bRepo.save(board);
		});
	}
}
