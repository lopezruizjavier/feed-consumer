# Message format

```
message LoadBatch {
    int64 numberOfRecords;
    repeated Record records;
}

message Record {
    int32 recordIndex;
    int64 timestamp;
    String city;
    int32 numberBytesSensorData;  # Number of bytes used in following sensorData section
    SensorCollection sensorsData;
    int64 crc32SensorsData; # crc32 of all bytes present in the sensorData section
}

message String {
    int32 numberOfBytes; 
    byte[] bytesInUtf8; 
}

message SensorCollection {
    int32 numberOfSensors;
    repeated Sensor sensors;
}

message Sensor {
    String id;
    int32 measure;
}
```
