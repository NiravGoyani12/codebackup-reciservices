package com.rcibanque.rcidirect.services.core.system.service;

import java.util.Date;
import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.FunctionCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.Function;
import com.rcibanque.rcidirect.services.core.system.domain.FunctionDetails;
import com.rcibanque.rcidirect.services.core.system.domain.Language;
import com.rcibanque.rcidirect.services.core.system.domain.Site;

public interface ISystemService {

	Date getCurrentDate();

	Date getCurrentDateUpdatedTime();


	List<FunctionDetails> getFunctions(IContext pContext, FunctionCriteria pCriteria);

	boolean isFunctionEnabled(IContext pContext, Function pFunction);


	List<Language> getLanguages(IContext pContext);

	Language getLanguage(IContext pContext, Integer pLanguageCode);


	Site getSite();

}
