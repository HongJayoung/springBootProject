package com.kosta.myapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kosta.myapp.vo.BoardVO;

public interface BoardRepository extends CrudRepository<BoardVO, Long>{
	//1.기본 CRUD는 제공됨...findAll(), findById(), save(), delete(), count()
	//2.규칙에 맞는 메서드 정의 추가
	List<BoardVO> findByTitle(String title);
	List<BoardVO> findByWriter(String writer);
	List<BoardVO> findByWriterAndTitle(String writer, String title);
	List<BoardVO> findByTitleLike(String title); //where title like ?
	List<BoardVO> findByTitleContaining(String title); //where title like '%'?'%'
	List<BoardVO> findByBnoGreaterThan(Long bno); //where bno>?
}
