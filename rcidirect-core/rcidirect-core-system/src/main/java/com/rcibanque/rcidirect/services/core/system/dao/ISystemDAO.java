package com.rcibanque.rcidirect.services.core.system.dao;

import java.util.Date;
import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.FunctionCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.Function;
import com.rcibanque.rcidirect.services.core.system.domain.FunctionDetails;
import com.rcibanque.rcidirect.services.core.system.domain.Language;
import com.rcibanque.rcidirect.services.core.system.domain.Site;

public interface ISystemDAO {

	Boolean isSystemInMaintenance();


	Date getCurrentDate();

	Date getCurrentDateUpdatedTime();


	List<FunctionDetails> getFunctions(IContext pContext, FunctionCriteria pCriteria);

	Boolean isFunctionEnabled(IContext pContext, Function pFunction);


	Site getSite();


	List<Language> getLanguages();

	Language getLanguage(Integer pLanguageCode);

}
