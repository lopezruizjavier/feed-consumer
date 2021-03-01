package com.sparta.feedconsumer.provider.model;

import com.sparta.feedconsumer.util.SpartaDeserializer;

import java.nio.ByteBuffer;

public class Sensor {
    private final String id;
    private final Integer measure;

    public Sensor(String id, Integer measure) {
        this.id = id;
        this.measure = measure;
    }

    public Sensor(ByteBuffer content) {
        this.id = SpartaDeserializer.getString(content);
        this.measure = content.getInt();
    }

    public String getId() {
        return id;
    }

    public Integer getMeasure() {
        return measure;
    }
}
