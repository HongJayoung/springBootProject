package com.kosta.myapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.kosta.myapp.vo.relation.PDSFile;

public interface PDSFileRepository extends CrudRepository<PDSFile, Long> {

	//1.기본제공되는 메서드 
	//2.규칙에 맞는 함수 정의 가능
	//3.JPQL : @Query이용해서 query 직접 사용가능
}
