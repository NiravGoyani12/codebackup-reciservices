package com.rcibanque.rcidirect.services.core.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class IteratorUtils {


	private IteratorUtils() {}


	/**
	 * Return a null-safe iterable
	 *
	 * @param pArray Array or null
	 * @return Iterable
	 */
	public static <T> Iterable<T> nullableIterable(T[] pArray) {

		List<T> list = pArray == null ? null : Arrays.asList(pArray);

		return nullableIterable(list);
	}


	/**
	 * Return a null-safe iterable
	 *
	 * @param pIterable Iterable or null
	 * @return Iterable
	 */
	public static <T> Iterable<T> nullableIterable(Iterable<T> pIterable) {

		return pIterable == null ? Collections.<T>emptySet() : pIterable;
	}


	/**
	 * Return a null-safe iterable
	 *
	 * @param pMap Map or null
	 * @return Iterable
	 */
	public static <K, T> Iterable<Entry<K,T>> nullableIterable(Map<K, T> pMap) {

		return pMap == null ? Collections.<K,T>emptyMap().entrySet() : pMap.entrySet();
	}


	/**
	 * Return the first element of an iterable
	 *
	 * @param pIterable Iterable
	 * @return First element
	 */
	public static <T> T firstElement(Iterable<T> pIterable) {

		return element(pIterable, true);
	}


	/**
	 * Return the last element of an iterable
	 *
	 * @param pIterable Iterable
	 * @return Last element
	 */
	public static <T> T lastElement(Iterable<T> pIterable) {

		return element(pIterable, false);
	}


	private static <T> T element(Iterable<T> pIterable, boolean pFirst) {

		T res = null;

		if(pIterable != null) {
			for(T t : pIterable) {
				res = t;
				if(pFirst) {
					break;
				}
			}
		}

		return res;
	}

}
