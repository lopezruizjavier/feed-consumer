package com.sparta.feedconsumer.provider.repository;

import com.sparta.feedconsumer.provider.model.Provider;
import com.sparta.feedconsumer.provider.model.Record;

import java.util.List;

public interface ProviderRepository {
    int upsert(final Provider provider);

    List<Record> selectRecords(String providerName);
}
