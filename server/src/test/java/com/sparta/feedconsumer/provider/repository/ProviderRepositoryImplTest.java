package com.sparta.feedconsumer.provider.repository;

import com.sparta.feedconsumer.provider.model.Provider;
import com.sparta.feedconsumer.provider.model.ProviderBuilder;
import com.sparta.feedconsumer.provider.repository.impl.ProviderRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class ProviderRepositoryImplTest {

    private ProviderRepository providerRepository;

    @BeforeEach
    public void setup() {
        providerRepository = new ProviderRepositoryImpl();
    }

    @Test
    void testUpsert() {
        Provider provider = new ProviderBuilder().addRecords(5).build();

        int result = providerRepository.upsert(provider);

        assertThat(result, is(equalTo(provider.getRecords().size())));
    }

    @Test
    void testSelectRecords() {
        Provider provider = new ProviderBuilder().build();

        providerRepository.upsert(provider);
        providerRepository.selectRecords(provider.getName());

        assertThat(providerRepository.selectRecords(provider.getName()).size(), is(equalTo(provider.getRecords().size())));
    }
}
