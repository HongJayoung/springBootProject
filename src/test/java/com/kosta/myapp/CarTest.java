package com.kosta.myapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.myapp.repository.CarRepository;
import com.kosta.myapp.vo.CarVO;

@SpringBootTest
public class CarTest {
	
	@Autowired
	CarRepository carRepo;
	
	//@Test
	public void test7() {
		Long count = carRepo.count();
		System.out.println("전체 건수 : "+count);
		
		boolean result = carRepo.existsById(103L);
		System.out.println(result?"존재":"존재X");
	}
	
	//@Test
	public void test6() {
		carRepo.findById(103L).ifPresentOrElse(car->{
			carRepo.delete(car);
			System.out.println("[알림] 삭제되었습니다.");
		}, ()->{
			System.out.println("[알림] 조회결과없음");
		});
	}
	
	//@Test
	public void test5() {
		carRepo.findById(103L).ifPresentOrElse(car->{
			System.out.println("[수정 전] "+car);
			
			car.setModel("123");
			car.setPrice(3000);
			carRepo.save(car);
			
			System.out.println("[수정 후] "+car);	
		}, ()->{
			System.out.println("[알림] 조회결과없음");
		});
	}
	
	//@ParameterizedTest
	//@ValueSource(longs = { 102L, 103L, 104L, 1000L })
	public void test4(Long args) {
		carRepo.findById(args).ifPresentOrElse(car->{
			System.out.println(car);
		}, ()->{
			System.out.println("[알림] 조회결과없음");
		});
	}

	//@Test
	public void test3() {
		List<Long> carNos = new ArrayList<>();
		carNos.add(102L);
		carNos.add(103L);
		carNos.add(104L);
		carNos.add(1000L);
		
		carRepo.findAllById(carNos).forEach(car->{
			System.out.println(car);
		});
	}
	
	//@Test
	public void test2() {
		carRepo.findAll().forEach(car->{
			System.out.println(car);
		});
	}
	
	//@Test
	public void test1() {
		String[] arr = {"black","white","green"};
		IntStream.rangeClosed(1, 10).forEach(index -> {
			int num = new Random().nextInt(arr.length);
			CarVO car = CarVO.builder()
							.color(arr[num])
							.price(2000)
							.model("ABC")
							.build();
			carRepo.save(car);
		});
	}
}
