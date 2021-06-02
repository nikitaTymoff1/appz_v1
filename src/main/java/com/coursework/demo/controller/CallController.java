package com.coursework.demo.controller;

import com.coursework.demo.dto.AddCallDTO;
import com.coursework.demo.dto.CallDTO;
import com.coursework.demo.entity.Call;
import com.coursework.demo.mapper.CallMapper;
import com.coursework.demo.service.CallService;
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
@Api(tags = "Call API")
@RequestMapping("/v1/calls")
public class CallController {

    private final CallService callService;
    private final CallMapper callMapper;

    @Autowired
    public CallController(CallService callService, CallMapper callMapper) {
        this.callService = callService;
        this.callMapper = callMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get call info by id")
    public ResponseEntity<CallDTO> get(@PathVariable("id") long id) {
        Call call = callService.getById(id);
        return ResponseEntity.status(OK).body(callMapper.convertToDto(call));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    @ApiOperation(value = "Get the list of all calls")
    public ResponseEntity<List<CallDTO>> getList(@PageableDefault(sort = {"id"}) Pageable pageable) {
        return ResponseEntity.ok().body(callMapper.convertToDtoList(callService.getAll(pageable)));
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Create new call")
    public ResponseEntity<CallDTO> save(@RequestBody AddCallDTO addCallDTO) {
        Call call = callService.save(callMapper.convertToEntity(addCallDTO));
        return ResponseEntity.status(CREATED).body(callMapper.convertToDto(call));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update existing call by id")
    public ResponseEntity<CallDTO> update(@PathVariable("id") long id, @RequestBody CallDTO callDTO) {
        if (id == callDTO.getId()) {
            Call call = callService.save(callMapper.convertToEntity(callDTO));
            return ResponseEntity.status(OK).body(callMapper.convertToDto(call));
        } else {
            return ResponseEntity.status(UNPROCESSABLE_ENTITY).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete call by id")
    public ResponseEntity delete(@PathVariable("id") long id) {
        Call call = callService.getById(id);
        callService.delete(call);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
