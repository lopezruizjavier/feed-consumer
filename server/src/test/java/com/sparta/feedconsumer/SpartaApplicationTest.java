package com.sparta.feedconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class SpartaApplicationTest {
    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        SpartaApplication.main(new String[]{});

        assertThat(context, is(not(nullValue())));
    }
}
