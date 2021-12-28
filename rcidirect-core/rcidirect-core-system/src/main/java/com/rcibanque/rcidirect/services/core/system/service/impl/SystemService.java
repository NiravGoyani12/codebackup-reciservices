package com.rcibanque.rcidirect.services.core.system.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.ISystemDAO;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.FunctionCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.Function;
import com.rcibanque.rcidirect.services.core.system.domain.FunctionDetails;
import com.rcibanque.rcidirect.services.core.system.domain.Language;
import com.rcibanque.rcidirect.services.core.system.domain.Site;
import com.rcibanque.rcidirect.services.core.system.service.ISystemService;

@Service
public class SystemService extends com.rcibanque.rcidirect.services.core.service.Service implements ISystemService {


	@Autowired
	private ISystemDAO _systemDAO;


	@Override
	public Date getCurrentDate() {

		return _systemDAO.getCurrentDate();
	}


	@Override
	public Date getCurrentDateUpdatedTime() {

		return _systemDAO.getCurrentDateUpdatedTime();
	}


	@Override
	public List<FunctionDetails> getFunctions(IContext pContext, FunctionCriteria pCriteria) {

		return _systemDAO.getFunctions(pContext, pCriteria);
	}


	@Override
	public boolean isFunctionEnabled(IContext pContext, Function pFunction) {

		return BooleanUtils.isTrue(_systemDAO.isFunctionEnabled(pContext, pFunction));
	}


	@Override
	public List<Language> getLanguages(IContext pContext) {

		return _systemDAO.getLanguages();
	}


	@Override
	public Language getLanguage(IContext pContext, Integer pLanguageCode) {

		return _systemDAO.getLanguage(pLanguageCode);
	}


	@Override
	public Site getSite() {

		return _systemDAO.getSite();
	}


}
