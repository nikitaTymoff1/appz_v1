package com.coursework.demo.it;

import com.coursework.demo.dto.ClientDTO;
import com.coursework.demo.entity.Client;
import com.coursework.demo.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.coursework.demo.TestData.getClient;
import static com.coursework.demo.TestData.getClientRequest;
import static com.coursework.demo.it.TestUtils.asJsonString;
import static com.coursework.demo.it.TestUtils.deleteRequest;
import static com.coursework.demo.it.TestUtils.getRequest;
import static com.coursework.demo.it.TestUtils.postRequest;
import static com.coursework.demo.it.TestUtils.putRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientControllerIT {

    private static final String CLIENT_CONTROLLER_PATH = "/v1/clients/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveClientById() throws Exception {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(getClient()));

        mockMvc.perform(getRequest(CLIENT_CONTROLLER_PATH + "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(getClientRequest())));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveClientList() throws Exception {
        final Client client = getClient();
        final List<Client> clients = Collections.singletonList(client);
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        final Page<Client> clientPage = new PageImpl<>(clients, pageable, 10);

        when(clientRepository.findAll(pageable)).thenReturn(clientPage);

        mockMvc.perform(getRequest(CLIENT_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(Collections.singletonList(getClientRequest()))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveClient() throws Exception {
        final Client client = getClient();
        final ClientDTO request = getClientRequest();

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        mockMvc.perform(postRequest(CLIENT_CONTROLLER_PATH, request))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateClient() throws Exception {
        final Client client = getClient();
        final ClientDTO request = getClientRequest();

        when(clientRepository.save(client)).thenReturn(client);

        mockMvc.perform(putRequest(CLIENT_CONTROLLER_PATH + "1", request))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateClientExpectedBadRequest() throws Exception {
        final Client client = getClient();
        final ClientDTO request = getClientRequest();

        when(clientRepository.save(client)).thenReturn(client);

        mockMvc.perform(putRequest(CLIENT_CONTROLLER_PATH + "2", request))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteClient() throws Exception {
        final Client client = getClient();

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).delete(client);

        mockMvc.perform(deleteRequest(CLIENT_CONTROLLER_PATH + "1"))
                .andExpect(status().isNoContent());
    }
}
