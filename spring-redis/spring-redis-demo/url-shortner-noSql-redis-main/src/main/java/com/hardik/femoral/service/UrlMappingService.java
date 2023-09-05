package com.hardik.femoral.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UrlMappingService {

	private final UrlValidator urlValidator;

	private final RedisTemplate redisTemplate;

	public boolean isValidUrl(final String originalUrlValue) {
		return urlValidator.isValid(originalUrlValue);
	}

	public String getOriginalUrl(final String urlId) {
		final var urlMapping = (String) redisTemplate.opsForValue().get(urlId);
		return urlMapping;
	}

	public String createShortUrl(String originalUrlValue) {
		String code = new RandomStringUtils().randomAlphabetic(8).toLowerCase();
		boolean result = false;
		while (!result) {
			code = new RandomStringUtils().randomAlphabetic(8).toLowerCase();
			result = redisTemplate.opsForValue().setIfAbsent(code, originalUrlValue);
		}
		return code;
	}

}
