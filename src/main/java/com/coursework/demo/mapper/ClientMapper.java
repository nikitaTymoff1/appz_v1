package com.coursework.demo.mapper;

import com.coursework.demo.dto.AddClientDTO;
import com.coursework.demo.dto.ClientDTO;
import com.coursework.demo.entity.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO convertToDto(Client client);

    Client convertToEntity(ClientDTO clientDTO);

    Client convertToEntity(AddClientDTO clientDTO);

    List<ClientDTO> convertToDtoList(List<Client> clients);

}