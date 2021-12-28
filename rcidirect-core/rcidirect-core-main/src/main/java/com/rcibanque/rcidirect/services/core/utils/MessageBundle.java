package com.rcibanque.rcidirect.services.core.utils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import com.rcibanque.rcidirect.services.core.domain.DateTime;


public class MessageBundle {

	private static final boolean CUSTOM_MESSAGE_PARAMETER_FORMATTING = true;

	private final String _baseName;


	/**
	 * @param pBaseName The full path to the messages file, without the '.properties' file extension
	 */
	public MessageBundle(String pBaseName) {
		super();
		_baseName = pBaseName;
	}


	private ResourceBundle getResourceBundle(Locale pLocale) {

		return ResourceBundle.getBundle(_baseName, pLocale);
	}


	/**
	 * @param pLocale Locale
	 * @param pMessageKey Message key
	 * @param pParameters Message parameters
	 * @return Message
	 */
	public final String getMessage(Locale pLocale, String pMessageKey, Object ... pParameters) {

		return getMessage(getResourceBundle(pLocale).getString(pMessageKey), pParameters);
	}


	/**
	 * @param pMessage Message (containing eventual parameter annotations)
	 * @param pParameters Message parameters
	 * @return Message
	 */
	public static final String getMessage(String pMessage, Object ... pParameters) {

		return MessageFormat.format(pMessage, getParameters(pParameters));
	}


	/**
	 * The default display of {@link MessageFormat} for some objects (for example Long, Integer) is not satisfying
	 *
	 * @param pParameters Object array
	 * @return String array
	 */
	private static Object[] getParameters(Object[] pParameters) {

		Object[] res = new Object[pParameters == null ? 0 : pParameters.length];

		for(int i=0; pParameters != null && i<pParameters.length; i++) {

			Object parameter = pParameters[i];
			Object value = parameter;

			if(CUSTOM_MESSAGE_PARAMETER_FORMATTING && parameter != null) {

				if(parameter.getClass().equals(Long.class)) {
					value = ConvertUtils.toString((Long) parameter);
				}
				else if(parameter.getClass().equals(Integer.class)) {
					value = ConvertUtils.toString((Integer) parameter);
				}
				else if(parameter.getClass().equals(BigDecimal.class)) {
					value = ConvertUtils.toString(MathUtils.round2((BigDecimal) parameter));
				}
				else if(parameter.getClass().equals(Double.class)) {
					value = ConvertUtils.toString((Double) parameter);
				}
				else if(parameter.getClass().equals(DateTime.class)) {
					value = ConvertUtils.toString((DateTime) parameter);
				}
				else if(parameter.getClass().equals(Date.class)) {
					value = ConvertUtils.toString((Date) parameter);
				}
			}

			res[i] = value;
		}

		return res;
	}


}
