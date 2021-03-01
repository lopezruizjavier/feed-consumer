# Welcome to Sparta Feed Consumer test!
 
We have organized a sandbox for you to show your Java skills to deal with a real life problem.
 
The project is composed by two parts, the client and the server:

 The client is a .jar provided by us, this client will perform two tasks: it will work as the feed that will send the data to the API and after feeding the server, it will request some data from the API.

 The server is the part you need to code, we have started a SpringBoot application and already defined the two endpoints you need to code: a POST method called 'load', that will receive the data from the client provided, and a GET method called 'total', that will be the method that the client will use to request the total of readings sent by provider.

The result of this exercise will be the base on which we will perform the technical interview, try to be clean and give a simple solution for what is requested, although the client is not going to request all the mapping data, the server will need to map it completely.

IMPORTANT NOTES:

- The client will try to connect to http://localhost:8080 to find the API.
- Do not use a database to store the data, just do it in memory.
- The format the data is being sent is at the end of this README.
- And just in case... to run the jar you need to execute: java -jar sparta-client.jar
 
If you have any question at anytime, do not hesitate to contact Luis on this email: luis@spartacommodities.com

# Message format

```
message LoadBatch {
    int64 numberOfRecords;
    repeated Record records;
}

message Record {
    int64 recordIndex;
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

