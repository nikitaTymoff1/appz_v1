package com.coursework.demo.dto;

import com.coursework.demo.entity.DeliveryMan;
import com.coursework.demo.entity.enums.CarStatus;
import lombok.Data;

@Data
public class AddCarDTO {

    private String model;

    private String colour;

    private DeliveryMan deliveryMan;

    private CarStatus carStatus;
}
