package com.kosta.myapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.myapp.vo.CarVO;

@SpringBootTest
class SpringBootProjectApplicationTests {

	@Test
	public void test1() {
		CarVO car1 = new CarVO();
		CarVO car3 = CarVO.builder()
				.model("car3")
				.price(2000)
				.build(); //new CarVO()
		CarVO car4 = CarVO.builder()
				.model("car3")
				.price(2000)
				.build(); //new CarVO()
		
		System.out.println(car1);
		System.out.println(car3);
		System.out.println(car4);
		System.out.println(car3.equals(car4));
		
		car1.setModel("car1");
		car1.setColor("black");
		System.out.println(car1);
		
	}
}