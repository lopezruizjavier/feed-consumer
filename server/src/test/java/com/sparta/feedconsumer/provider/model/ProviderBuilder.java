package com.sparta.feedconsumer.provider.model;

import com.github.javafaker.Faker;
import com.sparta.feedconsumer.util.AbstractTestBuilder;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.stream.IntStream;

public class ProviderBuilder extends AbstractTestBuilder<Provider> {

    public static byte[] toByteArray(Provider provider) throws IOException {
        ByteArrayOutputStream baosProvider = new ByteArrayOutputStream();
        DataOutputStream dosProvider = new DataOutputStream(baosProvider);
        dosProvider.writeLong(provider.getRecords().size());

        for (int i = 0; i < provider.getRecords().size(); i++) {
            dosProvider.write(RecordBuilder.toByteArray(provider.getRecords().get(i), (long) i));
        }

        dosProvider.close();

        return baosProvider.toByteArray();
    }

    @Override
    protected void create() {
        object = new Provider(Faker.instance().company().name());
    }

    public ProviderBuilder addRecord() {
        object.getRecords().add(new RecordBuilder().build());

        return this;
    }

    public ProviderBuilder addRecords(int n) {
        IntStream.range(0, n).forEach($ -> addRecord());

        return this;
    }
}
