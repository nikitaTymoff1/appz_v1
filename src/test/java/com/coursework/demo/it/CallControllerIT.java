package com.coursework.demo.it;

import com.coursework.demo.dto.CallDTO;
import com.coursework.demo.entity.Call;
import com.coursework.demo.repository.CallRepository;
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

import static com.coursework.demo.TestData.getCall;
import static com.coursework.demo.TestData.getCallRequest;
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
public class CallControllerIT {

    private static final String CALL_CONTROLLER_PATH = "/v1/calls/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CallRepository callRepository;

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveCallById() throws Exception {
        when(callRepository.findById(anyLong())).thenReturn(Optional.of(getCall()));

        mockMvc.perform(getRequest(CALL_CONTROLLER_PATH + "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(getCallRequest())));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRetrieveCallList() throws Exception {
        final Call call = getCall();
        final List<Call> calls = Collections.singletonList(call);
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        final Page<Call> callPage = new PageImpl<>(calls, pageable, 10);

        when(callRepository.findAll(pageable)).thenReturn(callPage);

        mockMvc.perform(getRequest(CALL_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(Collections.singletonList(getCallRequest()))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSaveCall() throws Exception {
        final Call call = getCall();
        final CallDTO request = getCallRequest();

        when(callRepository.save(any(Call.class))).thenReturn(call);

        mockMvc.perform(postRequest(CALL_CONTROLLER_PATH, request))
                .andExpect(status().isCreated())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCall() throws Exception {
        final Call call = getCall();
        final CallDTO request = getCallRequest();

        when(callRepository.save(call)).thenReturn(call);

        mockMvc.perform(putRequest(CALL_CONTROLLER_PATH + "1", request))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(request)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCallExpectedBadRequest() throws Exception {
        final Call call = getCall();
        final CallDTO request = getCallRequest();

        when(callRepository.save(call)).thenReturn(call);

        mockMvc.perform(putRequest(CALL_CONTROLLER_PATH + "2", request))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCall() throws Exception {
        final Call call = getCall();

        when(callRepository.findById(anyLong())).thenReturn(Optional.of(call));
        doNothing().when(callRepository).delete(call);

        mockMvc.perform(deleteRequest(CALL_CONTROLLER_PATH + "1"))
                .andExpect(status().isNoContent());
    }
}
