package com.kosta.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kosta.myapp.vo.relation.WebBoard;
import com.kosta.myapp.vo.relation.WebReply;

public interface WebReplyRepository extends PagingAndSortingRepository<WebReply, Long> {
	
	List<WebReply> findByBoard(WebBoard board);

	//JPQL
	@Query("select r from #{#entityName} r where r.board=?1 and r.rno >0 order by r.rno asc ")
	public List<WebReply> getRepliesOfBoard(WebBoard board);
	
	@Query(value = "select * from tbl_webreplies r where r.board_bno=?1 and r.rno >0 order by r.rno asc ",nativeQuery = true)
	public List<WebReply> getRepliesOfBoard2(Long bno);
	
	@Modifying
	@Query(value = "delete from tbl_webreplies where rno=?1",nativeQuery = true)
	public void replyDelete(Long rno);

}
