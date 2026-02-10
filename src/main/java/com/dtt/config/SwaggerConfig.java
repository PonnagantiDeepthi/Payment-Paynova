//package com.dtt.config;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.servers.Server;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import java.util.List;
//
//@Configuration
//public class SwaggerConfig {
//
//	@Bean
//	public OpenAPI paynovaOpenAPI() {
//		Server server = new Server();
//		server.setUrl("https://uaeid-stg.digitaltrusttech.com/payment-nova/PaymentNova");
//		server.setDescription("STG Server");
//
//		return new OpenAPI().servers(List.of(server))
//				.info(new Info().title("PayNova Payment API").version("1.0.0").description(
//						"Swagger documentation for PayNova integration APIs (Wallet, Card, Purchase, Transfer, Balance)")
//						.contact(new Contact().name("DigitalTrust Technologies"))
//						.license(new License().name("Private/Internal")));
//	}
//}
