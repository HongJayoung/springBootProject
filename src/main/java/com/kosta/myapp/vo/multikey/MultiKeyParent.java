package com.kosta.myapp.vo.multikey;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiKeyParent implements Serializable{ //Composite-id class must implement Serializable
	private static final long serialVersionUID = 1L;

	private Integer orderId;
	private Integer orderSeq;
	
}
