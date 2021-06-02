package com.coursework.demo.repository;

import com.coursework.demo.dto.DeliveryInfoDTO;
import com.coursework.demo.entity.DeliveryMan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeliveryManRepository extends PagingAndSortingRepository<DeliveryMan, Long> {

    @Query("SELECT NEW com.coursework.demo.dto.DeliveryInfoDTO(d.name, d.surname, c.model, c.colour) " +
           "FROM Car c " +
           "JOIN c.deliveryMan d " +
           "JOIN Call cl on cl.id = d.id " +
           "JOIN cl.order o " +
           "WHERE o.id = :orderId")
    DeliveryInfoDTO getDeliveryInfoByOrderId(Long orderId);
}
