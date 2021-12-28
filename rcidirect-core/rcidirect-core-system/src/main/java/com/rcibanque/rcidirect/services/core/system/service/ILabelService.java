package com.rcibanque.rcidirect.services.core.system.service;

import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.LabelCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.Label;

public interface ILabelService {


	List<Label> getLabels(IContext pContext, LabelCriteria pCriteria);

	boolean exists(IContext pContext, LabelCriteria pCriteria);


}
