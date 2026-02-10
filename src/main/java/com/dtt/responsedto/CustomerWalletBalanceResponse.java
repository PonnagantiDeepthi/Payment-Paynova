package com.dtt.responsedto;

import java.io.Serializable;

import com.dtt.model.CustomerEnrollment;

public class CustomerWalletBalanceResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private WalletBalanceResponse walletBalanceResponse;
	private CustomerEnrollment customerEnrollment;
	public WalletBalanceResponse getWalletBalanceResponse() {
		return walletBalanceResponse;
	}
	public void setWalletBalanceResponse(WalletBalanceResponse walletBalanceResponse) {
		this.walletBalanceResponse = walletBalanceResponse;
	}
	public CustomerEnrollment getCustomerEnrollment() {
		return customerEnrollment;
	}
	public void setCustomerEnrollment(CustomerEnrollment customerEnrollment) {
		this.customerEnrollment = customerEnrollment;
	}
	@Override
	public String toString() {
		return "[walletBalanceResponse=" + walletBalanceResponse + ", customerEnrollment="
				+ customerEnrollment + "]";
	}

}
