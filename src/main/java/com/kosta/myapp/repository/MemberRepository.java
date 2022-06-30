package com.kosta.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kosta.myapp.vo.MemberDTO;

public interface MemberRepository extends CrudRepository<MemberDTO, String> {

	//JPQL ... nativeQuery
	@Query(value = "select m.mid, count(p.member_mid)"
				+ " from tbl_members m left outer join tbl_profile p"
				+ " on( m.mid = p.member_mid)"
				+ " where m.mid like %?1%"
				+ " group by m.mid", nativeQuery = true)
	List<Object[]> getMemberWithProfileCount1(String mid);
	
	//JPQL
	@Query(value = "select m.mid, count(p)"
				+ " from MemberDTO m left outer join ProfileDTO p"
				+ " on( m.mid = p.member )"
				+ " where m.mid like %?1%"
				+ " group by m.mid")
	List<Object[]> getMemberWithProfileCount2(String mid);
}
