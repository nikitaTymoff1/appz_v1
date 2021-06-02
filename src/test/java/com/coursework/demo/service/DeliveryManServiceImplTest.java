package com.coursework.demo.service;

import com.coursework.demo.dto.DeliveryInfoDTO;
import com.coursework.demo.entity.DeliveryMan;
import com.coursework.demo.repository.DeliveryManRepository;
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

import static com.coursework.demo.TestData.getDeliveryMan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeliveryManServiceImplTest {

    @MockBean
    private DeliveryManRepository deliveryManRepository;

    @Autowired
    private DeliveryManService deliveryManService;

    @Test
    public void testGetById() {
        final DeliveryMan deliveryMan = getDeliveryMan();

        when(deliveryManRepository.findById(anyLong())).thenReturn(Optional.of(deliveryMan));

        final DeliveryMan result = deliveryManService.getById(1L);

        assertEquals(deliveryMan, result);
        verify(deliveryManRepository).findById(anyLong());
    }

    @Test
    public void testGetDeliveryInfoByOrderId() {
        final DeliveryInfoDTO expected = DeliveryInfoDTO.builder()
                .deliveryManName("John")
                .deliveryManSurname("Silver")
                .colour("black")
                .model("BMW")
                .build();

        when(deliveryManRepository.getDeliveryInfoByOrderId(1L)).thenReturn(expected);

        final DeliveryInfoDTO result = deliveryManService.getDeliveryInfoByOrderId(1L);

        assertEquals(expected, result);
        verify(deliveryManRepository).getDeliveryInfoByOrderId(1L);
    }

    @Test
    public void testGetAll() {
        final DeliveryMan deliveryMan = getDeliveryMan();
        final List<DeliveryMan> deliveryManList = Collections.singletonList(deliveryMan);
        final Pageable pageable = PageRequest.of(0, 5);
        final Page<DeliveryMan> deliveryManPage = new PageImpl<>(deliveryManList, pageable, 5);

        when(deliveryManRepository.findAll(pageable)).thenReturn(deliveryManPage);

        final List<DeliveryMan> result = deliveryManService.getAll(pageable);

        assertEquals(deliveryManList, result);
        verify(deliveryManRepository).findAll(pageable);
    }

    @Test
    public void testSave() {
        final DeliveryMan deliveryMan = getDeliveryMan();

        when(deliveryManRepository.save(deliveryMan)).thenReturn(deliveryMan);

        final DeliveryMan result = deliveryManService.save(deliveryMan);

        assertEquals(deliveryMan, result);
        verify(deliveryManRepository).save(deliveryMan);
    }

    @Test
    public void testDelete() {
        final DeliveryMan deliveryMan = getDeliveryMan();

        doNothing().when(deliveryManRepository).delete(deliveryMan);

        final DeliveryMan result = deliveryManService.delete(deliveryMan);

        assertEquals(deliveryMan, result);
        verify(deliveryManRepository).delete(deliveryMan);
    }
}
