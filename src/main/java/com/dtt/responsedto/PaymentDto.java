package com.dtt.responsedto;

import java.io.Serializable;
import java.util.List;

public class PaymentDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ServicePriceDto> services;

	public List<ServicePriceDto> getServices() {
		return services;
	}

	public void setServices(List<ServicePriceDto> services) {
		this.services = services;
	}

	@Override
	public String toString() {
		return "[services=" + services + "]";
	}
	

}
