package com.coursework.demo.controller;

import com.coursework.demo.dto.AddCarDTO;
import com.coursework.demo.dto.CarDTO;
import com.coursework.demo.entity.Car;
import com.coursework.demo.mapper.CarMapper;
import com.coursework.demo.service.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@Api(tags = "Car API")
@RequestMapping("/v1/cars")
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;

    @Autowired
    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get car info by id")
    public ResponseEntity<CarDTO> get(@PathVariable("id") long id) {
        Car car = carService.getById(id);
        return ResponseEntity.status(OK).body(carMapper.convertToDto(car));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get the list of all cars")
    public ResponseEntity<List<CarDTO>> getList(@PageableDefault(sort = {"id"}) Pageable pageable) {
        return ResponseEntity.ok().body(carMapper.convertToDtoList(carService.getAll(pageable)));
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Create new car")
    public ResponseEntity<CarDTO> save(@RequestBody AddCarDTO addCarDTO) {
        Car car = carService.save(carMapper.convertToEntity(addCarDTO));
        return ResponseEntity.status(CREATED).body(carMapper.convertToDto(car));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update existing car by id")
    public ResponseEntity<CarDTO> update(@PathVariable("id") long id, @RequestBody CarDTO carDTO) {
        if (id == carDTO.getId()) {
            Car car = carService.save(carMapper.convertToEntity(carDTO));
            return ResponseEntity.status(OK).body(carMapper.convertToDto(car));
        } else {
            return ResponseEntity.status(UNPROCESSABLE_ENTITY).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete car by id")
    public ResponseEntity delete(@PathVariable("id") long id) {
        Car car = carService.getById(id);
        carService.delete(car);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
