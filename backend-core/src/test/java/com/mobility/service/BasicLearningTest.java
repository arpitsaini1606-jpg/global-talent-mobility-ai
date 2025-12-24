package com.mobility.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * --- LEARNING NOTE: JUnit 5 ---
 * 1. @Test: Tells JUnit that this method is a test case.
 * 2. Assertions: Methods like assertEquals or assertTrue that check
 * if the code behaves as expected.
 * 3. AAA Pattern: Arrange (setup data), Act (call the method),
 * Assert (check the results).
 * ---
 */
public class BasicLearningTest {

    @Test
    public void testSimpleAddition() {
        // Arrange
        int a = 5;
        int b = 10;

        // Act
        int result = a + b;

        // Assert
        assertEquals(15, result, "The sum of 5 and 10 should be 15");
    }

    @Test
    public void testStringHandling() {
        String name = "GlobalTalent";
        assertTrue(name.startsWith("Global"), "Name should start with Global");
    }
}
