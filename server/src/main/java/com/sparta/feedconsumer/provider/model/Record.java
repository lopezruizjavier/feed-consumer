package com.sparta.feedconsumer.provider.model;

import com.sparta.feedconsumer.util.SpartaDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Record {
    private static final Logger LOGGER = LoggerFactory.getLogger(Record.class);

    private final Date timestamp;
    private final String city;
    private final List<Sensor> sensors;

    public Record(Date timestamp, String city, List<Sensor> sensors) {
        this.timestamp = timestamp;
        this.city = city;
        this.sensors = sensors;
    }

    public Record(ByteBuffer content) {
        content.getLong(); // Drop recordIndex field
        this.timestamp = new Date(content.getLong());
        this.city = SpartaDeserializer.getString(content);
        this.sensors = new ArrayList<>();

        ByteBuffer sensorsData = SpartaDeserializer.getBytesAsByteBuffer(content);

        final Checksum checksum = getCRC32(sensorsData);

        LongStream.range(0, sensorsData.getInt()).forEach($ -> sensors.add(new Sensor(sensorsData)));

        this.validateSensorsData(checksum.getValue(), content.getLong());
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getCity() {
        return city;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    private Checksum getCRC32(ByteBuffer sensorsData) {
        final Checksum checksum = new CRC32();
        checksum.update(sensorsData);

        sensorsData.position(0);

        return checksum;
    }

    private void validateSensorsData(long checksum, long crc32SensorsData) {
        if (checksum != crc32SensorsData) {
            LOGGER.error("Invalid checksum, cleaning sensor data for {}", this.city);

            this.sensors.clear();
        }
    }
}
