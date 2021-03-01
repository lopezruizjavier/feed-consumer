package com.sparta.feedconsumer.provider.service;

public interface ProviderService {
    int loadData(String providerName, byte[] content);

    int getTotalData(String providerName);
}
