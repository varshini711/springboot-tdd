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
		String delimiter = ",";
		String numbersWithoutDelimiter = numbers;

		if (numbers.startsWith("//")) {
			int delimiterEndIndex = numbers.indexOf('\n');
			if (delimiterEndIndex != -1) {

				String delimiterSection = numbers.substring(2, delimiterEndIndex);
				if (delimiterSection.startsWith("[") && delimiterSection.endsWith("]")) {

					delimiter = extractMultiDelimiters(delimiterSection);
				} else {

					delimiter = Pattern.quote(delimiterSection);
				}
				numbersWithoutDelimiter = numbers.substring(delimiterEndIndex + 1);
			}
		}

		numbersWithoutDelimiter = numbersWithoutDelimiter.replace("\n", ",").replaceAll(delimiter, ",");
		String cleaned = numbers.replaceAll("[\n\t]+", " ").trim();

		cleaned = cleaned.replaceAll("[^0-9,-]+", " ");

		cleaned = cleaned.replaceAll("[, ]+", ",");

		cleaned = cleaned.replaceAll("(^,)|(,$)", "").trim();

		cleaned = cleaned.replaceAll(",+", ",");

		System.out.println("Processed string: " + cleaned);

		String[] parts = cleaned.split(",");
		List<Integer> negatives = new ArrayList<>();
		int sum = 0;

		for (String part : parts) {
			part = part.trim();
			if (part.isEmpty()) {
				continue;
			}
			try {
				int number = Integer.parseInt(part);

				if (number < 0) {
					negatives.add(number);
				} else if (number <= 1000) {
					sum += number;
				}
			} catch (NumberFormatException e) {

				throw new IllegalArgumentException("Invalid input: " + part);
			}
		}

		if (!negatives.isEmpty()) {
			throw new IllegalArgumentException(
					"Negative numbers not allowed: " + negatives.toString().replaceAll("[\\[\\]]", ""));
		}

		return sum;
	}

	private String extractMultiDelimiters(String delimiterSection) {
		StringBuilder delimiterPattern = new StringBuilder();
		Matcher matcher = Pattern.compile("\\[(.*?)\\]").matcher(delimiterSection);
		while (matcher.find()) {
			String delimiter = Pattern.quote(matcher.group(1));
			if (delimiterPattern.length() > 0) {
				delimiterPattern.append("|");
			}
			delimiterPattern.append(delimiter);
		}
		return delimiterPattern.toString();
	}
}
