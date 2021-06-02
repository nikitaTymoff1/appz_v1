package com.coursework.demo.service;

import com.coursework.demo.entity.Client;
import com.coursework.demo.repository.ClientRepository;
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

import static com.coursework.demo.TestData.getClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceImplTest {
    
    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Test
    public void testGetById() {
        final Client client = getClient();

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        final Client result = clientService.getById(1L);

        assertEquals(client, result);
        verify(clientRepository).findById(anyLong());
    }

    @Test
    public void testGetAll() {
        final Client client = getClient();
        final List<Client> clients = Collections.singletonList(client);
        final Pageable pageable = PageRequest.of(0, 5);
        final Page<Client> clientPage = new PageImpl<>(clients, pageable, 5);

        when(clientRepository.findAll(pageable)).thenReturn(clientPage);

        final List<Client> result = clientService.getAll(pageable);

        assertEquals(clients, result);
        verify(clientRepository).findAll(pageable);
    }

    @Test
    public void testSave() {
        final Client client = getClient();

        when(clientRepository.save(client)).thenReturn(client);

        final Client result = clientService.save(client);

        assertEquals(client, result);
        verify(clientRepository).save(client);
    }

    @Test
    public void testDelete() {
        final Client client = getClient();

        doNothing().when(clientRepository).delete(client);

        final Client result = clientService.delete(client);

        assertEquals(client, result);
        verify(clientRepository).delete(client);
    }
}
