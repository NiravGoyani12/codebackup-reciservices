package com.rcibanque.rcidirect.services.core.property.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.property.IProperty;
import com.rcibanque.rcidirect.services.core.property.dao.criteria.PropertyCriteria;
import com.rcibanque.rcidirect.services.core.property.domain.Property;

public interface IPropertyDao {


	List<Property> getProperties(IContext pContext, PropertyCriteria pCriteria);


	// TODO Rename to 'save' once code is on a single branch


	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Integer pValue);

	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Integer[] pValues);


	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Long pValue);

	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Long[] pValues);


	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Double pValue);

	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Double[] pValues);


	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, BigDecimal pValue);

	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, BigDecimal[] pValues);


	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Boolean pValue);

	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Boolean[] pValues);


	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Date pValue);

	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Date[] pValues);


	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, String pValue);

	void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, String[] pValues);


}
