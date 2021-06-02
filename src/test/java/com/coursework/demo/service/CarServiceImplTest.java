package com.coursework.demo.service;

import com.coursework.demo.entity.Car;
import com.coursework.demo.entity.enums.CarStatus;
import com.coursework.demo.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.coursework.demo.TestData.getCar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CarServiceImplTest {

    @MockBean
    private CarRepository carRepository;

    @Autowired
    private CarService carService;

    @Test
    public void testGetById() {
        final Car car = getCar();

        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        final Car result = carService.getById(1L);

        assertEquals(car, result);
        verify(carRepository).findById(anyLong());
    }

    @Test
    public void testGetAll() {
        final Car car = getCar();
        final List<Car> cars = Collections.singletonList(car);
        final Pageable pageable = PageRequest.of(0, 5);
        final Page<Car> carPage = new PageImpl<>(cars, pageable, 5);

        when(carRepository.findAll(pageable)).thenReturn(carPage);

        final List<Car> result = carService.getAll(pageable);

        assertEquals(cars, result);
        verify(carRepository).findAll(pageable);
    }

    @Test
    public void testSave() {
        final Car car = getCar();

        when(carRepository.save(car)).thenReturn(car);

        final Car result = carService.save(car);

        assertEquals(car, result);
        verify(carRepository).save(car);
    }

    @Test
    public void testDelete() {
        final Car car = getCar();

        doNothing().when(carRepository).delete(car);

        final Car result = carService.delete(car);

        assertEquals(car, result);
        verify(carRepository).delete(car);
    }
}
