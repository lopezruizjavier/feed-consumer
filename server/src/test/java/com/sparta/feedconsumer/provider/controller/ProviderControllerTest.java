package com.sparta.feedconsumer.provider.controller;

import com.github.javafaker.Faker;
import com.sparta.feedconsumer.provider.model.Provider;
import com.sparta.feedconsumer.provider.model.ProviderBuilder;
import com.sparta.feedconsumer.provider.service.ProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProviderController.class)
class ProviderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProviderService providerService;

    @Test
    void testLoad() throws Exception {
        Integer totalMessages = 10;
        Provider fakeProvider = new ProviderBuilder().addRecords(totalMessages).build();
        byte[] content = ProviderBuilder.toByteArray(fakeProvider);

        when(providerService.loadData(fakeProvider.getName(), content)).thenReturn(totalMessages);

        mockMvc.perform(post("/load/{provider}", fakeProvider.getName()).content(content))
                .andExpect(status().isOk())
                .andExpect(content().string(totalMessages.toString()));
    }

    @Test
    void testTotal() throws Exception {
        String fakeProvider = Faker.instance().backToTheFuture().character();
        Integer totalMessages = 10;

        when(providerService.getTotalData(fakeProvider)).thenReturn(totalMessages);

        mockMvc.perform(get("/data/{provider}/total", fakeProvider))
                .andExpect(status().isOk())
                .andExpect(content().string(totalMessages.toString()));
    }
}
