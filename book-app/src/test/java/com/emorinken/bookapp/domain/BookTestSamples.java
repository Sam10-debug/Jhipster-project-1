package com.emorinken.bookapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BookTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Book getBookSample1() {
        return new Book().id(1L).title("title1").author("author1").isbn("isbn1");
    }

    public static Book getBookSample2() {
        return new Book().id(2L).title("title2").author("author2").isbn("isbn2");
    }

    public static Book getBookRandomSampleGenerator() {
        return new Book()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .author(UUID.randomUUID().toString())
            .isbn(UUID.randomUUID().toString());
    }
}
