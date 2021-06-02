package com.coursework.demo.service;

import com.coursework.demo.dto.OrderWithStatusDTO;
import com.coursework.demo.entity.Order;
import com.coursework.demo.entity.enums.CallStatus;
import com.coursework.demo.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.coursework.demo.TestData.getOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceImplTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void testGetById() {
        final Order order = getOrder();

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        final Order result = orderService.getById(1L);

        assertEquals(order, result);
        verify(orderRepository).findById(anyLong());
    }

    @Test
    public void testGetOrdersAndCallStatusByClient() {
        final Order order = getOrder();
        final List<OrderWithStatusDTO> orders =
                Collections.singletonList(
                        OrderWithStatusDTO.builder()
                                .orderDTO(order)
                                .callStatus(CallStatus.ACCEPTED)
                                .build());
        final Pageable pageable = PageRequest.of(0, 5);

        when(orderRepository.getOrdersAndCallStatusByClient(1L, pageable)).thenReturn(orders);

        final List<OrderWithStatusDTO> result = orderService.getOrdersAndCallStatusByClient(1L, pageable);

        assertEquals(orders, result);
        verify(orderRepository).getOrdersAndCallStatusByClient(1L, pageable);
    }

    @Test
    public void testGetAll() {
        final Order order = getOrder();
        final List<Order> orders = Collections.singletonList(order);
        final Pageable pageable = PageRequest.of(0, 5);
        final Page<Order> orderPage = new PageImpl<>(orders, pageable, 5);

        when(orderRepository.findAll(pageable)).thenReturn(orderPage);

        final List<Order> result = orderService.getAll(pageable);

        assertEquals(orders, result);
        verify(orderRepository).findAll(pageable);
    }

    @Test
    public void testSave() {
        final Order order = getOrder();

        when(orderRepository.save(order)).thenReturn(order);

        final Order result = orderService.save(order);

        assertEquals(order, result);
        verify(orderRepository).save(order);
    }

    @Test
    public void testDelete() {
        final Order order = getOrder();

        doNothing().when(orderRepository).delete(order);

        final Order result = orderService.delete(order);

        assertEquals(order, result);
        verify(orderRepository).delete(order);
    }
}
