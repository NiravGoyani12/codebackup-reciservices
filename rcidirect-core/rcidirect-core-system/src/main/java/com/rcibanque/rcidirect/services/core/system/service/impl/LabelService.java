package com.rcibanque.rcidirect.services.core.system.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.ILabelDao;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.LabelCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.Label;
import com.rcibanque.rcidirect.services.core.system.service.ILabelService;

@Service
public class LabelService extends com.rcibanque.rcidirect.services.core.service.Service implements ILabelService {


	@Autowired
	private ILabelDao _labelDao;


	@Override
	public List<Label> getLabels(IContext pContext, LabelCriteria pCriteria) {

		return _labelDao.getLabels(pContext, pCriteria);
	}


	@Override
	public boolean exists(IContext pContext, LabelCriteria pCriteria) {

		return CollectionUtils.isNotEmpty(getLabels(pContext, pCriteria));
	}


}
