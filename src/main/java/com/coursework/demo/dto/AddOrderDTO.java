package com.coursework.demo.dto;

import com.coursework.demo.entity.Client;
import com.coursework.demo.entity.enums.PaymentOption;
import lombok.Data;

@Data
public class AddOrderDTO {

    private String description;

    private Long price;

    private Long bonus;

    PaymentOption paymentOption;

    private Client client;
}
