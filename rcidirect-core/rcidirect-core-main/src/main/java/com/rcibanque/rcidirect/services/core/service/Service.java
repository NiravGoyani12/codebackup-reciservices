package com.rcibanque.rcidirect.services.core.service;

import java.util.List;

import com.rcibanque.rcidirect.services.core.utils.CoreUtils;

/**
 * Business service
 * <ul>
 * <li>...</li>
 * </ul>
 */
public abstract class Service {


	/**
	 * @param pList List
	 * @return Unique list element, or null if list is empty or has more than one element
	 * @see CoreUtils#getUniqueElement
	 */
	protected static final <T> T getUniqueElement(List<T> pList) {

		return CoreUtils.getUniqueElement(pList);
	}


}
