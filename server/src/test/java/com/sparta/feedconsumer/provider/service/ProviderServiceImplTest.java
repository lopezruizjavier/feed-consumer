package com.sparta.feedconsumer.provider.service;

import com.sparta.feedconsumer.provider.model.Provider;
import com.sparta.feedconsumer.provider.model.ProviderBuilder;
import com.sparta.feedconsumer.provider.repository.ProviderRepository;
import com.sparta.feedconsumer.provider.service.impl.ProviderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProviderServiceImplTest {

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderServiceImpl providerServiceImpl;

    @Test
    void testLoadData() throws IOException {
        int totalRecords = 5;
        Provider provider = new ProviderBuilder().addRecords(totalRecords).build();

        byte[] content = ProviderBuilder.toByteArray(provider);

        when(providerRepository.upsert(any(Provider.class))).thenReturn(provider.getRecords().size());

        int result = providerServiceImpl.loadData(provider.getName(), content);

        assertThat(result, is(equalTo(totalRecords)));
    }

    @Test
    void getTotalData() {
        int totalRecords = 5;
        Provider provider = new ProviderBuilder().addRecords(totalRecords).build();

        when(providerRepository.selectRecords(provider.getName())).thenReturn(provider.getRecords());

        int result = providerServiceImpl.getTotalData(provider.getName());

        assertThat(result, is(equalTo(totalRecords)));
    }
}
