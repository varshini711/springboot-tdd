package com.spring.tdd.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StringCalculatorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAdditionWithValidInput() {
        String numbers = "1,2,3";
        ResponseEntity<Integer> response = restTemplate.postForEntity("/calculator/add", numbers, Integer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(6, response.getBody());
    }

    @Test
    public void testAdditionWithCustomDelimiter() {
        String numbers = "//;\n1;2";
        ResponseEntity<Integer> response = restTemplate.postForEntity("/calculator/add", numbers, Integer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody());
    }
    
    @Test
    public void testAdditionWithCustomDelimiter1() {
        String numbers = "//;\n1;&2,5%";
        ResponseEntity<Integer> response = restTemplate.postForEntity("/calculator/add", numbers, Integer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(8, response.getBody());
    }
    
    @Test
    public void testAdditionWithCustomDelimiter2() {
        String numbers = "//;1,-2,-3,9,0,1,-8,-5";
        ResponseEntity<String> response = restTemplate.postForEntity("/calculator/add", numbers, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Negative numbers not allowed: -2, -3, -8, -5", response.getBody());
    }
    @Test
    public void testAdditionWithCustomDelimiter3() {
        String numbers = "//[*][%]\\n1*2%3";
        ResponseEntity<Integer> response = restTemplate.postForEntity("/calculator/add", numbers, Integer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(6, response.getBody());
    }
    @Test
    public void testAdditionWithCustomDelimiter4() {
        String numbers = "//[***]\\n1***2***3";
        ResponseEntity<Integer> response = restTemplate.postForEntity("/calculator/add", numbers, Integer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(6, response.getBody());
    }
    @Test
    public void testAdditionWithCustomDelimiter5() {
        String numbers = "//[*][%]\\n1*2%3,1000";
        ResponseEntity<Integer> response = restTemplate.postForEntity("/calculator/add", numbers, Integer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1006, response.getBody());
    }
    @Test
    public void testAdditionWithCustomDelimiter6() {
        String numbers = "//[*][%]\\n1*2%3,1001";
        ResponseEntity<Integer> response = restTemplate.postForEntity("/calculator/add", numbers, Integer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(6, response.getBody());
    }

    @Test
    public void testAdditionWithNegativeNumbers() {
        String numbers = "1,-2,3";
        ResponseEntity<String> response = restTemplate.postForEntity("/calculator/add", numbers, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Negative numbers not allowed: -2", response.getBody());
    }

    @Test
    public void testAdditionWithEmptyInput() {
        String numbers = "";
        ResponseEntity<Integer> response = restTemplate.postForEntity("/calculator/add", numbers, Integer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody());
    }
}
