package com.sparta.feedconsumer.provider.model;

import com.github.javafaker.Faker;
import com.sparta.feedconsumer.util.AbstractTestBuilder;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class RecordBuilder extends AbstractTestBuilder<Record> {
    public static byte[] toByteArray(Record record, Long recordIndex) throws IOException {
        ByteArrayOutputStream baosRecord = new ByteArrayOutputStream();
        DataOutputStream dosRecord = new DataOutputStream(baosRecord);
        dosRecord.writeLong(recordIndex);
        dosRecord.writeLong(record.getTimestamp().getTime());
        dosRecord.writeInt(record.getCity().getBytes(StandardCharsets.UTF_8).length);
        dosRecord.write(record.getCity().getBytes(StandardCharsets.UTF_8));

        List<byte[]> sensors = record.getSensors().stream().map(sensor -> SensorBuilder.toByteArray(sensor)).collect(Collectors.toList());

        int numberBytesSensorData = sensors.stream().mapToInt(sensor -> sensor.length).sum() + Integer.BYTES;
        dosRecord.writeInt(numberBytesSensorData);

        ByteArrayOutputStream baosSensorData = new ByteArrayOutputStream();
        DataOutputStream dosSensorData = new DataOutputStream(baosSensorData);

        dosSensorData.writeInt(sensors.size());

        for (int i = 0; i < sensors.size(); i++) {
            dosSensorData.write(sensors.get(i));
        }

        final Checksum checksum = new CRC32();
        checksum.update(baosSensorData.size());

        dosRecord.write(baosSensorData.toByteArray());
        dosRecord.writeLong(checksum.getValue());

        dosSensorData.close();
        dosRecord.close();

        return baosRecord.toByteArray();
    }

    @Override
    protected void create() {
        object = new Record(
                Faker.instance().date().birthday(),
                Faker.instance().address().city(),
                Arrays.asList(new SensorBuilder().build())
        );
    }
}
