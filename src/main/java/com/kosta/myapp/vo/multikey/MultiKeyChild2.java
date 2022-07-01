package com.kosta.myapp.vo.multikey;

import java.sql.Timestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
@Table(name = "tbl_order_detail2")
public class MultiKeyChild2 {

	@EmbeddedId
	MultiKeyParent2 orderid;
	
	String orderGoods;
	String orderUser;
	
	@CreationTimestamp
	Timestamp orderDate;
}
