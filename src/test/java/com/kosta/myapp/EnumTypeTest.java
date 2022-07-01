package com.kosta.myapp;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.myapp.repository.EnumTypeRepository;
import com.kosta.myapp.vo.MemberRoleEnum;
import com.kosta.myapp.vo.multikey.EnumTypeVO;

@SpringBootTest
public class EnumTypeTest {

	@Autowired
	EnumTypeRepository eRepo;
	
	@Test
	public void test1() {
		Set<MemberRoleEnum> mroleSet = new HashSet<>();
		mroleSet.add(MemberRoleEnum.ADMIN);
		mroleSet.add(MemberRoleEnum.MANAGER);
		mroleSet.add(MemberRoleEnum.USER);
		
		EnumTypeVO vo = EnumTypeVO.builder()
								.mid("ja0")
								.mpassword("1234")
								.mrole(mroleSet)
								.build();
		eRepo.save(vo);
	}
}
