package com.sparta.feedconsumer.provider.model;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class Provider {
    private final String name;
    private final List<Record> records;

    public Provider(String name) {
        this.name = name;
        this.records = new ArrayList<>();
    }

    public Provider addBatch(ByteBuffer loadBatch) {
        LongStream.range(0, loadBatch.getLong()).forEach($ -> this.records.add(new Record(loadBatch)));

        return this;
    }

    public String getName() {
        return this.name;
    }

    public List<Record> getRecords() {
        return this.records;
    }
}
