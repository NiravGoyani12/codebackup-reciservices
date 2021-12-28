package com.rcibanque.rcidirect.services.core.system.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.rcibanque.rcidirect.services.core.dao.DAO;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.system.dao.ILabelDao;
import com.rcibanque.rcidirect.services.core.system.dao.criteria.LabelCriteria;
import com.rcibanque.rcidirect.services.core.system.domain.Label;

@Repository
public class LabelDao extends DAO implements ILabelDao {


	@Override
	@Cacheable(value = "labelsCache", key = "#pCriteria")
	public List<Label> getLabels(IContext pContext, LabelCriteria pCriteria) {
		Assert.notNull(pCriteria.getLanguageCode(), "Language code is mandatory");

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" select kcd_numlib, kcd_typlib, kcd_codtyplib, klc_codtyplib, k_societe, kcd_typcib, tg_libelle ");
		sql.append(" from palibelle ");
		sql.append(" where kcd_langue = :languageCode ");

		params.put("languageCode", pCriteria.getLanguageCode());

		if(pCriteria.getLabelCode() != null) {
			sql.append(" and kcd_numlib = :labelCode ");
			params.put("labelCode", pCriteria.getLabelCode());
		}

		if(pCriteria.getLabelTypeCode() != null) {
			sql.append(" and kcd_typlib = :labelTypeCode ");
			params.put("labelTypeCode", pCriteria.getLabelTypeCode());
		}

		if(pCriteria.getEntityCodeInteger() != null) {
			sql.append(" and kcd_codtyplib = :entityCodeInteger ");
			params.put("entityCodeInteger", pCriteria.getEntityCodeInteger());
		}

		if(pCriteria.getEntityCodeString() != null) {
			sql.append(" and klc_codtyplib = :entityCodeString ");
			params.put("entityCodeString", pCriteria.getEntityCodeString());
		}

		if(pCriteria.getFinancialCompanyCode() != null) {
			sql.append(" and k_societe = :financialCompanyCode ");
			params.put("financialCompanyCode", pCriteria.getFinancialCompanyCode());
		}

		if(pCriteria.getLabelCategoryCode() != null) {
			sql.append(" and kcd_typcib = :labelCategoryCode ");
			params.put("labelCategoryCode", pCriteria.getLabelCategoryCode());
		}

		if(pCriteria.getLabel() != null) {
			sql.append(" and tg_libelle = :label ");
			params.put("label", pCriteria.getLabel());
		}

		return queryList(sql.toString(), params, GET_LABELS_RM);
	}

	private static final RowMapper<Label> GET_LABELS_RM = (ResultSet rs, int rowNumber) -> {

		Label res = new Label();

		res.setLabelCode(getLong(rs, "kcd_numlib"));
		res.setLabelTypeCode(getInt(rs, "kcd_typlib"));
		res.setEntityCodeInteger(getInt(rs, "kcd_codtyplib"));
		res.setEntityCodeString(getString(rs, "klc_codtyplib"));
		res.setFinancialCompanyCode(getString(rs, "k_societe"));
		res.setLabelCategoryCode(getInt(rs, "kcd_typcib"));
		res.setLabel(getString(rs, "tg_libelle"));

		return res;
	};

}
