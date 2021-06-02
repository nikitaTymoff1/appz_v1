package com.coursework.demo.repository;

import com.coursework.demo.dto.OrderWithStatusDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.coursework.demo.TestData.getExpectedOrderWithStatusDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Sql("/scripts/getOrdersAndCallStatusByClient.sql")
    public void testGetOrdersAndCallStatusByClient() {
        final Pageable pageable = PageRequest.of(0, 5);

        List<OrderWithStatusDTO> result = orderRepository.getOrdersAndCallStatusByClient(1L, pageable);

        assertFalse(result.isEmpty());
        assertEquals(getExpectedOrderWithStatusDto(), result);
    }
}
