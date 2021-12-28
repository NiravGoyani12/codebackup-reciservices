package com.rcibanque.rcidirect.services.core.utils;

public class ArrayUtils {


	private ArrayUtils() {}


	/**
	 * Changes the position of an element in an array (the element positions are switched)
	 *
	 * @param pArray Array
	 * @param pElement Element whose position is changed
	 * @param pPosition New position
	 */
	public static final <T> void switchPosition(T[] pArray, T pElement, int pPosition) {

		if (pArray == null || pArray.length <= pPosition || pArray[pPosition].equals(pElement)) {
			return;
		}

		T tmp = pArray[pPosition];
		pArray[pPosition] = pElement;

		for (int i = 0; i < pArray.length; i++) {
			if (pArray[i].equals(pElement) && i != pPosition) {
				pArray[i] = tmp;
				return;
			}
		}
	}

}
