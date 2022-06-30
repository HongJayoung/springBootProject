package com.kosta.myapp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.kosta.myapp.repository.BoardRepository;
import com.kosta.myapp.vo.BoardVO;

@SpringBootTest
public class BoardTest {
	
	@Autowired
	BoardRepository boardRepo;
	
	@Test
	public void jpqlTest1() {
		List<BoardVO> blist;
		
		//blist = boardRepo.selectAllByTitle("5",50L);
		//blist = boardRepo.selectAllByTitle2("5",50L);
		//blist = boardRepo.selectAllByTitle3("5",50L);	
		//blist = boardRepo.selectAllByTitle4("5",50L);
		blist = boardRepo.selectAllByTitle5("5",50L);
		
		blist.forEach(board->{
			System.out.println(board);
		});;
	}
	
	//@Test
	public void test21() {
		Timestamp s = Timestamp.valueOf("2022-06-30 00:00:00.000");
		Timestamp e = Timestamp.valueOf("2022-06-31 00:00:00.000");
		Pageable paging = PageRequest.of(3, 10, Sort.by(Direction.DESC, "writer", "regDate")); //page는 0부터 시작, 1 page의 건수는 10으로 설정
		
		Page<BoardVO> result = boardRepo.findByRegDateBetween(s,e, paging);
		System.out.println(result.getNumber());
		System.out.println(result.getTotalElements()); //전체건수
		System.out.println(result.getTotalPages()); //전체 페이지 수
		System.out.println(result.getNumberOfElements()); //한 페이지의 건 수
		
		List<BoardVO> blist = result.getContent();
		blist.forEach(board->{
			System.out.println(board);
		});
	}	
	
	//@Test
	public void test20() {
		Timestamp s = Timestamp.valueOf("2022-06-30 00:00:00.000");
		Timestamp e = Timestamp.valueOf("2022-06-31 00:00:00.000");
		Pageable paging = PageRequest.of(3, 10, Sort.by(Direction.DESC, "writer", "regDate")); //page는 0부터 시작, 1 page의 건수는 10으로 설정
		
		boardRepo.findByRegDateBetweenOrderByBnoDesc(s,e, paging).forEach(board->{
			System.out.println(board);
		});;
	}	
	
	//@Test
	public void test19() {
		Timestamp s = Timestamp.valueOf("2022-06-30 09:22:31.655");
		Timestamp e = Timestamp.valueOf("2022-06-30 09:22:31.816");
		
		boardRepo.findByRegDateGreaterThanEqualAndRegDateLessThanEqual(s,e).forEach(board->{
			System.out.println(board);
		});;
	}	
	
	//@Test
	public void test18() {
		Timestamp s = Timestamp.valueOf("2022-06-30 09:22:31.655");
		Timestamp e = Timestamp.valueOf("2022-06-30 09:22:31.816");
		
		boardRepo.findByRegDateBetween(s,e).forEach(board->{
			System.out.println(board);
		});;
	}	
	
	//@Test
	public void test17() {
		boardRepo.findByTitleEndingWith("9").forEach(board->{
			System.out.println(board);
		});;
	}	
	
	//@Test
	public void test16() {
		boardRepo.findByBnoLessThanEqual(10L).forEach(board->{
			System.out.println(board);
		});;
	}	
	
	//@Test
	public void test15() {
		boardRepo.findByWriterContainingOrTitleContaining("Jeoung", "9").forEach(board->{
			System.out.println(board);
		});;
	}	
	
	//@Test
	public void test14() {
		boardRepo.findByBnoGreaterThanEqualOrderByBnoDesc(90L).forEach(board->{
			System.out.println(board);
		});;
	}	
	
	//@Test
	public void test13() {
		boardRepo.findByBnoGreaterThan(90L).forEach(board->{
			System.out.println(board);
		});;
	}	
	
	//@Test
	public void test12() {
		boardRepo.findByTitleLike("%2%").forEach(board->{
			System.out.println(board);
		});;
		
		boardRepo.findByTitleContaining("수").forEach(board->{
			System.out.println(board);
		});;
	}	
	
	//@Test
	public void test11() {
		boardRepo.findByWriterAndTitle("Kim", "새로운제목!").forEach(board->{
			System.out.println(board);
		});;
	}	
	
	//@Test
	public void test10() {
		boardRepo.findByWriter("Kim").forEach(board->{
			System.out.println(board);
		});;
	}	
	
	//@Test
	public void test9() {
		boardRepo.findByTitle("제목50").forEach(board->{
			System.out.println(board);
		});;
	}	

	//@Test
	public void test8() {
		Long board = boardRepo.count(); //count
		System.out.println("전체 건수 : "+board);
		
		boolean result = boardRepo.existsById(10L);
		System.out.println(result?"존재":"존재X");
	}	
		
	//@Test
	public void test7() {
		boardRepo.deleteById(19L); //delete
		boardRepo.findById(20L).ifPresentOrElse(board->{
			boardRepo.delete(board); //delete			
			System.out.println("[알림] 게시글이 삭제되었습니다.");
		}, ()->{System.out.println("[알림] 해당하는 게시글이 없습니다.");});
	}
	
	//@Test
	public void test6() {//update
		boardRepo.findById(19L).ifPresentOrElse(board->{
			System.out.println("[수정 전] " + board);
			
			board.setTitle("제목 수정~~~");
			board.setContent("내용 수정!!!!");
			board.setWriter("Kim");
			boardRepo.save(board);
			
			System.out.println("[수정 후] " + board);
		}, ()->{ //없으면 새로 만들기
			BoardVO board = BoardVO.builder()
					.title("새로운제목!")
					.content("새로운 내용~~")
					.writer("Kim")
					.build();
			boardRepo.save(board);
			
			System.out.println("[NEW BOARD] " + board);
		});
	}

	//@Test
	public void test5() { //1건 조회
		Optional<BoardVO> board = boardRepo.findById(30L);
		if(board.isPresent()) {
			BoardVO b = board.get();
			System.out.println(b);
		} else {
			System.out.println("[알림] 해당하는 게시글이 없습니다.");
		}
	}
	
	//@Test
	public void test4() { //1건 조회
		boardRepo.findById(20L).ifPresentOrElse(board->{
			System.out.println(board);
		}, ()->{System.out.println("[알림] 해당하는 게시글이 없습니다.");});
	}
	
	//@Test
	public void test3() { //1건 조회
		boardRepo.findById(1L).ifPresent(board->{
			System.out.println(board);
		});
	}
	
	//@Test
	public void test2() {
		List<BoardVO> boardList = (List<BoardVO>)boardRepo.findAll(); //select * from t_boards
		boardList.forEach(board -> {
			System.out.println(board);
		});
	}
	
	//@Test
	public void test1() {
		IntStream.rangeClosed(1, 10).forEach(i->{
			BoardVO board = BoardVO.builder()
									.title("목요알"+i)
									.content("비가온다"+i)
									.writer("Jeoung")
									.build();
			boardRepo.save(board); //insert
		});
	}
}
