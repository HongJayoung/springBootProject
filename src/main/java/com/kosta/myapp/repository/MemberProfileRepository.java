package com.kosta.myapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kosta.myapp.vo.MemberDTO;
import com.kosta.myapp.vo.ProfileDTO;

public interface MemberProfileRepository extends CrudRepository<ProfileDTO, Long> {

	//1.기본제공 메서드 : findAll(), findById(), save() ...
	//2.규칙에 맞는 메서드 정의
	List<ProfileDTO> findByMember(MemberDTO member);
}
