package com.sparta.feedconsumer.provider.repository.impl;

import com.sparta.feedconsumer.provider.model.Provider;
import com.sparta.feedconsumer.provider.model.Record;
import com.sparta.feedconsumer.provider.repository.ProviderRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProviderRepositoryImpl implements ProviderRepository {

    private final Map<String, List<Record>> dataSource = Collections.synchronizedMap(new HashMap<>());

    @Override
    public int upsert(Provider provider) {
        dataSource.computeIfAbsent(provider.getName(), $ -> new ArrayList<>()).addAll(provider.getRecords());

        return provider.getRecords().size();
    }

    @Override
    public List<Record> selectRecords(String providerName) {
        return dataSource.computeIfAbsent(providerName, $ -> new ArrayList<>());
    }
}
