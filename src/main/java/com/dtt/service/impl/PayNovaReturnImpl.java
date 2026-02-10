package com.dtt.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestParam;

import com.dtt.model.PaynovaReturnTransaction;
import com.dtt.repo.PaynovaReturnTransactionRepo;
import com.dtt.service.iface.PayNovaReturnIface;
import com.dtt.utils.AesDecryptor;
import com.dtt.utils.AppUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PayNovaReturnImpl implements PayNovaReturnIface {

	private static final Logger log = LoggerFactory.getLogger(PayNovaReturnImpl.class);
	private static final ObjectMapper mapper = new ObjectMapper();

	@Value("${paynova.encryption-key}")
	private String encryptionKey;

	@Value("${paynova.return-target-url}")
	private String targetUrl;

	@Autowired
	private PaynovaReturnTransactionRepo returnTransactionRepo;

	@Override
	public ResponseEntity<String> handleReturn(
			@RequestParam("PaymentTransactionSummary") String paymentTransactionSummary) {

		try {
			log.info("PaymentTransactionSummary received: {}", paymentTransactionSummary);
			String payload = paymentTransactionSummary.trim();

			if (!looksLikeJson(payload)) {
				payload = AesDecryptor.decryptBase64(payload, encryptionKey);
			}

			JsonNode root = mapper.readTree(payload);

			log.info("root return :: " + root);

			// Extract fields
			String clientReference = root.path("client_reference").asText();
			String paymentTxnRef = root.path("payment_transaction_reference").asText();
			int statusCode = root.path("status").asInt();
			String statusText = root.path("response_description").asText();
			BigDecimal totalAmount = root.path("total_amount").decimalValue();
			String responseDateTime = root.path("response_date_time").asText();

			String finalStatus = switch (statusCode) {
			case 2 -> "SUCCESS";
			case 3 -> "FAILED";
			case 4 -> "REVERSED";
			default -> "UNKNOWN";
			};

			// Save DB
			saveReturnTransaction(root, payload);

			// Build HTML
			String html = buildPaymentHtml(finalStatus, clientReference, paymentTxnRef, totalAmount, responseDateTime,
					statusText);

			return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_HTML)
					.body(errorHtml());
		}
	}

	/** Simple utility to detect if string is JSON-ish */
	private boolean looksLikeJson(String input) {
		if (input == null)
			return false;
		String trimmed = input.trim();
		return trimmed.startsWith("{") || trimmed.startsWith("[");
	}

	private void saveReturnTransaction(JsonNode root, String payload) {
		try {
			String txnRef = root.path("payment_transaction_reference").asText();

			PaynovaReturnTransaction entity = returnTransactionRepo.findByPaymentTransactionReference(txnRef)
					.orElse(new PaynovaReturnTransaction());

			entity.setPaymentTransactionReference(txnRef);
			entity.setClientReference(root.path("client_reference").asText(null));

			entity.setPaymentTransactionInstrument(root.path("payment_transaction_instrument").asInt());

			entity.setPaymentTransactionInstrumentName(root.path("payment_transaction_instrument_name").asText(null));

			entity.setStatus(root.path("status").asInt());
			entity.setStatusName(root.path("status_name").asText(null));

			entity.setServiceAmount(root.path("service_amount").decimalValue());
			entity.setTotalCharges(root.path("total_charges").decimalValue());
			entity.setTotalAmount(root.path("total_amount").decimalValue());

			entity.setCardNumber(root.path("card_number").asText(null));
			entity.setPayerCustomerCode(root.path("payer_customer_code").asText(null));

			entity.setClientExtra(root.path("client_extra").asText(null));

			entity.setResponseCode(root.path("response_code").asInt());
			entity.setResponseDescription(root.path("response_description").asText(null));

			// transaction_date (ISO format)
			if (!root.path("transaction_date").isMissingNode()) {
				entity.setTransactionDate(LocalDateTime.parse(root.path("transaction_date").asText()));
			}

			String reconDate = root.path("reconciliation_date").asText(null);

			entity.setReconciliationDate(AppUtils.parsePayNovaDateOrTodayUTC(reconDate));

			// response_date_time (yyyy-MM-dd HH:mm:ss)
			if (!root.path("response_date_time").isMissingNode()) {
				entity.setResponseDateTime(LocalDateTime.parse(root.path("response_date_time").asText(),
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			}

			// payment_reference inside array
			JsonNode payments = root.path("payment_transactions");
			if (payments.isArray() && payments.size() > 0) {
				entity.setPaymentReference(payments.get(0).path("payment_reference").asText(null));
			}
			entity.setCreatedOn(AppUtils.getCurrentDateTime());
			entity.setUpdatedOn(AppUtils.getCurrentDateTime());

			entity.setRawPayload(payload);
			entity.setSource("RETURN");

			returnTransactionRepo.save(entity);
		} catch (Exception e) {
			// Exception caught and handled
		}

	}

	private String buildPaymentHtml(String status, String transactionId, String gatewayTxnId, BigDecimal amount,
			String dateTime, String message) {

		boolean success = "SUCCESS".equals(status);
		String color = success ? "#28a745" : "#dc3545";
		String icon = success ? "✅" : "❌";

		return """
					        <!DOCTYPE html>
					        <html lang="en">
					        <head>
					            <meta charset="UTF-8">
					            <title>Payment Status</title>
					            <style>
					                body {
					                    font-family: Arial, sans-serif;
					                    background-color: #f4f6f9;
					                    display: flex;
					                    justify-content: center;
					                    align-items: center;
					                    height: 100vh;
					                }
					                .card {
					                    background: #fff;
					                    padding: 30px;
					                    border-radius: 12px;
					                    width: 420px;
					                    box-shadow: 0 4px 12px rgba(0,0,0,0.15);
					                    text-align: center;
					                }
					                .status {
					                    color: %s;
					                    font-size: 26px;
					                    font-weight: bold;
					                    margin-bottom: 20px;
					                }
					                .row {
					                    text-align: left;
					                    margin: 10px 0;
					                    font-size: 14px;
					                }
					                .label {
					                    font-weight: bold;
					                }
					                button {
					                    margin-top: 20px;
					                    padding: 8px 16px;
					                    border-radius: 6px;
					                    border: 1px solid #ccc;
					                    cursor: pointer;
					                }
					            </style>
					        </head>
					        <body>
					            <div class="card">
					                <div class="status">%s %s</div>
					                <div class="row"><span class="label">Transaction ID:</span> %s</div>
					                <div class="row"><span class="label">Gateway Ref:</span> %s</div>
					                <div class="row"><span class="label">Amount:</span> AED %s</div>
					                <div class="row"><span class="label">Date:</span> %s</div>
					                <div class="row"><span class="label">Message:</span> %s</div>

					                <button onclick="handleClose()">Close</button>
					            </div>

					            <script>
									function handleClose() {
				    					if (window.opener && !window.opener.closed) {
				        					window.opener.focus();
				        					window.close();
				    					} else {
				        					alert("Payment completed. You can safely close this tab.");
				    					}
									}
								</script>
					        </body>
					        </html>
					        """.formatted(color, icon, status, transactionId, gatewayTxnId, amount, dateTime, message);
	}

	private String errorHtml() {
		return """
				<html>
				<body style="font-family:Arial;text-align:center;margin-top:100px">
				    <h2 style="color:red;">Payment Processing Error</h2>
				    <p>Please contact support.</p>
				</body>
				</html>
				""";
	}

}
