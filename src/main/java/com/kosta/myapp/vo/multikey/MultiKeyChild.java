package com.kosta.myapp.vo.multikey;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
@Table(name = "tbl_order_detail")
@IdClass(MultiKeyParent.class)
public class MultiKeyChild {

	@Id
	private Integer orderId;
	
	@Id
	private Integer orderSeq;
	
	String orderGoods;
	String orderUser;
	
	@CreationTimestamp
	Timestamp orderDate;
}
