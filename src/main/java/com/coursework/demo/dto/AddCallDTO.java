package com.coursework.demo.dto;

import com.coursework.demo.entity.DeliveryMan;
import com.coursework.demo.entity.Order;
import com.coursework.demo.entity.enums.CallStatus;
import lombok.Data;

@Data
public class AddCallDTO {

    private DeliveryMan deliveryman;

    private Order order;

    private CallStatus callStatus;
}
