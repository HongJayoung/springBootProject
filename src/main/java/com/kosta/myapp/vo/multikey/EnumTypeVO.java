package com.kosta.myapp.vo.multikey;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.kosta.myapp.vo.MemberRoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_enum")
public class EnumTypeVO {
	
	@Id
	private String mid;
	private String mpassword;
	private String mname;
	
	//하나의 맴버가 여러개의 role을 가진다.
	@ElementCollection(targetClass = MemberRoleEnum.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "tbl_enum_mroles", joinColumns = @JoinColumn(name = "mid"))
	private Set<MemberRoleEnum> mrole;

}
