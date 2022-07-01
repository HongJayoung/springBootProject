package com.kosta.myapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.kosta.myapp.vo.multikey.EnumTypeVO;

public interface EnumTypeRepository extends CrudRepository<EnumTypeVO, String>{

}
