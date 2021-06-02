package com.coursework.demo.it;

import com.coursework.demo.dto.CarDTO;
import com.coursework.demo.entity.Car;
import com.coursework.demo.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.coursework.demo.TestData.getCar;
import static com.coursework.demo.TestData.getCarRequest;
import static com.coursework.demo.it.TestUtils.asJsonString;
import static com.coursework.demo.it.TestUtils.deleteRequest;
import static com.coursework.demo.it.TestUtils.getRequest;
import static com.coursework.demo.it.TestUtils.postRequest;
import static com.coursework.demo.it.TestUtils.putRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CarControllerIT {

    private static final String CAR_CONTROLLER_PATH = "/v1/cars/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarRepository carRepository;

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveCarById() throws Exception {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(getCar()));

        mockMvc.perform(getRequest(CAR_CONTROLLER_PATH + "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(getCarRequest())));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveCarList() throws Exception {
        final Car car = getCar();
        final List<Car> cars = Collections.singletonList(car);
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        final Page<Car> carPage = new PageImpl<>(cars, pageable, 10);

        when(carRepository.findAll(pageable)).thenReturn(carPage);

        mockMvc.perform(getRequest(CAR_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(Collections.singletonList(getCarRequest()))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveCar() throws Exception {
        final Car car = getCar();
        final CarDTO request = getCarRequest();

        when(carRepository.save(any(Car.class))).thenReturn(car);

        mockMvc.perform(postRequest(CAR_CONTROLLER_PATH, request))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCar() throws Exception {
        final Car car = getCar();
        final CarDTO request = getCarRequest();

        when(carRepository.save(car)).thenReturn(car);

        mockMvc.perform(putRequest(CAR_CONTROLLER_PATH + "1", request))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCarExpectedBadRequest() throws Exception {
        final Car car = getCar();
        final CarDTO request = getCarRequest();

        when(carRepository.save(car)).thenReturn(car);

        mockMvc.perform(putRequest(CAR_CONTROLLER_PATH + "2", request))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCar() throws Exception {
        final Car car = getCar();

        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));
        doNothing().when(carRepository).delete(car);

        mockMvc.perform(deleteRequest(CAR_CONTROLLER_PATH + "1"))
                .andExpect(status().isNoContent());
    }
}
