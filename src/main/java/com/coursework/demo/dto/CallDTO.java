package com.coursework.demo.dto;

import com.coursework.demo.entity.DeliveryMan;
import com.coursework.demo.entity.Order;
import com.coursework.demo.entity.enums.CallStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CallDTO {
    private Long id;

    private DeliveryMan deliveryman;

    private Order order;

    private CallStatus callStatus;
}
