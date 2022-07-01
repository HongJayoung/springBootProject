package com.kosta.myapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.kosta.myapp.repository.PDSBoardRepository;
import com.kosta.myapp.repository.PDSFileRepository;
import com.kosta.myapp.vo.relation.PDSBoard;
import com.kosta.myapp.vo.relation.PDSFile;

import lombok.extern.java.Log;

@SpringBootTest
@Log
@Commit //test 할 때 필요(없으면 테스트 종료 후 rollback 됨)
public class PDSBoardTest {
	
	@Autowired
	PDSBoardRepository bRepo;
	
	@Autowired
	PDSFileRepository fRepo;
	
	@Test
	public void boardselectAll() {
		List<PDSBoard> blist = (List<PDSBoard>)bRepo.findAll(Sort.by(Direction.DESC, "pwriter"));
		
		for(PDSBoard b:blist) {
			System.out.println(b);
		}
	}
	
	//@Test
	public void boardDelete() {
		bRepo.deleteById(147L);
	}
	
	//@Test
	public void boardUpdateFileAdd() {
		bRepo.findById(153L).ifPresentOrElse(board->{
			board.setPwriter("지현");
			board.setPname("비밀문서");
			
			List<PDSFile> flist = board.getFiles2();
			flist.remove(0);
			
			PDSFile file = PDSFile.builder()
								.pdsfilename("aa.jpg")
								.build();
			flist.add(file);
			bRepo.save(board);
		}, ()->{
			log.info("[알림] XXXXX");
		});
	}
	
	//@Transactional //@Modifying를 수행하기 위해
	//@Test
	public void fileNameUpdate2() {
		int result = bRepo.updatePDSFile(156L, "최종문서.xml");
		log.info(result+"건 수정");
	}
	
	//@Test
	public void fileNameUpdate() {
		fRepo.findById(150L).ifPresentOrElse(f->{
			f.setPdsfilename("이름수정!!.xml");
			fRepo.save(f);
		}, ()->{
			log.info("[알림] XXXXX");
		});
	}
	
	//@Test
	public void getFilesCount() {
		//List<Object[]> blist = bRepo.getFilesCount1();
		List<Object[]> blist = bRepo.getFilesCount2();
		
		blist.forEach(b->{
			System.out.println(Arrays.toString(b));
		});
	}
	
	//@Test
	public void getFileInfo() {
		bRepo.getFilesInfo().forEach(b->{
			System.out.println(Arrays.toString(b));
		});
	}
	
	//@Test
	public void fileSearch() {
		fRepo.findById(145L).ifPresentOrElse(f->{
			log.info(f.toString());
		}, ()->{
			log.info("[알림] XXXXX");
		});
	}
	
	//@Test
	public void retrieveById() {
		bRepo.findById(141L).ifPresentOrElse(board->{
			log.info(board.toString());
		}, ()->{
			log.info("[알림] XXXXX");
		}); 
	}
	
	//@Test
	public void retrieve() {
		bRepo.findAll().forEach(board->{
			System.out.println(board);
		});
	}
	
	//@Test
	public void insert() {
		//Board 3건 file 5건 입력
		IntStream.rangeClosed(1, 3).forEach(i->{
			PDSBoard board = PDSBoard.builder()
					.pname("메뉴얼"+i) 
					.pwriter("홍길동")
					.build();
			
			List<PDSFile> fileList = new ArrayList<>();
			IntStream.rangeClosed(1, 5).forEach(ff->{
				PDSFile file = PDSFile.builder()
						.pdsfilename("첨부파일"+ff+".doc")
						.build();
				fileList.add(file);
			});
			
			board.setFiles2(fileList);
			bRepo.save(board);
		});
	}
}
