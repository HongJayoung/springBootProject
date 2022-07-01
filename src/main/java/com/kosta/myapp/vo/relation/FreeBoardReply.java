package com.kosta.myapp.vo.relation;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "board") //양방향일때 무한loop주의
@EqualsAndHashCode(of="rno")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_free_replies")
public class FreeBoardReply{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//oracle:sequence, mysql:identity
	Long rno;
	
    String reply;
	
    String replyer;
	
    @CreationTimestamp
	Timestamp regdate;
	
    @UpdateTimestamp
	Timestamp updatedate;
    
    @ManyToOne
    FreeBoard board; //board_bno
}