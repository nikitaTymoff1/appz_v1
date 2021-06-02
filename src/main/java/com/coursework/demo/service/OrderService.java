package com.coursework.demo.service;

import com.coursework.demo.dto.OrderWithStatusDTO;
import com.coursework.demo.entity.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService extends BasicService<Order, Long> {
    List<OrderWithStatusDTO> getOrdersAndCallStatusByClient(Long clientId, Pageable pageable);
}