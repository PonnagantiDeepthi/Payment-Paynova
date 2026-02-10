package com.dtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class PaymentNovaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentNovaApplication.class, args);
	}

}
