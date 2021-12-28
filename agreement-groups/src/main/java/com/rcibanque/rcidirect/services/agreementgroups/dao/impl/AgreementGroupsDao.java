package com.rcibanque.rcidirect.services.agreementgroups.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.rcibanque.rcidirect.services.agreementgroups.dao.IAgreementGroupsDao;
import com.rcibanque.rcidirect.services.agreementgroups.dao.criteria.AgreementGroupCriteria;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroup;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroupAgreement;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.dealerselection.core.domain.DealershipActorLinkType;
import com.rcibanque.rcidirect.services.dealerselection.core.utils.DealerSelectionCriteriaUtils;
import com.rcibanque.rcidirect.services.general.parameters.dao.impl.CommonDao;

@Repository
public class AgreementGroupsDao extends CommonDao implements IAgreementGroupsDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgreementGroupsDao.class);


	@Override
	public List<AgreementGroup> getAgreementGroups(IContext pContext, AgreementGroupCriteria pCriteria) {

		LOGGER.debug("Get agreement groups");

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();
		sql.append(" select acg.ki_grcont, acg.ka_acteur, acg.k_societe, acg.tl_nomgroup, acg.fl_autorelf, acg.fl_autoprel, acg.fl_master_agreement ");
		sql.append(" from acgrcont acg ");
		sql.append(" where 1=1 ");

		if (StringUtils.isNotBlank(pCriteria.getCustomerActorCode())) {
			sql.append(" and acg.ka_acteur = :customerActorCode ");
			params.put("customerActorCode", pCriteria.getCustomerActorCode());
		}

		if (StringUtils.isNotBlank(pCriteria.getFinancialCompanyCode())) {
			sql.append(" and acg.k_societe = :financialCompanyCode ");
			params.put("financialCompanyCode", pCriteria.getFinancialCompanyCode());
		}

		if (pCriteria.getMasterAgreement() != null) {
			sql.append(" and acg.fl_master_agreement = :masterAgreement ");
			params.put("masterAgreement", pCriteria.getMasterAgreement());
		}

		if (pCriteria.getGroupId() != null) {
			sql.append(" and acg.ki_grcont = :groupID ");
			params.put("groupID", pCriteria.getGroupId());
		}

		// Dealer filtering
		if(StringUtils.isNotBlank(pCriteria.getDealerUserActorCode())) {

			sql.append(" and ka_acteur in ( ");
			sql.append("    select distinct ka_client ");
			sql.append("    from dossiers dos ");
			sql.append("    join acregact reg on reg.ka_fils = dos.ka_concess ");
			sql.append("    where klc_typlien in (:userDealershipLinks) and ka_pere = :userActorCode ");

			sql.append("    and ka_concess = coalesce(:dealershipActorCode, ka_concess) ");
			sql.append("    and ka_vendeur = coalesce(:salesExecutiveActorCode, ka_vendeur) ");
			sql.append(" ) ");

			params.put("userDealershipLinks", DealershipActorLinkType.getDealershipLinks());
			params.put("userActorCode", pCriteria.getDealerUserActorCode());
			params.put("dealershipActorCode", pCriteria.getDealershipActorCode());
			params.put("salesExecutiveActorCode", pCriteria.getSalesExecutiveActorCode());
		}

		return queryList(sql.toString(), params, GET_AGREEMENT_GROUPS_RM);
	}

	private static final RowMapper<AgreementGroup> GET_AGREEMENT_GROUPS_RM = (ResultSet rs, int rowNumber) -> {
		AgreementGroup res = new AgreementGroup();

		res.setGroupId(getLong(rs, "ki_grcont"));
		res.setCustomerActorCode(getString(rs, "ka_acteur"));
		res.setFinancialCompanyCode(getString(rs, "k_societe"));
		res.setGroupName(getString(rs, "tl_nomgroup"));
		res.setInvoicesGrouped(getBoolean(rs, "fl_autorelf"));
		res.setDirectDebitsGrouped(getBoolean(rs, "fl_autoprel"));
		res.setMasterAgreement(getBoolean(rs, "fl_master_agreement"));

		return res;
	};

	@Override
	public boolean checkCustomerActorAccess(IContext pContext, String pActorCode) {

		boolean res = true;

		AgreementGroupCriteria criteria = new AgreementGroupCriteria();
		DealerSelectionCriteriaUtils.setCriteria(pContext, criteria);

		// Dealer filtering
		if(StringUtils.isNotBlank(criteria.getDealerUserActorCode())) {

			Map<String, Object> params = newParamsMap();

			StringBuilder sql = new StringBuilder();
			sql.append(" select distinct 1 ");
			sql.append(" from dossiers dos ");
			sql.append(" join acregact reg on reg.ka_fils = dos.ka_concess ");
			sql.append(" where klc_typlien in (:userDealershipLinks) and ka_pere = :userActorCode ");

			sql.append(" and ka_concess = coalesce(:dealershipActorCode, ka_concess) ");
			sql.append(" and ka_vendeur = coalesce(:salesExecutiveActorCode, ka_vendeur) ");
			sql.append(" and ka_client = :customerActorCode ");

			params.put("userDealershipLinks", DealershipActorLinkType.getDealershipLinks());
			params.put("userActorCode", criteria.getDealerUserActorCode());
			params.put("dealershipActorCode", criteria.getDealershipActorCode());
			params.put("salesExecutiveActorCode", criteria.getSalesExecutiveActorCode());
			params.put("customerActorCode", pActorCode);

			res = BooleanUtils.isTrue(queryObject(sql.toString(), params, Boolean.class));
		}

		return res;
	}

	@Override
	public void createAgreementGroup(IContext pContext, AgreementGroup pAgreementGroup) {

		LOGGER.debug("Create agreement group");

		String sql = " insert into acgrcont ";
		sql += " (ka_acteur, k_societe, tl_nomgroup, fl_autorelf, fl_autoprel, fl_master_agreement) ";
		sql += " values ";
		sql += " (:customerActorCode, :financialCompanyCode, :groupName, :invoicesGrouped, :directDebitsGroupped, :masterAgreement) ";

		Map<String, Object> params = getParameters(pAgreementGroup);

		KeyHolder keyHolder = newKeyHolder();

		int res = update(sql, params, keyHolder);

		handleResult(res, pContext, "Could not insert Agreement Group");

		pAgreementGroup.setGroupId(keyHolder.getKey().longValue());
	}

	@Override
	public void updateAgreementGroup(IContext pContext, AgreementGroup pAgreementGroup) {

		LOGGER.debug("Update agreement group");

		String sql = " update acgrcont set ";
		sql += " tl_nomgroup = :groupName, ";
		sql += " fl_autorelf = :invoicesGrouped, ";
		sql += " fl_autoprel = :directDebitsGroupped ";

		sql += " where ki_grcont = :groupId ";

		Map<String, Object> params = getParameters(pAgreementGroup);

		int res = update(sql, params);

		handleResult(res, pContext, "Could not update Agreement Group {0}", pAgreementGroup.getGroupId());
	}

	private static Map<String, Object> getParameters(AgreementGroup pAgreementGroup) {
		Map<String, Object> params = newParamsMap();

		params.put("groupId", pAgreementGroup.getGroupId());
		params.put("customerActorCode", pAgreementGroup.getCustomerActorCode());
		params.put("financialCompanyCode", pAgreementGroup.getFinancialCompanyCode());
		params.put("groupName", pAgreementGroup.getGroupName());
		params.put("invoicesGrouped", BooleanUtils.isTrue(pAgreementGroup.getInvoicesGrouped()));
		params.put("directDebitsGroupped", BooleanUtils.isTrue(pAgreementGroup.getDirectDebitsGrouped()));
		params.put("masterAgreement", BooleanUtils.isTrue(pAgreementGroup.getMasterAgreement()));

		return params;
	}

	@Override
	public void deleteAgreementGroup(IContext pContext, Long pGroupId) {

		LOGGER.debug("Delete agreement group");

		String sql = " delete from acgrcont where ki_grcont = :groupId ";

		Map<String, Object> params = newParamsMap();
		params.put("groupId", pGroupId);

		int res = update(sql, params);

		handleResult(res, pContext, "Could not delete Agreement Group {0}", pGroupId);
	}


	@Override
	public List<AgreementGroupAgreement> getAgreementGroupAgreements(IContext pContext, AgreementGroupCriteria pCriteria) {

		LOGGER.debug("Get agreement group agreements");

		Map<String, Object> params = newParamsMap();

		StringBuilder sql = new StringBuilder();

		sql.append(" select dos.ki_contrat, dos.k_contrat, dos.kcd_statdos, dos.kcd_typfin, dos.kl3_numsi, ");
		sql.append(" db.kcd_nbie, m.tg_marque, db.lc_immat, loy.mt_tottt ");
		sql.append(" from dossiers dos ");
		sql.append(" inner join dobiendo dbd on dbd.k_nocontrat = dos.ki_contrat ");
		sql.append(" inner join dobiens db on db.ki_bien = dbd.k_nobien ");
		sql.append(" inner join pamarque m on m.klc_marque = db.klc_marque and m.kcd_langue = :languageCode ");
		sql.append(" inner join reprodco prod ON prod.klc_produit = dos.klc_produit ");
		sql.append(" inner join rebartyp bart on bart.kcd_barfi = prod.kcd_barfi ");
		sql.append(" left outer join doloyers loy on loy.k_nocontrat = dos.ki_contrat and loy.va_nbechea = (select max(va_nbechea) from doloyers loy2 where loy2.k_nocontrat = dos.ki_contrat) ");

		params.put("languageCode", pCriteria.getLanguageCode());

		if (pCriteria.getGroupId() != null) { // Agreements in the group

			sql.append(" inner join dogrcont dog on dog.k_nocontrat = dos.ki_contrat ");
			sql.append(" where dog.k_nogrcont = :groupId ");

			params.put("groupId", pCriteria.getGroupId());
		}
		else { // Agreements not in any group

			sql.append(" where dos.ka_client = :customerActorCode ");
			sql.append(" and dos.k_societe = :financialCompanyCode ");
			sql.append(" and not exists ( ");
			sql.append(" select 1 ");
			sql.append(" from acgrcont acg ");
			sql.append(" join dogrcont dog on dog.k_nogrcont = acg.ki_grcont ");
			sql.append(" where k_nocontrat = dos.ki_contrat) ");

			params.put("customerActorCode", pCriteria.getCustomerActorCode());
			params.put("financialCompanyCode", pCriteria.getFinancialCompanyCode());
		}

		return queryList(sql.toString(), params, GET_AGREEMENT_GROUP_AGREEMENTS_RM);
	}

	private static final RowMapper<AgreementGroupAgreement> GET_AGREEMENT_GROUP_AGREEMENTS_RM = (ResultSet rs, int rowNumber) -> {
		AgreementGroupAgreement res = new AgreementGroupAgreement();

		res.setAgreementId(getLong(rs, "ki_contrat"));
		res.setAgreementCode(getString(rs, "k_contrat"));
		res.setSimulationCode(getString(rs, "kl3_numsi"));
		res.setAgreementStatusCode(getInt(rs, "kcd_statdos"));
		res.setFinanceTypeCode(getInt(rs, "kcd_typfin"));
		res.setVehicleNatureCode(getInt(rs, "kcd_nbie"));
		res.setVehicleBrandLabel(getString(rs, "tg_marque"));
		res.setVehicleRegistration(StringUtils.trimToNull(getString(rs, "lc_immat")));
		res.setRentalAmount(getBigDecimal(rs, "mt_tottt"));

		return res;
	};

	@Override
	public void addAgreementToAgreementGroup(IContext pContext, Long pGroupId, Long pAgreementId) {

		LOGGER.debug("Add agreement to agreement group");

		String sql = " insert into dogrcont ";
		sql += " (k_nocontrat, k_nogrcont) ";
		sql += " values ";
		sql += " (:agreementId , :groupId) ";

		Map<String, Object> params = newParamsMap();
		params.put("groupId", pGroupId);
		params.put("agreementId", pAgreementId);

		int res = update(sql, params);

		handleResult(res, pContext, "Could not add Agreement Group for Group ID {0} and Agreement ID {1}", pGroupId, pAgreementId);
	}

	@Override
	public void removeAgreementFromAgreementGroup(IContext pContext, Long pGroupId, Long pAgreementId) {

		LOGGER.debug("Remove agreement from agreement group");

		String sql = " delete from dogrcont where k_nogrcont = :groupId and k_nocontrat = :agreementId ";

		Map<String, Object> params = newParamsMap();
		params.put("groupId", pGroupId);
		params.put("agreementId", pAgreementId);

		int res = update(sql, params);

		handleResult(res, pContext, "Could not remove Agreement Group for Group ID {0} and Agreement ID {1}", pGroupId, pAgreementId);
	}

	@Override
	public void updateAgreementWithAgreementGroup(IContext pContext, AgreementGroup pGroup, List<Long> pAgreementIds) {

		LOGGER.debug("Update agreement with agreement group");

		ListUtils.partition(pAgreementIds, 50).forEach(ids -> {

			String sql = " update dossiers set ";
			sql += " fl_grcont = :groupedAgreement, ";
			sql += " fl_grrelf = :groupedInvoices, ";
			sql += " fl_grprel = :groupedDirectDebits" ;
			sql += " where ki_contrat in (:agreementIds) ";

			Map<String, Object> params = newParamsMap();
			params.put("groupedAgreement", pGroup != null ? Boolean.TRUE : Boolean.FALSE);
			params.put("groupedInvoices", pGroup != null ? pGroup.getInvoicesGrouped() : Boolean.FALSE);
			params.put("groupedDirectDebits", pGroup != null ? pGroup.getDirectDebitsGrouped() : Boolean.FALSE);
			params.put("agreementIds", ids);

			int res = update(sql, params);

			handleResult(res, ids.size(), pContext, "Could not update Agreement with Agreement Group for Agreement IDs {0}", ids);
		});
	}

}
