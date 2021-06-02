package com.coursework.demo.service.impl;

import com.coursework.demo.entity.Call;
import com.coursework.demo.repository.CallRepository;
import com.coursework.demo.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CallServiceImpl implements CallService {

    private CallRepository callRepository;

    @Autowired
    public CallServiceImpl(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    @Override
    public Call getById(Long id) {
        return callRepository.findById(id).get();
    }

    @Override
    public List<Call> getAll(Pageable pageable) {
        return callRepository.findAll(pageable).getContent();
    }

    @Override
    public Call save(Call object) {
        return callRepository.save(object);
    }

    @Override
    public Call delete(Call object) {
        callRepository.delete(object);
        return object;
    }
}
