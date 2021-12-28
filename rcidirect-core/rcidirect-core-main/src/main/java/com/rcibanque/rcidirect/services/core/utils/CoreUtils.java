package com.rcibanque.rcidirect.services.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class CoreUtils {


	private CoreUtils() {
		super();
	}


	/**
	 * @param pList List
	 * @return Unique list element, or null if list is empty or has more than one element
	 */
	public static final <T> T getUniqueElement(List<T> pList) {

		return CollectionUtils.size(pList) != 1 ? null : pList.get(0);
	}


	/**
	 * @param pList List
	 * @return New empty list, with an initial <b>capacity</b> equal to the size of the parameter list (the result list itself is empty)
	 */
	public static final <T> List<T> newEmptyList(List<?> pList) {

		return new ArrayList<>(CollectionUtils.size(pList));
	}


	/**
	 * @param pTested Tested value
	 * @param pValues Possible values
	 * @return True if tested value is not null and equal to one of the possible values
	 */
	@SafeVarargs
	public static final <T> boolean isIn(T pTested, T... pValues) {
		boolean res = false;
		if(pTested != null) {
			for(T value : pValues) {
				if(pTested.equals(value)) {
					res = true;
					break;
				}
			}
		}
		return res;
	}


	/**
	 * @param pColl1 Collection 1
	 * @param pColl2 Collection 2
	 * @return True if collections are not empty and share common elements
	 */
	public static final <T> boolean hasIntersection(Collection<T> pColl1, Collection<T> pColl2) {
		boolean res = false;
		if(CollectionUtils.isNotEmpty(pColl1) && CollectionUtils.isNotEmpty(pColl2)) {
			res = CollectionUtils.isNotEmpty(CollectionUtils.intersection(pColl1, pColl2));
		}
		return res;
	}


	/**
	 * @param pCol Collection (may be null)
	 * @param pElt Element (may be null)
	 * @return New collection containing the union of the input collection and element
	 */
	public static final <T> Collection<T> union(Collection<T> pCol, T pElt) {
		Collection<T> res = new HashSet<>();
		if(pCol != null) {
			res.addAll(pCol);
		}
		CollectionUtils.addIgnoreNull(res, pElt);
		return res;
	}


}
