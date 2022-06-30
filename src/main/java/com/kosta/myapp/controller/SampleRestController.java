package com.kosta.myapp.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.myapp.repository.BoardRepository;
import com.kosta.myapp.vo.BoardVO;
import com.kosta.myapp.vo.QBoardVO;
import com.querydsl.core.BooleanBuilder;

@RestController
public class SampleRestController {

	@Autowired
	BoardRepository boardRepo;
	
	@GetMapping("/boardList.do/{type}/{keyword}")
	public List<BoardVO> testPredicate(@PathVariable String type, @PathVariable String keyword) {

		BooleanBuilder builder = new BooleanBuilder();
		QBoardVO board = QBoardVO.boardVO;
		if (type.equals("content")) {
			builder.and(board.content.like("%" + keyword + "%")); //content like '%'?'%'
		} else if(type.equals("title")) {
			builder.and(board.title.like("%"+ keyword +"%")); //title like '%'?'%'
		}
		builder.and(board.bno.gt(50L)); // bno > 50
		System.out.println(builder);
		Pageable pageable = PageRequest.of(0, 5);
		Page<BoardVO> result = boardRepo.findAll(builder, pageable);
		System.out.println("getSize:" + result.getSize());
		System.out.println("getTotalElements:" + result.getTotalElements());
		System.out.println("getTotalPages:" + result.getTotalPages());
		System.out.println("nextPageable:" + result.nextPageable());

		return result.getContent();
	}
	
	@GetMapping("/boardList.do/{pageno}")
	public List<BoardVO> pageInfo(@PathVariable int pageno) {
		Timestamp s = Timestamp.valueOf("2022-06-30 00:00:00.000");
		Timestamp e = Timestamp.valueOf("2022-06-31 00:00:00.000");
		Pageable paging = PageRequest.of(pageno, 10, Sort.by(Direction.DESC, "writer", "regDate")); //page는 0부터 시작, 1 page의 건수는 10으로 설정
		
		Page<BoardVO> result = boardRepo.findByRegDateBetween(s,e, paging);
		System.out.println(result.getNumber());
		System.out.println(result.getTotalElements()); //전체건수
		System.out.println(result.getTotalPages()); //전체 페이지 수
		System.out.println(result.getNumberOfElements()); //한 페이지의 건 수
		
		List<BoardVO> blist = result.getContent();
		return blist;
	}
	
}
