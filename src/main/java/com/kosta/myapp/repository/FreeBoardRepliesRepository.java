package com.kosta.myapp.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.kosta.myapp.vo.relation.FreeBoardReply;

public interface FreeBoardRepliesRepository extends PagingAndSortingRepository<FreeBoardReply, Long> {

}
