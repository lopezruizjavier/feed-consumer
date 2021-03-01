package com.sparta.feedconsumer.provider.service.impl;

import com.sparta.feedconsumer.provider.model.Provider;
import com.sparta.feedconsumer.provider.repository.ProviderRepository;
import com.sparta.feedconsumer.provider.service.ProviderService;
import com.sparta.feedconsumer.util.SpartaDeserializer;
import com.sparta.feedconsumer.util.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProviderServiceImpl implements ProviderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderServiceImpl.class);

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    @Transactional
    public int loadData(String providerName, byte[] content) {
        LOGGER.debug("Loading data for provider {} with {}", providerName, content.length);

        Provider provider = new Provider(providerName).addBatch(SpartaDeserializer.getBytesAsByteBuffer(content));

        providerRepository.upsert(provider);

        final int processedRecords = provider.getRecords().size();

        LOGGER.debug("Processed {} records", processedRecords);

        return processedRecords;
    }

    @Override
    @Transactional
    public int getTotalData(String providerName) {
        LOGGER.debug("Searching messages from provider {}", providerName);

        final int totalMessages = providerRepository.selectRecords(providerName).size();

        LOGGER.debug("Total messages from provider {}: {}", providerName, totalMessages);

        return totalMessages;
    }
}
