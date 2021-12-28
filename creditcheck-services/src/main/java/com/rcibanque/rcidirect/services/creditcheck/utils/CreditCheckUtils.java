package com.rcibanque.rcidirect.services.creditcheck.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rcibanque.rcidirect.services.agreements.domain.Agreement;
import com.rcibanque.rcidirect.services.creditcheck.domain.Title;
import com.rcibanque.rcidirect.services.finances.utils.FinanceConstants;

public class CreditCheckUtils {

	private CreditCheckUtils() {
	}

	public static boolean isServiceOnlyAgreement(Agreement agreement) {

		return FinanceConstants.FINANCE_TYPE_SERVICE_ONLY.equals(agreement.getFinancialDetail().getFinanceTypeCode());
	}

	public static Integer getGender(Integer pTitleCode) {

		return Title.valueOf(pTitleCode).getGenderCode();

	}

	public static String getMatchingVal(final String pTextToParse, Pattern pPattern) {

		String matchingVal = null;
		final Matcher matcher = pPattern.matcher(pTextToParse);
		if(matcher.find()) {
			matchingVal = matcher.group(1);
		}
		return matchingVal;
	}

}
