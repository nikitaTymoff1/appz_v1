package com.coursework.demo.service;

import com.coursework.demo.entity.Call;
import com.coursework.demo.entity.enums.CallStatus;
import com.coursework.demo.repository.CallRepository;
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

import static com.coursework.demo.TestData.getCall;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CallServiceImplTest {
    @MockBean
    private CallRepository callRepository;

    @Autowired
    private CallService callService;

    @Test
    public void testGetById() {
        final Call call = getCall();

        when(callRepository.findById(anyLong())).thenReturn(Optional.of(call));

        final Call result = callService.getById(1L);

        assertEquals(call, result);
        verify(callRepository).findById(anyLong());
    }

    @Test
    public void testGetAll() {
        final Call call = getCall();
        final List<Call> calls = Collections.singletonList(call);
        final Pageable pageable = PageRequest.of(0, 5);
        final Page<Call> callPage = new PageImpl<>(calls, pageable, 5);

        when(callRepository.findAll(pageable)).thenReturn(callPage);

        final List<Call> result = callService.getAll(pageable);

        assertEquals(calls, result);
        verify(callRepository).findAll(pageable);
    }

    @Test
    public void testSave() {
        final Call call = getCall();

        when(callRepository.save(call)).thenReturn(call);

        final Call result = callService.save(call);

        assertEquals(call, result);
        verify(callRepository).save(call);
    }

    @Test
    public void testDelete() {
        final Call call = getCall();

        doNothing().when(callRepository).delete(call);

        final Call result = callService.delete(call);

        assertEquals(call, result);
        verify(callRepository).delete(call);
    }
}
