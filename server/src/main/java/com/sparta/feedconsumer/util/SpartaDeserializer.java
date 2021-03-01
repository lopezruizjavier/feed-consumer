package com.sparta.feedconsumer.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class SpartaDeserializer {
    private SpartaDeserializer() {
        throw new UnsupportedOperationException("Not instantiable class!");
    }

    public static String getString(ByteBuffer content) {
        return new String(StandardCharsets.UTF_8.decode(getBytesAsByteBuffer(content)).array());
    }

    public static ByteBuffer getBytesAsByteBuffer(byte[] content) { return ByteBuffer.wrap(content); }

    public static ByteBuffer getBytesAsByteBuffer(ByteBuffer content) {
        return ByteBuffer.wrap(getBytes(content));
    }

    private static byte[] getBytes(ByteBuffer content) {
        int size = content.getInt();

        byte[] array = new byte[size];
        content.get(array);

        return array;
    }
}
