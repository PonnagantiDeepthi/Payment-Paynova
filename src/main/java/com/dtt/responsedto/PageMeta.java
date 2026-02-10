package com.dtt.responsedto;

import java.io.Serializable;

public class PageMeta implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int size;
    private int number;
    private long totalElements;
    private int totalPages;
    
    
	public PageMeta(int size, int number, long totalElements, int totalPages) {
		super();
		this.size = size;
		this.number = number;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	@Override
	public String toString() {
		return "[size=" + size + ", number=" + number + ", totalElements=" + totalElements + ", totalPages="
				+ totalPages + "]";
	}
	
    
}