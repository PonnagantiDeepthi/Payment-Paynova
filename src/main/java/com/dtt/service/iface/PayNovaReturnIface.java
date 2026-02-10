package com.dtt.service.iface;

import org.springframework.http.ResponseEntity;


public interface PayNovaReturnIface {
	
	public ResponseEntity<String> handleReturn(String resturnResponse);

}
