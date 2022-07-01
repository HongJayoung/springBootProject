package com.kosta.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kosta.myapp.vo.relation.PDSBoard;

public interface PDSBoardRepository extends PagingAndSortingRepository<PDSBoard, Long> {

	//PagingAndSortingRepository에는 findAll(Sort)가 있음
	// 1.기본제공되는 메서드
	// 2.규칙에 맞는 함수 정의 가능
	// 3.JPQL : @Query이용해서 query 직접 사용가능
	@Query("select b.pid, b.pname, b.pwriter, f.fno, f.pdsfilename from PDSBoard b "
			+ " left outer join b.files2 f order by b.pid ")
	public List<Object[]> getFilesInfo();

	@Query("select b.pname, count(f) from PDSBoard b left outer join b.files2 f "
			+ " where b.pid>0 group by b.pname order by b.pname ")
	public List<Object[]> getFilesCount1();

	@Query(value = "select b.pname, count(f.pdsno) from tbl_pdsboard b left outer join"
			+ " tbl_pdsfiles f on(b.pid=f.pdsno) group by b.pname order by 1 ", nativeQuery = true)
	public List<Object[]> getFilesCount2();

	//@Query => select
	//DML(insert, update, delete)사용하려면 @Modifying 추가
	@Modifying
	@Query("UPDATE FROM PDSFile f set f.pdsfilename = ?2 WHERE f.fno = ?1 ")
	int updatePDSFile(Long fno, String newFileName);

}
