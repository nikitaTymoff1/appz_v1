package com.coursework.demo.mapper;

import com.coursework.demo.dto.AddCarDTO;
import com.coursework.demo.dto.CarDTO;
import com.coursework.demo.entity.Car;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDTO convertToDto(Car car);

    Car convertToEntity(CarDTO carDTO);

    Car convertToEntity(AddCarDTO carDTO);

    List<CarDTO> convertToDtoList(List<Car> cars);

}
