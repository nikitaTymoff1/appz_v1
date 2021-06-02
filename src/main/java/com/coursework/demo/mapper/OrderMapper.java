package com.coursework.demo.mapper;

import com.coursework.demo.dto.AddOrderDTO;
import com.coursework.demo.dto.OrderDTO;
import com.coursework.demo.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO convertToDto(Order order);

    Order convertToEntity(OrderDTO orderDTO);

    Order convertToEntity(AddOrderDTO orderDTO);

    List<OrderDTO> convertToDtoList(List<Order> orders);

}