package com.emorinken.review.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ReviewTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Review getReviewSample1() {
        return new Review().id(1L).reviewText("reviewText1").reviewerName("reviewerName1").bookIsbn("bookIsbn1");
    }

    public static Review getReviewSample2() {
        return new Review().id(2L).reviewText("reviewText2").reviewerName("reviewerName2").bookIsbn("bookIsbn2");
    }

    public static Review getReviewRandomSampleGenerator() {
        return new Review()
            .id(longCount.incrementAndGet())
            .reviewText(UUID.randomUUID().toString())
            .reviewerName(UUID.randomUUID().toString())
            .bookIsbn(UUID.randomUUID().toString());
    }
}
