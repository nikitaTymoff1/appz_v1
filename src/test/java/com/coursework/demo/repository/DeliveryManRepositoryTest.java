package com.coursework.demo.repository;

import com.coursework.demo.dto.DeliveryInfoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static com.coursework.demo.TestData.getDeliveryInfoDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class DeliveryManRepositoryTest {

    @Autowired
    private DeliveryManRepository deliveryManRepository;

    @Test
    @Sql("/scripts/getDeliveryInfoByOrderId.sql")
    public void testGetDeliveryInfoByOrderId() {
       final DeliveryInfoDTO expected = getDeliveryInfoDto();

        DeliveryInfoDTO result = deliveryManRepository.getDeliveryInfoByOrderId(1L);

        assertNotNull(result);
        assertEquals(expected, result);
    }
}
