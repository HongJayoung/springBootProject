package com.kosta.myapp.vo.relation;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_pdsboard")
@EqualsAndHashCode(of = "pid")
//@ToString(exclude = "files2") //fetch = FetchType.LAZY인 경우 
public class PDSBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pid;
	private String pname;
	private String pwriter;

	//cascade : 영속성전이 PDSBoard가 수정되면 PDSFile변경
	//fetch : EAGER(즉시로딩), LAZY(지연로딩)
	//PDSBoard 조회 시 자식인 PDSFile가 조인하여 조회
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "pdsno") // PDSFile칼럼에 생성 //없으면 중간 테이블이 생김
	private List<PDSFile> files2;
}

/*
@OneToMany로 인해 중간테이블이 생성된다. (@JoinColumn를 사용하면 반대쪽에 칼럼이 추가되고 중간테이블은 생기지않는다)
중간테이블을 생성하지않고  PK,FK를 지정하여 관계를 맺을 수 있다.
PK쪽에서 mappedBy를 이용하여 자신이 다른객체에 매여있음을 명시한다. 
“매여있다”게시글의 댓글이 존재한다면 게시글을 지울수 없다의 의미이다.
mappedBy 에는 참조한 쪽의 변수를 지정한다.  
tbl_free_replies 테이블에 board_bno칼럼이 추가된다.
board의 댓글수를 가져오기위해 지연로딩을 즉시로딩으로 변경한다. (EAGER)
지연로딩을 사용하는 경우는 연관관계 정보를 가져오기 위해 @Transactinal을 사용한다. (LAZY)
양방향 설정을 사용하는 경우 ToString에 주의한다.
*/