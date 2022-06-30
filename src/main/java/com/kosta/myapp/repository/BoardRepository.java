package com.kosta.myapp.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.kosta.myapp.vo.BoardVO;

public interface BoardRepository extends CrudRepository<BoardVO, Long>,
										QuerydslPredicateExecutor<BoardVO>{
	//1.기본 CRUD는 제공됨...findAll(), findById(), save(), delete(), count()
	
	//2.규칙에 맞는 메서드 정의 추가
	List<BoardVO> findByTitle(String title);
	List<BoardVO> findByWriter(String writer);
	List<BoardVO> findByWriterAndTitle(String writer, String title);
	List<BoardVO> findByTitleLike(String title); //where title like ?
	List<BoardVO> findByTitleContaining(String title); //where title like '%'?'%'
	List<BoardVO> findByBnoGreaterThan(Long bno); //where bno>?
	
	List<BoardVO> findByTitleEndingWith(String title); 

	List<BoardVO> findByBnoGreaterThanEqualOrderByBnoDesc(Long bno);
	List<BoardVO> findByWriterContainingOrTitleContaining(String writer, String title);
	List<BoardVO> findByBnoLessThanEqual(Long bno);
	List<BoardVO> findByRegDateBetween(Timestamp sdate, Timestamp edate);
	List<BoardVO> findByRegDateGreaterThanEqualAndRegDateLessThanEqual(Timestamp sdate, Timestamp edate);
	List<BoardVO> findByRegDateBetweenOrderByBnoDesc(Timestamp sdate, Timestamp edate, Pageable paging);
	Page<BoardVO> findByRegDateBetween(Timestamp sdate, Timestamp edate, Pageable paging);
	
	//3.JPQL(JPA Query Language)...join, 복잡한 sql가능
	//*불가
	@Query("select b from BoardVO b where b.bno >= ?2 and b.title like %?1% order by b.title desc")
	List<BoardVO> selectAllByTitle(String title, Long bno);
	
	@Query("select b from BoardVO b where b.bno >= :bno and b.title like %:tt% order by b.title desc")
	List<BoardVO> selectAllByTitle3(@Param("tt") String title, @Param("bno") Long bno);
	
	@Query("select b from #{#entityName} b where b.bno >= :bno and b.title like %:tt% order by b.title desc")
	List<BoardVO> selectAllByTitle4(@Param("tt") String title, @Param("bno") Long bno);
	
	@Query("select b from #{#entityName} b where b.bno >= ?2 and b.title like %?1% order by b.title desc")
	List<BoardVO> selectAllByTitle5(String title, Long bno);
	
	//nativeQuery = true => SQL문임
	@Query(value = "select * from t_boards b where b.bno >= ?2 and b.title like %?1% order by b.title desc", nativeQuery = true)
	List<BoardVO> selectAllByTitle2(String title, Long bno);
}
