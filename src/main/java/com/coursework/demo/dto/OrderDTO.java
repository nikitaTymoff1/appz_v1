package com.coursework.demo.dto;

import com.coursework.demo.entity.Client;
import com.coursework.demo.entity.enums.PaymentOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;

    private String description;

    private Long price;

    private Long bonus;

    PaymentOption paymentOption;

    private Client client;
}
