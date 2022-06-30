package com.kosta.myapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.myapp.repository.MemberRepository;
import com.kosta.myapp.vo.MemberDTO;
import com.kosta.myapp.vo.MemberRoleEnum;

import lombok.extern.java.Log;

@SpringBootTest
@Log
public class MemberTest {
	
	@Autowired
	MemberRepository mRepo;
	
	@Test
	public void jpqlTest1() {
		List<Object[]> plist;
		//plist = mRepo.getMemberWithProfileCount1("ja");
		plist = mRepo.getMemberWithProfileCount2("ja");
		
		plist.forEach(obj->{
			log.info(obj[0]+" => "+obj[1]+"개");
		});
	}
	
	//@Test
	public void delete() {
		mRepo.findById("ja05").ifPresentOrElse(m->{
			mRepo.delete(m);
		}, ()->{
			System.out.println("[알림] XXXXX");
		});
	}
	
	//@Test
	public void selectById() {
		mRepo.findById("ja02").ifPresentOrElse(m->{
			System.out.println(m);
		}, ()->{
			System.out.println("[알림] XXXXX");
		});
	}
	
	//@Test
	public void update() {
		mRepo.findById("ja02").ifPresentOrElse(m->{
			m.setMname("Hong");
			m.setMpassword("9999");
			m.setMrole(MemberRoleEnum.MANAGER);
			mRepo.save(m);
		}, ()->{
			System.out.println("[알림] XXXXX");
		});
	}
	
	//@Test
	public void selectAll() {
		mRepo.findAll().forEach(m->{
			System.out.println(m);
		});
	}
	
	//@Test
	public void insert() {
		IntStream.rangeClosed(1, 5).forEach(i->{
			MemberDTO member = MemberDTO.builder()
										.mid("ja0"+i)
										.mname("맴버"+i)
										.mpassword("1234")
										.mrole(i%2==0?MemberRoleEnum.ADMIN:MemberRoleEnum.USER)
										.build();
			mRepo.save(member);
		});
	}
}
