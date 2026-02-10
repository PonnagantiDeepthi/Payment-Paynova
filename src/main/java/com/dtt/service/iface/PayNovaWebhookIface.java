package com.dtt.service.iface;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;

public interface PayNovaWebhookIface {

	ResponseEntity<String> handleWebhook(JsonNode payload);

}
