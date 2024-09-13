package com.spring.tdd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.tdd.service.StringCalculatorService;

@RestController
@RequestMapping("/calculator")
public class StringCalculatorController {
	
	@Autowired
	private StringCalculatorService calculatorService;

	@PostMapping("/add")
	public ResponseEntity<?> additionOfString(@RequestBody(required = false) String numbers) {
		try {
			int result = calculatorService.add(numbers);
			return ResponseEntity.ok(result);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
