package com.spring.tdd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class StringCalculatorService {

	public int add(String numbers) {
		if (numbers == null || numbers.isEmpty()) {
			return 0;
		}
		Pattern pattern = Pattern.compile("-?\\d+");
		Matcher matcher = pattern.matcher(numbers);

		int totalSum = 0;
		List<Integer> negatives = new ArrayList<>();
		while (matcher.find()) {
			int number = Integer.parseInt(matcher.group());
			if (number < 0) {
				negatives.add(number); // Collect negative numbers
			} else if (number <= 1000) {
				totalSum += number; // Add to sum for non-negative numbers

			}
		}
		if (!negatives.isEmpty()) {
			throw new IllegalArgumentException(
					"Negative numbers not allowed: " + negatives.toString().replaceAll("[\\[\\]]", ""));
		}

		// Print the total sum of non-negative numbers
		System.out.println("Total sum of non-negative numbers: " + totalSum);

		return totalSum;
	}

}
