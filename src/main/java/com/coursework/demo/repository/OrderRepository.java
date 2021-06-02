package com.coursework.demo.repository;

import com.coursework.demo.dto.OrderWithStatusDTO;
import com.coursework.demo.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    @Query("SELECT NEW com.coursework.demo.dto.OrderWithStatusDTO(o, call.callStatus) FROM Order o " +
           "JOIN o.client client " +
           "JOIN Call call ON o.id = call.id " +
           "WHERE client.id = :clientId ")
    List<OrderWithStatusDTO> getOrdersAndCallStatusByClient(Long clientId, Pageable pageable);
}
