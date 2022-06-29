package com.kosta.myapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.kosta.myapp.vo.CarVO;

public interface CarRepository extends CrudRepository<CarVO, Long>{

}
