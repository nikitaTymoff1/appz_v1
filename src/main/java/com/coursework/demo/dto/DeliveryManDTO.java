package com.coursework.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryManDTO {
    private long id;

    private String name;

    private String surname;

    private int age;

    private int wages;
}
