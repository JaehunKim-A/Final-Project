package com.team1.team1project.controller.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/geocode")
@Log4j2
public class GeocodeProxyController {

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${naver.client-id}")
	private String clientId;

	@Value("${naver.client-secret}")
	private String clientSecret;

	@GetMapping
	public ResponseEntity<?> getCoordinates(@RequestParam String query) {
		try {
			// 1. 헤더 설정
			HttpHeaders headers = new HttpHeaders();
			headers.set("x-ncp-apigw-api-key-id", clientId);
			headers.set("x-ncp-apigw-api-key", clientSecret);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.set("content-type", "application/json");

			// 2. 요청 URI 구성
			URI uri = UriComponentsBuilder
					.fromUriString("https://maps.apigw.ntruss.com/map-geocode/v2/geocode?query")
					.queryParam("query", UriUtils.encode(query, StandardCharsets.UTF_8))
					.build(true)
					.toUri();

			// 로그 확인용 출력
			log.info("📍 요청 URI: {}", uri);

			// 3. 요청 실행
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

			// 4. 결과 JSON 파싱 후 전달
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> json = mapper.readValue(response.getBody(), Map.class);

			log.info("✅ 응답 성공: {}", json);
			return ResponseEntity.ok(json);

		} catch (Exception e) {
			log.error("❌ Geocode API 호출 실패", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("errorMessage", "API 호출 실패: " + e.getMessage()));
		}
	}
}
