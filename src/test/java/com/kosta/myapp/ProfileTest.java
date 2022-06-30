package com.kosta.myapp;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.myapp.repository.MemberProfileRepository;
import com.kosta.myapp.repository.MemberRepository;
import com.kosta.myapp.vo.MemberDTO;
import com.kosta.myapp.vo.ProfileDTO;

import lombok.extern.java.Log;

@SpringBootTest
@Log
public class ProfileTest {
	
	@Autowired
	MemberProfileRepository proRepo;
	
	@Autowired
	MemberRepository mRepo;
	
	//@Test
	public void update() {
		proRepo.findById(120L).ifPresentOrElse(p->{
			p.setFname("수정");
			proRepo.save(p);
		}, ()->{
			System.out.println("[알림] XXXXX");
		});
	}
	
	//@Test
	public void deleteById() {
		proRepo.findById(114L).ifPresentOrElse(p->{
			proRepo.delete(p);
		}, ()->{
			System.out.println("[알림] XXXXX");
		});
	}
	
	@Test
	public void selectByMember() {
		MemberDTO member = mRepo.findById("ja01").orElse(null);
		
		if(member == null) {
			System.out.println("[알림] XXXXX");
			return;
		}

		List<ProfileDTO> plist = proRepo.findByMember(member);
		plist.forEach(p->{
			log.info(p.toString());
			log.info(p.getMember().toString());
			log.info("------------------------------");
		});
	}
	
	//@Test
	public void selectById() {
		proRepo.findById(120L).ifPresentOrElse(p->{
			System.out.println(p);
		}, ()->{
			System.out.println("[알림] XXXXX");
		});
	}
	
	//@Test
	public void selectAll() {
		proRepo.findAll().forEach(p->{
			System.out.println(p);
		});
	}
	
	//@Test
	public void insert() {
		Optional<MemberDTO> mm = mRepo.findById("ja02");
		if(mm == null) {
			System.out.println("[알림] XXXXX");
			return;
		} else {
			MemberDTO member = mm.get();
			IntStream.rangeClosed(1, 10).forEach(i->{
				ProfileDTO profile = ProfileDTO.builder()
												.fname("ja02_face"+i+"jpg")
												.current_yn(i==1?true:false)
												.member(member)
												.build();
				proRepo.save(profile);
			});
		}
	}
}
