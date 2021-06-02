package com.coursework.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DeliveryInfoDTO {

    private String deliveryManName;

    private String deliveryManSurname;

    private String model;

    private String colour;
}
