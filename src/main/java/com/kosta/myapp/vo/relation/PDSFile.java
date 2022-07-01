package com.kosta.myapp.vo.relation;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_pdsfiles")
public class PDSFile {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long fno;
	private String pdsfilename;
}
