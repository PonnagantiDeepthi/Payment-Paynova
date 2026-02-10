package com.dtt.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.additionalInterceptors((request, body, execution) -> {
			System.out.println("Calling: " + request.getURI());
			return execution.execute(request, body);
		}).build();
	}

}
