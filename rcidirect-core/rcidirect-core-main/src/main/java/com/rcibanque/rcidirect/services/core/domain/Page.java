package com.rcibanque.rcidirect.services.core.domain;

import java.io.Serializable;
import java.util.List;

public class Page<T extends Serializable> extends DTO {

	private static final long serialVersionUID = 2621644489950141202L;


	private Integer pageNumber;

	private Integer pagesAvailable;

	private List<T> pageItems;


	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pPageNumber) {
		pageNumber = pPageNumber;
	}

	public Integer getPagesAvailable() {
		return pagesAvailable;
	}

	public void setPagesAvailable(Integer pPagesAvailable) {
		pagesAvailable = pPagesAvailable;
	}

	public List<T> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<T> pPageItems) {
		pageItems = pPageItems;
	}



}
