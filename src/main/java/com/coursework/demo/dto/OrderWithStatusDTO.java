package com.coursework.demo.dto;

import com.coursework.demo.entity.Order;
import com.coursework.demo.entity.enums.CallStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderWithStatusDTO {
    Order orderDTO;
    CallStatus callStatus;
}
