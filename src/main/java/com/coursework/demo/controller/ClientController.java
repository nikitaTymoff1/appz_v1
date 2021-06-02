package com.coursework.demo.controller;

import com.coursework.demo.dto.AddClientDTO;
import com.coursework.demo.dto.ClientDTO;
import com.coursework.demo.entity.Client;
import com.coursework.demo.mapper.ClientMapper;
import com.coursework.demo.service.ClientService;
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
@Api(tags = "Client API")
@RequestMapping("/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Get client info by id")
    public ResponseEntity<ClientDTO> get(@PathVariable("id") long id){
        Client client = clientService.getById(id);
        return ResponseEntity.status(OK).body(clientMapper.convertToDto(client));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get the list of all clients")
    public ResponseEntity<List<ClientDTO>> getList(@PageableDefault(sort = {"id"}) Pageable pageable) {
        return ResponseEntity.ok().body(clientMapper.convertToDtoList(clientService.getAll(pageable)));
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Create new client")
    public ResponseEntity<ClientDTO> save(@RequestBody AddClientDTO addClientDTO) {
        Client client = clientService.save(clientMapper.convertToEntity(addClientDTO));
        return ResponseEntity.status(CREATED).body(clientMapper.convertToDto(client));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update existing client by id")
    public ResponseEntity<ClientDTO> update(@PathVariable("id") long id, @RequestBody ClientDTO clientDTO) {
        if (id == clientDTO.getId()) {
            Client client = clientService.save(clientMapper.convertToEntity(clientDTO));
            return ResponseEntity.status(OK).body(clientMapper.convertToDto(client));
        } else {
            return ResponseEntity.status(UNPROCESSABLE_ENTITY).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete client by id")
    public ResponseEntity delete(@PathVariable("id") long id){
        Client client = clientService.getById(id);
        clientService.delete(client);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
