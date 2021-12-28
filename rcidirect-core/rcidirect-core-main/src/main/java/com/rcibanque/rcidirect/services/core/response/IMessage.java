package com.rcibanque.rcidirect.services.core.response;

import java.util.Locale;

import com.rcibanque.rcidirect.services.core.utils.MessageBundle;

public interface IMessage {

	/**
	 * Add  details concerning a field
	 *
	 * @param pLocale User locale
	 * @param pDomainName Field domain name
	 * @param pFieldName Field name
	 * @param pReferenceId Field reference ID
	 * @param pBundle Message bundle
	 * @param pKey Message key
	 * @param peParams Message parameters
	 * @return
	 */
	IMessage addDetail(Locale pLocale,
			String pDomainName, String pFieldName, String pReferenceId,
			MessageBundle pBundle, String pKey, Object... pParams);

}
