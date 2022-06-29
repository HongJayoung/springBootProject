package com.kosta.myapp;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.myapp.repository.BoardRepository;
import com.kosta.myapp.vo.BoardVO;

@SpringBootTest
public class BoardTest {
	
	@Autowired
	BoardRepository boardRepo;
	
	@Test
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
		IntStream.rangeClosed(1, 100).forEach(i->{
			BoardVO board = BoardVO.builder()
									.title("제목"+i)
									.content("내용"+i)
									.writer("Hong")
									.build();
			boardRepo.save(board); //insert
		});
	}
}
