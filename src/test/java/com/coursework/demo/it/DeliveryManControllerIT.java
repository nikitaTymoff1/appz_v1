package com.coursework.demo.it;

import com.coursework.demo.dto.DeliveryInfoDTO;
import com.coursework.demo.dto.DeliveryManDTO;
import com.coursework.demo.entity.DeliveryMan;
import com.coursework.demo.repository.DeliveryManRepository;
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

import static com.coursework.demo.TestData.getDeliveryInfoDto;
import static com.coursework.demo.TestData.getDeliveryMan;
import static com.coursework.demo.TestData.getDeliveryManRequest;
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
public class DeliveryManControllerIT {

    private static final String CLIENT_CONTROLLER_PATH = "/v1/deliverymen/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryManRepository deliveryManRepository;

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveDeliveryManById() throws Exception {
        when(deliveryManRepository.findById(anyLong())).thenReturn(Optional.of(getDeliveryMan()));

        mockMvc.perform(getRequest(CLIENT_CONTROLLER_PATH + "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(getDeliveryManRequest())));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveDeliveryInfoDtoByOrderId() throws Exception {
        final DeliveryInfoDTO dto = getDeliveryInfoDto();
        when(deliveryManRepository.getDeliveryInfoByOrderId(1L)).thenReturn(dto);

        mockMvc.perform(getRequest(CLIENT_CONTROLLER_PATH + "orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(dto)));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveDeliveryManList() throws Exception {
        final DeliveryMan deliveryMan = getDeliveryMan();
        final List<DeliveryMan> deliveryMans = Collections.singletonList(deliveryMan);
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        final Page<DeliveryMan> deliveryManPage = new PageImpl<>(deliveryMans, pageable, 10);

        when(deliveryManRepository.findAll(pageable)).thenReturn(deliveryManPage);

        mockMvc.perform(getRequest(CLIENT_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(Collections.singletonList(getDeliveryManRequest()))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveDeliveryMan() throws Exception {
        final DeliveryMan deliveryMan = getDeliveryMan();
        final DeliveryManDTO request = getDeliveryManRequest();

        when(deliveryManRepository.save(any(DeliveryMan.class))).thenReturn(deliveryMan);

        mockMvc.perform(postRequest(CLIENT_CONTROLLER_PATH, request))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateDeliveryMan() throws Exception {
        final DeliveryMan deliveryMan = getDeliveryMan();
        final DeliveryManDTO request = getDeliveryManRequest();

        when(deliveryManRepository.save(deliveryMan)).thenReturn(deliveryMan);

        mockMvc.perform(putRequest(CLIENT_CONTROLLER_PATH + "1", request))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateDeliveryManExpectedBadRequest() throws Exception {
        final DeliveryMan deliveryMan = getDeliveryMan();
        final DeliveryManDTO request = getDeliveryManRequest();

        when(deliveryManRepository.save(deliveryMan)).thenReturn(deliveryMan);

        mockMvc.perform(putRequest(CLIENT_CONTROLLER_PATH + "2", request))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteDeliveryMan() throws Exception {
        final DeliveryMan deliveryMan = getDeliveryMan();

        when(deliveryManRepository.findById(anyLong())).thenReturn(Optional.of(deliveryMan));
        doNothing().when(deliveryManRepository).delete(deliveryMan);

        mockMvc.perform(deleteRequest(CLIENT_CONTROLLER_PATH + "1"))
                .andExpect(status().isNoContent());
    }
}
