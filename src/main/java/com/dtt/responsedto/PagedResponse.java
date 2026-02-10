package com.dtt.responsedto;

import java.io.Serializable;
import java.util.List;

public class PagedResponse<T> implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<T> content;
    private PageMeta page;
    
	public PagedResponse(List<T> content, PageMeta page) {
		super();
		this.content = content;
		this.page = page;
	}
	public List<T> getContent() {
		return content;
	}
	public void setContent(List<T> content) {
		this.content = content;
	}
	public PageMeta getPage() {
		return page;
	}
	public void setPage(PageMeta page) {
		this.page = page;
	}
	@Override
	public String toString() {
		return "s[content=" + content + ", page=" + page + "]";
	}
    
}