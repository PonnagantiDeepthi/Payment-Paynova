package com.dtt.controller;

import com.dtt.responsedto.ApiResponse;
import com.dtt.event.MetricsUpdatedEvent;
import com.dtt.service.iface.LogMetricsIface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/paynova/log")
//@RequestMapping("/log")
@CrossOrigin
public class LogController {

	@Autowired
	LogMetricsIface logMetricsIface;

	private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

	@GetMapping("/payments/metrics")
	public ApiResponse getPaymentMetrics() {
		return logMetricsIface.getPaymentMetrics();
	}

	@GetMapping("/get/transaction/count")
	public ApiResponse getTransactionCount() {
		return logMetricsIface.getTransactionCount();
	}

	@GetMapping(value = "/payments/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter streamMetrics() {

		SseEmitter emitter = new SseEmitter(0L);
		emitters.add(emitter);

		emitter.onCompletion(() -> emitters.remove(emitter));
		emitter.onTimeout(() -> emitters.remove(emitter));
		emitter.onError((e) -> emitters.remove(emitter));

		// ✅ Don't call DB here
		try {
			emitter.send(SseEmitter.event().name("connected").data("SSE Connected"));
		} catch (IOException e) {
			emitter.complete();
		}

		return emitter;
	}

//    @GetMapping(value = "/payments/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public SseEmitter streamMetrics() {
//        // Create an emitter with no timeout (or set a specific one)
//        //SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
//        SseEmitter emitter = new SseEmitter(0L);
//
//        this.emitters.add(emitter);
//
//        // Cleanup on connection close
//        emitter.onCompletion(() -> this.emitters.remove(emitter));
//        emitter.onTimeout(() -> this.emitters.remove(emitter));
//        emitter.onError((e) -> this.emitters.remove(emitter));
//
//        // Optional: Send initial data immediately upon connection
//        try {
//            emitter.send(SseEmitter.event()
//                    .data(logMetricsIface.getPaymentMetrics()));
//        } catch (IOException e) {
//            emitter.complete();
//        }
//
//        return emitter;
//    }

	@EventListener
	public void onMetricsUpdated(MetricsUpdatedEvent event) {
		List<SseEmitter> deadEmitters = new CopyOnWriteArrayList<>();

		for (SseEmitter emitter : emitters) {
			try {
				emitter.send(SseEmitter.event().name("metrics-update").data(event.getMetrics()));
			} catch (IOException e) {
				deadEmitters.add(emitter);
			}
		}
		emitters.removeAll(deadEmitters);
	}

}
