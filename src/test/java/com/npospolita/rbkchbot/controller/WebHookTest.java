package com.npospolita.rbkchbot.controller;

import com.npospolita.rbkchbot.service.UpdateProcessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WebHookTest {

    @Value("${telegram.bot.token}")
    String botToken;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UpdateProcessService updateProcessService;

    @Test
    void webHookControllerIsWorking() throws Exception {
        mockMvc.perform(
                post(URI.create("http://localhost:8080/" + botToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .content("{'test':'kek'}"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(updateProcessService, times(1)).process(any());
    }
}
