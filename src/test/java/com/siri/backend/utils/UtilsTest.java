package com.siri.backend.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    public void testGenerateAlphanumericCode() {
        final int SIZE = 25;
        String result = Utils.generateAlphanumericCode(SIZE);
        Assertions.assertEquals(result.length(), SIZE);
    }
}