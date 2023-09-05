package com.hardik.femoral.controller;

import java.net.URI;
import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.femoral.service.UrlMappingService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/url")
@AllArgsConstructor
public class UrlController {

	private final UrlMappingService urlMappingService;

	@PostMapping
	public ResponseEntity<?> createShortUrl(@RequestBody(required = true) final String originalUrlValue) {
		final var response = new JSONObject();
		if (urlMappingService.isValidUrl(originalUrlValue)) {
			final var code = urlMappingService.createShortUrl(originalUrlValue);
			response.put("code", code);
			response.put("originalUrl", originalUrlValue);
			response.put("message", "SuccessFully Shortened Given Url");
			response.put("timestamp", LocalDateTime.now().toString());
			return ResponseEntity.ok(response.toString());

		} else {
			response.put("message", "URL Entered Is Not A Valid Http or Https URL");
			response.put("timestamp", LocalDateTime.now().toString());
			return ResponseEntity.badRequest().body(response.toString());
		}
	}

	@GetMapping("/{urlId}")
	public ResponseEntity<?> redirect(@PathVariable(name = "urlId", required = true) final String urlId) {
		final var redirectUrl = urlMappingService.getOriginalUrl(urlId);
		if (redirectUrl.isEmpty()) {
			final var response = new JSONObject();
			response.put("message", "Incorrect Short Url Id Provided");
			response.put("timestamp", LocalDateTime.now().toString());
			return ResponseEntity.badRequest().body(response.toString());
		}
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
	}

}
