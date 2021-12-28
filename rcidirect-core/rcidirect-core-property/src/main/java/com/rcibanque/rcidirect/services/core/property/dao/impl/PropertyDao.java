package com.rcibanque.rcidirect.services.core.property.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rcibanque.rcidirect.services.core.dao.DAO;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.property.IProperty;
import com.rcibanque.rcidirect.services.core.property.dao.IPropertyDao;
import com.rcibanque.rcidirect.services.core.property.dao.criteria.PropertyCriteria;
import com.rcibanque.rcidirect.services.core.property.domain.Property;
import com.rcibanque.rcidirect.services.core.utils.ConvertUtils;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;

@Repository
public class PropertyDao extends DAO implements IPropertyDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyDao.class);


	@Override
	public List<Property> getProperties(IContext pContext, PropertyCriteria pCriteria) {

		if(pCriteria.getParentReferenceKey() == null && CollectionUtils.isEmpty(pCriteria.getParentReferenceKeys())) {
			throw new IllegalStateException("No parent reference key has been set");
		}

		PropertyTableMetaData ptmd = pCriteria.getPropertyTableMetaData();

		Map<String, Object> params = newParamsMap();

		StringBuilder sql  = new StringBuilder();
		sql.append(" select ").append(ptmd.getParentReferenceColumnName()).append(" as parentRef, klc_property, co_value ");
		sql.append(" from ").append(ptmd.getPropertyTableName()).append(" ");
		sql.append(" where 1=1 ");

		if(pCriteria.getParentReferenceKey() != null) {
			sql.append(" and ").append(ptmd.getParentReferenceColumnName()).append(" = :parentReferenceKey ");
			params.put("parentReferenceKey", pCriteria.getParentReferenceKey());
		}
		if(CollectionUtils.isNotEmpty(pCriteria.getParentReferenceKeys())) {
			sql.append(" and ").append(ptmd.getParentReferenceColumnName()).append(" in (:parentReferenceKeys) ");
			params.put("parentReferenceKeys", pCriteria.getParentReferenceKeys());
		}
		if(pCriteria.getPropertyMetaData() != null) {
			sql.append(" and klc_property = :propertyName ");
			params.put("propertyName", pCriteria.getPropertyMetaData().getPropertyName());
		}

		List<Property> res = queryList(sql.toString(), params, GET_PROPERTY_VALUE);
		List<Property> resKnowPropertiesOnly = new ArrayList<>(); // Unknown properties (i.e. for which we dom't know the meta-data) are not handled

		// Parent reference key (II/II) : convert the string representation of the key from stage I into a value with the proper type
		for(Property property : IteratorUtils.nullableIterable(res)) {
			property.setParentReferenceKey(getParentReferenceKey(ptmd.getParentReferenceColumnType(), (String) property.getParentReferenceKey()));
		}

		// Add meta data (II/II) : convert the minimal meta-data from stage I into the actual meta-data
		for(Property property : IteratorUtils.nullableIterable(res)) {
			IProperty pmd = ptmd.get(property.getPropertyName());
			if(pmd != null) {
				property.setPropertyMetaData(pmd.getPropertyMetaData());
				resKnowPropertiesOnly.add(property);
			}
			else {
				LOGGER.warn("Unsupported property: {}.{}", ptmd.getPropertyTableName(), property.getPropertyName());
			}
		}

		return resKnowPropertiesOnly;
	}

	private static final RowMapper<Property> GET_PROPERTY_VALUE = (ResultSet rs, int rowNumber) -> {

		Property property = new Property();

		// Parent reference key (I/II) : get a string representation of the key, converted into the actual type in stage II
		property.setParentReferenceKey(getString(rs, "parentRef"));

		// Add meta data (I/II): create a minimal meta-data, converted into the actual meta-data in stage II
		property.setPropertyMetaData(new PropertyMetaData(null, getString(rs, "klc_property")));

		property.setValue(getString(rs, "co_value"));

		return property;
	};

	private static Serializable getParentReferenceKey(KeyType pKeyType, String pParentReferenceKey) {
		Serializable res = null;

		switch(pKeyType) {
		case STRING:
			res = pParentReferenceKey;
			break;
		case INTEGER:
			res = ConvertUtils.parseInteger(pParentReferenceKey);
			break;
		case LONG:
			res = ConvertUtils.parseLong(pParentReferenceKey);
			break;
		default:
			throw new IllegalArgumentException("Unsupported property parent reference type");
		}

		return res;
	}


	private void insertProperty(IContext pContext, Property pProperty) {

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ").append(pProperty.getPropertyTableName()).append(" ");
		sql.append(" ( ").append(pProperty.getParentReferenceColumnName()).append(", klc_property, co_value) ");
		sql.append(" values ( :parentReferenceKey, :propertyName, :propertyValue )");

		params.put("parentReferenceKey", pProperty.getParentReferenceKey());
		params.put("propertyName", pProperty.getPropertyName());
		params.put("propertyValue", pProperty.getValue());

		int res = update(sql.toString(), params);

		handleResult(res, pContext, "Could not insert property {0} into {1} table",
				pProperty.getPropertyName(),
				pProperty.getPropertyTableName());
	}


	private void deleteProperty(IContext pContext, PropertyCriteria pCriteria) {

		PropertyMetaData pmd = pCriteria.getPropertyMetaData();
		PropertyTableMetaData ptmd = pCriteria.getPropertyTableMetaData();

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" delete from ").append(ptmd.getPropertyTableName()).append(" ");
		sql.append(" where ").append(ptmd.getParentReferenceColumnName()).append(" = :parentReferenceKey ");
		sql.append(" and klc_property = :propertyName ");

		if(StringUtils.isNotEmpty(pCriteria.getValue())) {
			sql.append(" and co_value = :value ");
			params.put("value", pCriteria.getValue());
		}

		params.put("parentReferenceKey", pCriteria.getParentReferenceKey());
		params.put("propertyName", pmd.getPropertyName());

		int res = update(sql.toString(), params);

		handleResult(res, pContext, "Could not delete property {0} from {1} table",
				pmd.getPropertyName(),
				ptmd.getPropertyTableName());
	}


	private void updateProperty(IContext pContext, Property pProperty) {

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" update ").append(pProperty.getPropertyTableName()).append(" ");
		sql.append(" set co_value = :propertyValue ");
		sql.append(" where ").append(pProperty.getParentReferenceColumnName()).append(" = :parentReferenceKey ");
		sql.append(" and klc_property = :propertyName ");

		params.put("parentReferenceKey", pProperty.getParentReferenceKey());
		params.put("propertyName", pProperty.getPropertyName());
		params.put("propertyValue", pProperty.getValue());

		int res = update(sql.toString(), params);

		handleResult(res, pContext, "Could not update property {0} in {1} table",
				pProperty.getPropertyName(),
				pProperty.getPropertyTableName());
	}


	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Integer pValue) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(new Object[]{pValue}), false);
	}

	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Integer[] pValues) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(pValues), true);
	}


	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Long pValue) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(new Object[]{pValue}), false);
	}

	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Long[] pValues) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(pValues), true);
	}


	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Double pValue) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(new Object[]{pValue}), false);
	}

	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Double[] pValues) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(pValues), true);
	}


	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, BigDecimal pValue) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(new Object[]{pValue}), false);
	}

	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, BigDecimal[] pValues) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(pValues), true);
	}


	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Boolean pValue) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(new Object[]{pValue}), false);
	}

	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Boolean[] pValues) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(pValues), true);
	}


	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Date pValue) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(new Object[]{pValue}), false);
	}

	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, Date[] pValues) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(pValues), true);
	}


	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, String pValue) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(new Object[]{pValue}), false);
	}

	@Override
	public void updateOrInsert(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, String[] pValues) {

		save(pContext, pProperty, pParentReferenceKey, toStringArray(pValues), true);
	}


	private void save(IContext pContext, IProperty pProperty, Serializable pParentReferenceKey, String[] pValues, boolean pMultiple) {

		// Get existing properties
		PropertyCriteria criteria = new PropertyCriteria();
		criteria.setLanguageCode(pContext.getLanguageCode());
		criteria.setPropertyMetaData(pProperty.getPropertyMetaData());
		criteria.setParentReferenceKey(pParentReferenceKey);

		List<Property> existingProperties = getProperties(pContext, criteria);

		// Single and multiple properties cannot be handled the same way
		if(! pMultiple) {

			if((pValues != null && pValues.length > 1) || (CollectionUtils.isNotEmpty(existingProperties) && existingProperties.size() > 1)) {
				throw new IllegalStateException("Single valued property has multiple property values");
			}
			String value = pValues != null && pValues.length > 0 ? pValues[0] : null;
			Property existingProperty = CollectionUtils.isEmpty(existingProperties) ? null : existingProperties.get(0);

			boolean valueDefined = value != null; // 'null' instead of 'StringUtils.isNotEmpty(pValue)'

			// Update, insert or delete (depending on whether or not the property already exists and whether or not the value is defined)
			if(existingProperty == null) {
				if(valueDefined) {
					Property newProperty = new Property(pProperty.getPropertyMetaData(), pParentReferenceKey, value);
					insertProperty(pContext, newProperty);
				}
			}
			else {
				if(valueDefined) {
					existingProperty.setValue(value);
					updateProperty(pContext, existingProperty);
				}
				else {
					deleteProperty(pContext, criteria);
				}
			}
		}
		else {

			boolean valuesDefined = ArrayUtils.isNotEmpty(pValues);

			if(! valuesDefined && CollectionUtils.isNotEmpty(existingProperties)) {

				// Delete existing properties that do not exist anymore
				deleteProperty(pContext, criteria);
			}
			else {

				// Delete existing properties that do not exist anymore
				for(Property existingProperty : existingProperties) {
					boolean exists = false;
					for(String value : pValues) {
						if(existingProperty.getValue() == value || existingProperty.getValue().equals(value)) {
							exists = true;
							break;
						}
					}
					if(! exists) {
						criteria.setValue(existingProperty.getValue());
						deleteProperty(pContext, criteria);
						criteria.setValue(null);
					}
				}

				// Create properties that do not yet exist
				for(String value : pValues) {
					boolean exists = false;
					for(Property existingProperty : existingProperties) {
						if(existingProperty.getValue() == value || existingProperty.getValue().equals(value)) {
							exists = true;
							break;
						}
					}
					if(! exists) {
						Property newProperty = new Property(pProperty.getPropertyMetaData(), pParentReferenceKey, value);
						insertProperty(pContext, newProperty);
					}
				}
			}
		}
	}

	private static String[] toStringArray(Object[] pValues) {
		String[] res = new String[pValues.length];

		for(int i=0; i<pValues.length; i++) {
			Object value = pValues[i];

			String stringValue = null;
			if(value != null) {
				if(value instanceof Integer) {
					stringValue = ConvertUtils.toString((Integer) value);
				}
				else if(value instanceof Long) {
					stringValue = ConvertUtils.toString((Long) value);
				}
				else if(value instanceof Double) {
					stringValue = ConvertUtils.toString((Double) value);
				}
				else if(value instanceof BigDecimal) {
					stringValue = ConvertUtils.toString((BigDecimal) value);
				}
				else if(value instanceof Boolean) {
					stringValue = ConvertUtils.toString((Boolean) value);
				}
				else if(value instanceof Date) {
					stringValue = ConvertUtils.toStringProperties((Date) value);
				}
				else if(value instanceof String) {
					stringValue = (String) value;
				}
				else {
					throw new IllegalArgumentException("Unsupported property type");
				}
			}
			res[i] = stringValue;
		}
		return res;
	}


}
