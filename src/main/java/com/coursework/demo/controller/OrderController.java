package com.coursework.demo.controller;

import com.coursework.demo.dto.AddOrderDTO;
import com.coursework.demo.dto.OrderDTO;
import com.coursework.demo.dto.OrderWithStatusDTO;
import com.coursework.demo.entity.Order;
import com.coursework.demo.mapper.OrderMapper;
import com.coursework.demo.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@Api(tags = "Order API")
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get order info by id")
    public ResponseEntity<OrderDTO> get(@PathVariable("id") long id) {
        Order order = orderService.getById(id);
        return ResponseEntity.status(OK).body(orderMapper.convertToDto(order));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get the list of all orders")
    public ResponseEntity<List<OrderDTO>> getList(@PageableDefault(sort = {"id"}) Pageable pageable) {
        return ResponseEntity.ok().body(orderMapper.convertToDtoList(orderService.getAll(pageable)));
    }

    @GetMapping("clients/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get the list of all orders and their statuses by client id")
    public ResponseEntity<List<OrderWithStatusDTO>> getList(@PathVariable("id") long id, @PageableDefault(sort = {"id"}) Pageable pageable) {
        return ResponseEntity.ok().body(orderService.getOrdersAndCallStatusByClient(id, pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Create new order")
    public ResponseEntity<OrderDTO> save(@RequestBody AddOrderDTO addOrderDTO) {
        Order order = orderService.save(orderMapper.convertToEntity(addOrderDTO));
        return ResponseEntity.status(CREATED).body(orderMapper.convertToDto(order));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update existing order by id")
    public ResponseEntity<OrderDTO> update(@PathVariable("id") long id, @RequestBody OrderDTO orderDTO) {
        if (id == orderDTO.getId()) {
            Order order = orderService.save(orderMapper.convertToEntity(orderDTO));
            return ResponseEntity.status(OK).body(orderMapper.convertToDto(order));
        } else {
            return ResponseEntity.status(UNPROCESSABLE_ENTITY).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete order by id")
    public ResponseEntity delete(@PathVariable("id") long id) {
        Order order = orderService.getById(id);
        orderService.delete(order);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
