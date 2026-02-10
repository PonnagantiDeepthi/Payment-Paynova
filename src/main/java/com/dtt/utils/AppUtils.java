package com.dtt.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class AppUtils {

	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final DateTimeFormatter DEFAULT_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static String generateUniqueId() {
		// UUID uuid = UUID.randomUUID();
		// return uuid.toString();

		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	// ✅ Get current UTC date-time in system zone
	public static Instant getCurrentDateTime() {
		Instant utcNow = Instant.now();
		return utcNow;
	}

	public static LocalDate parsePayNovaDateOrTodayUTC(String value) {

		LocalDate todayUtc = LocalDate.now(ZoneOffset.UTC);

		if (value == null || value.isBlank() || "null".equalsIgnoreCase(value) || value.startsWith("0001-01-01")) {
			return todayUtc;
		}
		try {
			if (value.length() == 10 && value.charAt(4) == '-' && value.charAt(7) == '-') {
				return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
			}

			if (value.contains("+") || value.endsWith("Z")) {
				return OffsetDateTime.parse(value).toLocalDate();
			}

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd['T'HH:mm:ss[.SSS][.SS]]");

			return LocalDateTime.parse(value, formatter).toLocalDate();

		} catch (Exception e) {
			e.printStackTrace();
			return todayUtc;
		}
	}

}
