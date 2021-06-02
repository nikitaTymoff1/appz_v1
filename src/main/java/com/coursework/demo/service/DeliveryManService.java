package com.coursework.demo.service;

import com.coursework.demo.dto.DeliveryInfoDTO;
import com.coursework.demo.entity.DeliveryMan;

public interface DeliveryManService extends BasicService<DeliveryMan, Long> {
    DeliveryInfoDTO getDeliveryInfoByOrderId(Long orderId);
}
