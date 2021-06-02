package com.coursework.demo.service.impl;

import com.coursework.demo.entity.Car;
import com.coursework.demo.repository.CarRepository;
import com.coursework.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car getById(Long id) {
        return carRepository.findById(id).get();
    }

    @Override
    public List<Car> getAll(Pageable pageable) {
        return carRepository.findAll(pageable).getContent();
    }

    @Override
    public Car save(Car object) {
        return carRepository.save(object);
    }

    @Override
    public Car delete(Car object) {
        carRepository.delete(object);
        return object;
    }
}
