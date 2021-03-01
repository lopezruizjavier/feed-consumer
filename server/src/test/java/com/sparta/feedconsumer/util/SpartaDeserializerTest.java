package com.sparta.feedconsumer.util;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SpartaDeserializerTest extends AbstractUtilityBaseTester<SpartaDeserializer> {
    @Test
    void testGetString() {
        String fakeString = Faker.instance().gameOfThrones().character();
        ByteBuffer content = this.buildByteBufferString(fakeString);

        String result = SpartaDeserializer.getString(content);

        assertThat(result, is(equalTo(fakeString)));
    }

    @Test
    void testGetBytesAsByteBufferWithByteArray() {
        String fakeString = Faker.instance().gameOfThrones().character();
        ByteBuffer content = this.buildByteBufferString(fakeString);

        ByteBuffer result = SpartaDeserializer.getBytesAsByteBuffer(content.array());

        assertThat(result, is(equalTo(content)));
    }

    @Test
    void testGetBytesAsByteBufferWithByteBuffer() {
        String fakeString = Faker.instance().gameOfThrones().character();
        ByteBuffer content = this.buildByteBufferString(fakeString);

        ByteBuffer result = SpartaDeserializer.getBytesAsByteBuffer(content);

        assertThat(result.array(), is(equalTo(fakeString.getBytes(StandardCharsets.UTF_8))));
    }

    private ByteBuffer buildByteBufferString(String fakeString) {
        byte[] bytes = fakeString.getBytes(StandardCharsets.UTF_8);
        ByteBuffer content = ByteBuffer.allocate(bytes.length + Integer.BYTES);
        content.putInt(fakeString.length());
        content.put(bytes);
        content.position(0);

        return content;
    }
}
