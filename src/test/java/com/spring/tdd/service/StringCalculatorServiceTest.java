package com.spring.tdd.service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorServiceTest {

    private final StringCalculatorService stringCalculatorService = new StringCalculatorService();

    @Test
    public void testadd_WithDefaultDelimiter() {
        assertEquals(0, stringCalculatorService.add(""));
        assertEquals(1, stringCalculatorService.add("1"));
        assertEquals(6, stringCalculatorService.add("1,2,3"));
    }

    @Test
    public void testadd_WithNewLineDelimiter() {
        assertEquals(6, stringCalculatorService.add("1\n2,3"));
    }

    @Test
    public void testadd_WithCustomDelimiter() {
        // Custom delimiter ";"
        assertEquals(3, stringCalculatorService.add("//;\n1;2"));

        // Custom delimiter "|"
        assertEquals(10, stringCalculatorService.add("//|\n2|3|5"));

        // Custom delimiter "###"
        assertEquals(15, stringCalculatorService.add("//[###]\n5###5###5"));
    }

    @Test
    public void testadd_WithNegativeNumbers() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            stringCalculatorService.add("1,-2,3,-4");
        });
        assertEquals("Negative numbers not allowed: -2, -4", exception.getMessage());
    }

    @Test
    public void testadd_WithMultipleCustomDelimiters() {
        assertEquals(6, stringCalculatorService.add("//[;][%]\n1;2%3"));
    }
}
