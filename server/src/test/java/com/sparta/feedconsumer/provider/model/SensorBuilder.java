package com.sparta.feedconsumer.provider.model;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.github.javafaker.Faker;
import com.sparta.feedconsumer.util.AbstractTestBuilder;

import java.nio.charset.StandardCharsets;

public class SensorBuilder extends AbstractTestBuilder<Sensor> {
    public static byte[] toByteArray(Sensor sensor) {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();

        byte[] id = sensor.getId().getBytes(StandardCharsets.UTF_8);

        byteArrayBuilder.appendFourBytes(id.length);
        byteArrayBuilder.write(id);
        byteArrayBuilder.appendFourBytes(sensor.getMeasure());

        return byteArrayBuilder.toByteArray();
    }

    @Override
    protected void create() {
        object = new Sensor(
                Faker.instance().chuckNorris().fact(),
                Faker.instance().number().randomDigit()
        );
    }
}
