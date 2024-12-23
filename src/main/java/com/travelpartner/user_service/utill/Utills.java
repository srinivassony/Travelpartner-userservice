package com.travelpartner.user_service.utill;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utills {

	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public String generateString(int length) {
		Random random = new Random();
		StringBuilder builder = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
		}

		return builder.toString().toUpperCase();
	}

	public String getShortDateFormayte() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		return sdf.format(timestamp);
	}

	public String getStackTraceAsString(Exception e) {
		// StringBuilder to accumulate the stack trace as a string
		StringBuilder sb = new StringBuilder();
	
		// Append the exception message itself (optional, but useful to provide the exception's description)
		sb.append("Exception: ").append(e.toString()).append("\n");
	
		// Iterate through each stack trace element and append it to the StringBuilder
		for (StackTraceElement element : e.getStackTrace()) {
			// Each element represents a single line in the stack trace
			sb.append("\tat ").append(element.toString()).append("\n");
		}
	
		// If there are any cause details for the exception, append that as well
		Throwable cause = e.getCause();
		if (cause != null) {
			sb.append("Caused by: ").append(cause.toString()).append("\n");
			for (StackTraceElement element : cause.getStackTrace()) {
				sb.append("\tat ").append(element.toString()).append("\n");
			}
		}
	
		// Return the accumulated stack trace string
		return sb.toString();
	}
	
}
