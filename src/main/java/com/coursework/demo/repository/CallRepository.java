package com.coursework.demo.repository;

import com.coursework.demo.entity.Call;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CallRepository extends PagingAndSortingRepository<Call, Long> {
}
