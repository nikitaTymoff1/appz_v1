package com.coursework.demo;

import com.coursework.demo.dto.CallDTO;
import com.coursework.demo.dto.CarDTO;
import com.coursework.demo.dto.ClientDTO;
import com.coursework.demo.dto.DeliveryInfoDTO;
import com.coursework.demo.dto.DeliveryManDTO;
import com.coursework.demo.dto.OrderDTO;
import com.coursework.demo.dto.OrderWithStatusDTO;
import com.coursework.demo.entity.Call;
import com.coursework.demo.entity.Car;
import com.coursework.demo.entity.Client;
import com.coursework.demo.entity.DeliveryMan;
import com.coursework.demo.entity.Order;
import com.coursework.demo.entity.enums.CallStatus;
import com.coursework.demo.entity.enums.CarStatus;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

import static com.coursework.demo.entity.enums.CallStatus.ACCEPTED;
import static com.coursework.demo.entity.enums.CallStatus.DELIVERED;
import static com.coursework.demo.entity.enums.CallStatus.WAITING;
import static com.coursework.demo.entity.enums.PaymentOption.CASH;
import static com.coursework.demo.entity.enums.PaymentOption.MASTERCARD;
import static com.coursework.demo.entity.enums.PaymentOption.VISA;

@UtilityClass
public class TestData {

    public static Call getCall() {
        return Call.builder()
                .id(1L)
                .callStatus(CallStatus.ACCEPTED)
                .build();
    }

    public static CallDTO getCallRequest() {
        return CallDTO.builder()
                .id(1L)
                .callStatus(CallStatus.ACCEPTED)
                .build();
    }

    public static Car getCar() {
        return Car.builder()
                .id(1L)
                .carStatus(CarStatus.BUSY)
                .colour("black")
                .model("BMW")
                .build();
    }

    public static CarDTO getCarRequest() {
        return CarDTO.builder()
                .id(1L)
                .carStatus(CarStatus.BUSY)
                .colour("black")
                .model("BMW")
                .build();
    }


    public static Client getClient() {
        return Client.builder()
                .id(1L)
                .address("Main Street")
                .name("John")
                .build();
    }

    public static ClientDTO getClientRequest() {
        return ClientDTO.builder()
                .id(1L)
                .address("Main Street")
                .name("John")
                .build();
    }

    public static DeliveryMan getDeliveryMan() {
        return DeliveryMan.builder()
                .id(1L)
                .age(18)
                .name("John")
                .wages(25)
                .build();
    }

    public static DeliveryManDTO getDeliveryManRequest() {
        return DeliveryManDTO.builder()
                .id(1L)
                .age(18)
                .name("John")
                .wages(25)
                .build();
    }


    public static Order getOrder() {
        return Order.builder()
                .id(1L)
                .bonus(50L)
                .description("2 pizza")
                .price(250L)
                .build();
    }

    public static OrderDTO getOrderRequest() {
        return OrderDTO.builder()
                .id(1L)
                .bonus(50L)
                .description("2 pizza")
                .price(250L)
                .build();
    }

    public static List<OrderWithStatusDTO> getExpectedOrderWithStatusDto() {
        Client client = Client.builder()
                .id(1L)
                .address("Main Street")
                .name("John")
                .phoneNumber("+380932930")
                .build();

        return Arrays.asList(
                OrderWithStatusDTO.builder()
                        .orderDTO(Order.builder()
                                .id(1L)
                                .bonus(3L)
                                .description("2 pizza")
                                .paymentOption(VISA)
                                .price(50L)
                                .client(client)
                                .build())
                        .callStatus(WAITING).build(),
                OrderWithStatusDTO.builder()
                        .orderDTO(Order.builder()
                                .id(2L)
                                .bonus(5L)
                                .description("beer")
                                .paymentOption(MASTERCARD)
                                .price(33L)
                                .client(client)
                                .build())
                        .callStatus(ACCEPTED).build(),
                OrderWithStatusDTO.builder()
                        .orderDTO(Order.builder()
                                .id(3L)
                                .bonus(77L)
                                .description("sushi")
                                .paymentOption(CASH)
                                .price(100L)
                                .client(client)
                                .build())
                        .callStatus(DELIVERED).build()
        );
    }

    public static DeliveryInfoDTO getDeliveryInfoDto() {
        return DeliveryInfoDTO.builder()
                .deliveryManName("John")
                .deliveryManSurname("Silver")
                .colour("black")
                .model("BMW")
                .build();
    }

}
