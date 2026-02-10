package com.dtt.responsedto;

import java.io.Serializable;

public class PaymentRefundResponseDto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -891810913016689755L;
	private Integer responseCode;
    private String responseDatetime;
    private String responseDescription;

    public Integer getResponseCode() {
        return responseCode;
    }
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDatetime() {
        return responseDatetime;
    }
    public void setResponseDatetime(String responseDatetime) {
        this.responseDatetime = responseDatetime;
    }

    public String getResponseDescription() {
        return responseDescription;
    }
    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }
	@Override
	public String toString() {
		return " [responseCode=" + responseCode + ", responseDatetime=" + responseDatetime
				+ ", responseDescription=" + responseDescription + "]";
	}
}

