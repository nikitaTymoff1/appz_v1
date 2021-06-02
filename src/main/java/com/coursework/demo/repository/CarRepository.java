package com.coursework.demo.repository;

import com.coursework.demo.entity.Car;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CarRepository extends PagingAndSortingRepository<Car, Long> {

}
