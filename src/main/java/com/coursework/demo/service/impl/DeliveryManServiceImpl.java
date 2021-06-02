package com.coursework.demo.service.impl;

import com.coursework.demo.dto.DeliveryInfoDTO;
import com.coursework.demo.entity.DeliveryMan;
import com.coursework.demo.repository.DeliveryManRepository;
import com.coursework.demo.service.DeliveryManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class DeliveryManServiceImpl implements DeliveryManService {

    private DeliveryManRepository deliveryManRepository;

    @Autowired
    public DeliveryManServiceImpl(DeliveryManRepository deliveryManRepository) {
        this.deliveryManRepository = deliveryManRepository;
    }

    @Override
    public DeliveryInfoDTO getDeliveryInfoByOrderId(Long orderId) {
        return deliveryManRepository.getDeliveryInfoByOrderId(orderId);
    }

    @Override
    public DeliveryMan getById(Long id) {
        return deliveryManRepository.findById(id).get();
    }

    @Override
    public List<DeliveryMan> getAll(Pageable pageable) {
        return deliveryManRepository.findAll(pageable).getContent();
    }

    @Override
    public DeliveryMan save(DeliveryMan object) {
        return deliveryManRepository.save(object);
    }

    @Override
    public DeliveryMan delete(DeliveryMan object) {
        deliveryManRepository.delete(object);
        return object;
    }
}
