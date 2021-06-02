package com.coursework.demo.service.impl;

import com.coursework.demo.entity.Client;
import com.coursework.demo.repository.ClientRepository;
import com.coursework.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client getById(Long id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public List<Client> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable).getContent();
    }

    @Override
    public Client save(Client object) {
        return clientRepository.save(object);
    }

    @Override
    public Client delete(Client object) {
        clientRepository.delete(object);
        return object;
    }
}
