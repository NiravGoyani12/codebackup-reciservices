package com.rcibanque.rcidirect.services.agreementgroups.resource.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithUserDetails;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroup;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroupAgreement;
import com.rcibanque.rcidirect.services.core.config.domain.Market;
import com.rcibanque.rcidirect.services.core.response.APIResponse;
import com.rcibanque.rcidirect.services.core.test.annotation.RunIf;
import com.rcibanque.rcidirect.services.core.test.rest.AbstractResourceTest;
import com.rcibanque.rcidirect.services.core.test.rest.Users;
import com.rcibanque.rcidirect.services.core.utils.TextUtils;
import com.rcibanque.rcidirect.services.dealerselection.core.domain.IDealerSelection;

@WithUserDetails(value = Users.ADMIN)
class AgreementGroupResourceTest extends AbstractResourceTest {


	private static boolean INITIALISED;

	private static Long GROUP_ID;

	private static String CUSTOMER_ACTOR_CODE;

	private static String FINANCIAL_COMPANY_CODE;


	@BeforeEach
	private void beforeEach() throws IOException {

		if(! INITIALISED) {

			IDealerSelection ds = Users.getDealerSelection(_env, Users.ADMIN);

			Map<String, Object> params = new HashMap<>();
			params.put("salesExecutiveActorCode", ds.getSelectedSalesExecutive().getActorCode());

			Object[] existingGroupData = _dao.queryObjectFromResource("/sql/GetExistingMasterAgreementGroup.sql", params, (ResultSet rs, int rowNumber) -> {
				return new Object[] {
						rs.getLong("ki_grcont"),
						rs.getString("ka_acteur"),
						rs.getString("k_societe")
				};
			});

			GROUP_ID = (Long) existingGroupData[0];
			CUSTOMER_ACTOR_CODE = (String) existingGroupData[1];
			FINANCIAL_COMPANY_CODE = (String) existingGroupData[2];

			INITIALISED = true;
		}
	}


	@Test
	@RunIf(Market.ie)
	void shouldGetAgreementGroupsByCustomerActorCode() {
		try {

			APIResponse<List<AgreementGroup>> groupsResponse = performGet("/agreement-groups" + "?customer_actor_code=" + CUSTOMER_ACTOR_CODE, HttpStatus.OK, getListOfAgreementGroupsReference());

			List<AgreementGroup> groups = groupsResponse.getData();
			assertThat(groups).isNotEmpty();
			groups.forEach(g -> testAgreementGroup(g));
			assertThat(groups).allMatch(ag -> ag.getCustomerActorCode().equals(CUSTOMER_ACTOR_CODE));

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldGetAgreementGroupsByCustomerActorCodeAndFinancialCompanyCode() {
		try {

			APIResponse<List<AgreementGroup>> groupsResponse = performGet("/agreement-groups" + "?customer_actor_code=" + CUSTOMER_ACTOR_CODE + "&financial_company_code=" + FINANCIAL_COMPANY_CODE, HttpStatus.OK, getListOfAgreementGroupsReference());

			List<AgreementGroup> groups = groupsResponse.getData();
			assertThat(groups).isNotEmpty();
			groups.forEach(g -> testAgreementGroup(g));
			assertThat(groups).allMatch(ag -> ag.getCustomerActorCode().equals(CUSTOMER_ACTOR_CODE));
			assertThat(groups).allMatch(ag -> ag.getFinancialCompanyCode().equals(FINANCIAL_COMPANY_CODE));

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldGetAgreementGroupsByCustomerActorCodeAndFinancialCompanyCodeAndMasterFlag() {
		try {

			APIResponse<List<AgreementGroup>> groupsResponse = performGet("/agreement-groups" + "?customer_actor_code=" + CUSTOMER_ACTOR_CODE + "&financial_company_code=" + FINANCIAL_COMPANY_CODE + "&master_agreement=true", HttpStatus.OK, getListOfAgreementGroupsReference());

			List<AgreementGroup> groups = groupsResponse.getData();
			assertThat(groups).isNotEmpty();
			groups.forEach(g -> testAgreementGroup(g));
			assertThat(groups).allMatch(ag -> ag.getCustomerActorCode().equals(CUSTOMER_ACTOR_CODE));
			assertThat(groups).allMatch(ag -> ag.getFinancialCompanyCode().equals(FINANCIAL_COMPANY_CODE));
			assertThat(groups).allMatch(ag -> ag.getMasterAgreement().equals(Boolean.TRUE));

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldNotGetAgreementGroupsByCustomerActorCodeAndInvalidFinancialCompanyCode() {
		try {

			APIResponse<List<AgreementGroup>> groupsResponse = performGet("/agreement-groups" + "?customer_actor_code=" + CUSTOMER_ACTOR_CODE + "&financial_company_code=" + "KO", HttpStatus.OK, getListOfAgreementGroupsReference());

			List<AgreementGroup> groups = groupsResponse.getData();
			assertThat(groups).isEmpty();

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldNotGetAgreementGroupsByInvalidCustomerActorCodeAndFinancialCompanyCode() {
		try {

			APIResponse<List<AgreementGroup>> groupsResponse = performGet("/agreement-groups" + "?customer_actor_code=" + "KO" + "&financial_company_code=" + FINANCIAL_COMPANY_CODE, HttpStatus.OK, getListOfAgreementGroupsReference());

			List<AgreementGroup> groups = groupsResponse.getData();
			assertThat(groups).isEmpty();

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldNotGetAgreementGroupsByCustomerActorCodeAndFinancialCompanyCodeAndWrongMasterFlag() {
		try {

			APIResponse<List<AgreementGroup>> groupsResponse = performGet("/agreement-groups" + "?customer_actor_code=" + CUSTOMER_ACTOR_CODE + "&financial_company_code=" + FINANCIAL_COMPANY_CODE + "&master_agreement=false", HttpStatus.OK, getListOfAgreementGroupsReference());

			List<AgreementGroup> groups = groupsResponse.getData();
			assertThat(groups).isEmpty();

		} catch (Exception e) {
			fail(e);
		}
	}

	private static void testAgreementGroup(AgreementGroup pAgreementGroup) {

		assertThat(pAgreementGroup.getGroupId()).isNotNull();
		assertThat(pAgreementGroup.getCustomerActorCode()).isNotBlank();
		assertThat(pAgreementGroup.getFinancialCompanyCode()).isNotBlank();
		assertThat(pAgreementGroup.getGroupName()).isNotNull(); // Should actually be not blank, but some records exist with blank names
		assertThat(pAgreementGroup.getMasterAgreement()).isNotNull();
		assertThat(pAgreementGroup.getInvoicesGrouped()).isNotNull();
		assertThat(pAgreementGroup.getDirectDebitsGrouped()).isNotNull();
	}



	@Test
	@RunIf(Market.ie)
	void shouldFailToCreateAgreementGroupWithInvalidData() {
		try {

			// Create Agreement Group (no data)
			AgreementGroup agreementGroup = new AgreementGroup();

			APIResponse<AgreementGroup> response = performPost("/agreement-groups", agreementGroup, HttpStatus.BAD_REQUEST, getAgreementGroupsReference());

			testDetailedValidationError(response);


			// Create Agreement Group (missing flags)
			agreementGroup = new AgreementGroup();
			agreementGroup.setCustomerActorCode(CUSTOMER_ACTOR_CODE);
			agreementGroup.setFinancialCompanyCode(FINANCIAL_COMPANY_CODE);
			agreementGroup.setGroupName(generateGroupName(CUSTOMER_ACTOR_CODE));

			response = performPost("/agreement-groups", agreementGroup, HttpStatus.BAD_REQUEST, getAgreementGroupsReference());

			testDetailedValidationError(response);


			// Create Agreement Group (invalid group name)
			agreementGroup = new AgreementGroup();
			agreementGroup.setCustomerActorCode(CUSTOMER_ACTOR_CODE);
			agreementGroup.setFinancialCompanyCode(FINANCIAL_COMPANY_CODE);
			agreementGroup.setGroupName("1234567890123456789012345678901234567890"); // Too long
			agreementGroup.setMasterAgreement(Boolean.TRUE);
			agreementGroup.setInvoicesGrouped(Boolean.TRUE);
			agreementGroup.setDirectDebitsGrouped(Boolean.TRUE);

			response = performPost("/agreement-groups", agreementGroup, HttpStatus.BAD_REQUEST, getAgreementGroupsReference());

			testDetailedValidationError(response);

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldFailToCreateAgreementGroupWithExistingGroupName() {
		try {

			// List Agreement Groups
			APIResponse<List<AgreementGroup>> groupsResponse = performGet("/agreement-groups" + "?customer_actor_code=" + CUSTOMER_ACTOR_CODE, HttpStatus.OK, getListOfAgreementGroupsReference());

			List<AgreementGroup> groups = groupsResponse.getData();
			assertThat(groups).isNotEmpty();


			// Create Agreement Group
			AgreementGroup agreementGroup = new AgreementGroup();
			agreementGroup.setCustomerActorCode(CUSTOMER_ACTOR_CODE);
			agreementGroup.setFinancialCompanyCode(FINANCIAL_COMPANY_CODE);
			agreementGroup.setGroupName(groups.get(0).getGroupName()); // Same name as an existing group
			agreementGroup.setMasterAgreement(Boolean.TRUE);
			agreementGroup.setInvoicesGrouped(Boolean.TRUE);
			agreementGroup.setDirectDebitsGrouped(Boolean.TRUE);

			APIResponse<AgreementGroup> response = performPost("/agreement-groups", agreementGroup, HttpStatus.BAD_REQUEST, getAgreementGroupsReference());

			testError(response);

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldFailToUpdateInexistingAgreementGroup() {
		try {

			AgreementGroup agreementGroup = new AgreementGroup();
			agreementGroup.setGroupId(0L);
			agreementGroup.setFinancialCompanyCode(FINANCIAL_COMPANY_CODE);
			agreementGroup.setCustomerActorCode(CUSTOMER_ACTOR_CODE);
			agreementGroup.setGroupName(generateGroupName(CUSTOMER_ACTOR_CODE));
			agreementGroup.setMasterAgreement(Boolean.TRUE);
			agreementGroup.setInvoicesGrouped(Boolean.TRUE);
			agreementGroup.setDirectDebitsGrouped(Boolean.TRUE);

			APIResponse<AgreementGroup> response = performPut("/agreement-groups", agreementGroup, HttpStatus.NOT_FOUND, getAgreementGroupsReference());

			testError(response);

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldFailToDeleteInexistingAgreementGroup() {
		try {

			APIResponse<AgreementGroup> response = performDelete("/agreement-groups/" + 0L, HttpStatus.NOT_FOUND, getAgreementGroupsReference());

			testError(response);

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldFailToDeleteAgreementGroupIfNotEmpty() {
		try {

			// Delete group
			APIResponse<Void> response = performDelete("/agreement-groups/" + GROUP_ID + "/" , HttpStatus.BAD_REQUEST, getVoidTypeReference());

			testError(response);

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldCreateUpdateAndDeleteAgreementGroup() {
		try {

			// List Agreement Groups
			APIResponse<List<AgreementGroup>> groupsResponse = performGet("/agreement-groups" + "?customer_actor_code=" + CUSTOMER_ACTOR_CODE, HttpStatus.OK, getListOfAgreementGroupsReference());

			List<AgreementGroup> groups = groupsResponse.getData();
			int nbGroups = CollectionUtils.size(groups);


			// Create Agreement Group
			AgreementGroup agreementGroup = new AgreementGroup();
			agreementGroup.setCustomerActorCode(CUSTOMER_ACTOR_CODE);
			agreementGroup.setFinancialCompanyCode(FINANCIAL_COMPANY_CODE);
			agreementGroup.setGroupName(generateGroupName(CUSTOMER_ACTOR_CODE));
			agreementGroup.setMasterAgreement(Boolean.TRUE);
			agreementGroup.setInvoicesGrouped(Boolean.TRUE);
			agreementGroup.setDirectDebitsGrouped(Boolean.TRUE);

			APIResponse<AgreementGroup> response = performPost("/agreement-groups", agreementGroup, HttpStatus.OK, getAgreementGroupsReference());

			AgreementGroup result = response.getData();
			assertThat(result).isNotNull();
			assertThat(result.getGroupId()).isNotNull();


			// List Agreement Groups
			groupsResponse = performGet("/agreement-groups" + "?customer_actor_code=" + CUSTOMER_ACTOR_CODE, HttpStatus.OK, getListOfAgreementGroupsReference());

			groups = groupsResponse.getData();
			assertThat(groups).hasSize(nbGroups + 1);
			assertThat(groups).anyMatch(ag -> ag.getGroupId().equals(result.getGroupId())
					&& ag.getCustomerActorCode().equals(result.getCustomerActorCode())
					&& ag.getFinancialCompanyCode().equals(result.getFinancialCompanyCode())
					&& ag.getGroupName().equals(result.getGroupName())
					&& ag.getMasterAgreement().equals(result.getMasterAgreement())
					&& ag.getInvoicesGrouped().equals(result.getInvoicesGrouped())
					&& ag.getDirectDebitsGrouped().equals(result.getDirectDebitsGrouped()));


			// Update Agreement Group
			result.setGroupName(generateGroupName(CUSTOMER_ACTOR_CODE));
			result.setInvoicesGrouped(BooleanUtils.negate(agreementGroup.getInvoicesGrouped()));
			result.setDirectDebitsGrouped(BooleanUtils.negate(agreementGroup.getDirectDebitsGrouped()));

			response = performPut("/agreement-groups", result, HttpStatus.OK, getAgreementGroupsReference());

			AgreementGroup result2 = response.getData();
			assertThat(result2).isNotNull();
			assertThat(result2.getGroupId()).isEqualTo(result.getGroupId()) ;
			assertThat(result2.getGroupName()).isEqualTo(result.getGroupName());


			// List Agreement Groups
			groupsResponse = performGet("/agreement-groups" + "?customer_actor_code=" + CUSTOMER_ACTOR_CODE, HttpStatus.OK, getListOfAgreementGroupsReference());

			groups = groupsResponse.getData();
			assertThat(groups).hasSize(nbGroups + 1);
			assertThat(groups).anyMatch(ag -> ag.getGroupId().equals(result.getGroupId())
					&& ag.getCustomerActorCode().equals(result.getCustomerActorCode())
					&& ag.getFinancialCompanyCode().equals(result.getFinancialCompanyCode())
					&& ag.getGroupName().equals(result.getGroupName())
					&& ag.getMasterAgreement().equals(result.getMasterAgreement())
					&& ag.getInvoicesGrouped().equals(result.getInvoicesGrouped())
					&& ag.getDirectDebitsGrouped().equals(result.getDirectDebitsGrouped()));


			// Delete Agreement Group
			performDelete("/agreement-groups/" + result.getGroupId() , HttpStatus.OK, getVoidTypeReference());


			// List Agreement Groups
			groupsResponse = performGet("/agreement-groups" + "?customer_actor_code=" + CUSTOMER_ACTOR_CODE, HttpStatus.OK, getListOfAgreementGroupsReference());

			groups = groupsResponse.getData();
			assertThat(groups).hasSize(nbGroups);

		} catch (Exception e) {
			fail(e);
		}
	}



	@Test
	@RunIf(Market.ie)
	void shouldFailToGetAgreementsFromInexistingGroup() {
		try {

			// List Agreements
			APIResponse<List<AgreementGroupAgreement>> agreementsResponse = performGet("/agreement-groups/" + 0L + "/agreements" + "?grouped=true", HttpStatus.NOT_FOUND, getListOfAgreementGroupsByAgreementReference());

			testError(agreementsResponse);

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldGetGroupedAgreements() {
		try {

			// List Agreements
			APIResponse<List<AgreementGroupAgreement>> agreementsResponse = performGet("/agreement-groups/" + GROUP_ID + "/agreements" + "?grouped=true", HttpStatus.OK, getListOfAgreementGroupsByAgreementReference());

			List<AgreementGroupAgreement> agreements = agreementsResponse.getData();
			assertThat(agreements).isNotEmpty();
			agreements.forEach(a -> testAgreementGroupAgreement(a));

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldGetUngroupedAgreement() {
		try {

			// List Agreements
			APIResponse<List<AgreementGroupAgreement>> agreementsResponse = performGet("/agreement-groups/" + GROUP_ID + "/agreements" + "?grouped=false", HttpStatus.OK, getListOfAgreementGroupsByAgreementReference());

			List<AgreementGroupAgreement> agreements = agreementsResponse.getData();
			// Can be empty
			agreements.forEach(a -> testAgreementGroupAgreement(a));

		} catch (Exception e) {
			fail(e);
		}
	}

	private static void testAgreementGroupAgreement(AgreementGroupAgreement pAgreement) {

		assertThat(pAgreement.getAgreementId()).isNotNull();
		assertThat(pAgreement.getAgreementCode()).isNotBlank();
		assertThat(pAgreement.getSimulationCode()).isNotBlank();
		assertThat(pAgreement.getAgreementStatusCode()).isNotNull();
		assertThat(pAgreement.getFinanceTypeCode()).isNotNull();
		assertThat(pAgreement.getVehicleNatureCode()).isNotNull();
		assertThat(pAgreement.getVehicleBrandLabel()).isNotBlank();
	}



	@Test
	@RunIf(Market.ie)
	void shouldFailToAddAndRemoveAgreementFromInexistingAgreementGroup() {
		try {

			// Add Agreement to Agreement Group
			APIResponse<Void> response = performPost("/agreement-groups/" + 0L + "/agreements/" + 0L, null,  HttpStatus.NOT_FOUND, getVoidTypeReference());

			testError(response);

			// Remove Agreement from Agreement Group
			response = performDelete("/agreement-groups/" + 0L + "/agreements/" + 0L,  HttpStatus.NOT_FOUND, getVoidTypeReference());

			testError(response);

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldFailToAddAlreadyGroupedAgreementToAgreementGroup() {
		try {

			// List grouped Agreements
			APIResponse<List<AgreementGroupAgreement>> groupedAgreementsResponse = performGet("/agreement-groups/" + GROUP_ID + "/agreements" + "?grouped=true", HttpStatus.OK, getListOfAgreementGroupsByAgreementReference());

			List<AgreementGroupAgreement> groupedAgreements = groupedAgreementsResponse.getData();
			assertThat(groupedAgreements).isNotEmpty();
			Long agreementId = groupedAgreements.get(0).getAgreementId();


			// Add grouped Agreement to Agreement Group
			APIResponse<Void> response = performPost("/agreement-groups/" + GROUP_ID + "/agreements/" + agreementId, null,  HttpStatus.BAD_REQUEST, getVoidTypeReference());

			testError(response);

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldFailToRemoveUngroupedAgreementFromAgreementGroup() {
		try {

			// Remove Agreement from Agreement Group
			APIResponse<Void> response = performDelete("/agreement-groups/" + GROUP_ID + "/agreements/" + 0L,  HttpStatus.BAD_REQUEST, getVoidTypeReference());

			testError(response);

		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@RunIf(Market.ie)
	void shouldCreateGroupAddToGroupRemoveFromGroupAndDeleteGroup() {
		try {

			// Create Agreement Group
			AgreementGroup agreementGroup = new AgreementGroup();
			agreementGroup.setGroupName(generateGroupName(CUSTOMER_ACTOR_CODE));
			agreementGroup.setFinancialCompanyCode(FINANCIAL_COMPANY_CODE);
			agreementGroup.setCustomerActorCode(CUSTOMER_ACTOR_CODE);
			agreementGroup.setMasterAgreement(Boolean.TRUE);
			agreementGroup.setInvoicesGrouped(Boolean.TRUE);
			agreementGroup.setDirectDebitsGrouped(Boolean.TRUE);

			APIResponse<AgreementGroup> response = performPost("/agreement-groups/", agreementGroup, HttpStatus.OK, getAgreementGroupsReference());

			AgreementGroup result = response.getData();
			assertThat(result).isNotNull();
			Long groupId = result.getGroupId();


			// List Agreements
			APIResponse<List<AgreementGroupAgreement>> groupedAgreementsResponse = performGet("/agreement-groups/" + groupId + "/agreements" + "?grouped=true", HttpStatus.OK, getListOfAgreementGroupsByAgreementReference());
			int nbGroupedAgreements = CollectionUtils.size(groupedAgreementsResponse.getData());

			APIResponse<List<AgreementGroupAgreement>> ungroupedAgreementsResponse = performGet("/agreement-groups/" + groupId + "/agreements" + "?grouped=false", HttpStatus.OK, getListOfAgreementGroupsByAgreementReference());

			List<AgreementGroupAgreement> ungroupedAgreements = ungroupedAgreementsResponse.getData();
			assertThat(ungroupedAgreements).isNotEmpty();
			Long agreementId = ungroupedAgreements.stream().filter(a -> a.getAgreementStatusCode().equals(1)).findFirst().orElse(null).getAgreementId();


			// Add Agreement to Agreement Group
			performPost("/agreement-groups/" + groupId + "/agreements/" + agreementId, null, HttpStatus.OK, getVoidTypeReference());


			// List Agreements
			groupedAgreementsResponse = performGet("/agreement-groups/" + groupId + "/agreements" + "?grouped=true", HttpStatus.OK, getListOfAgreementGroupsByAgreementReference());
			assertThat(groupedAgreementsResponse.getData()).hasSize(nbGroupedAgreements + 1);


			// Remove agreement from group
			performDelete("/agreement-groups/" + groupId + "/agreements/" + agreementId + "/",  HttpStatus.OK, getVoidTypeReference());


			// List Agreements
			groupedAgreementsResponse = performGet("/agreement-groups/" + groupId + "/agreements" + "?grouped=true", HttpStatus.OK, getListOfAgreementGroupsByAgreementReference());
			assertThat(groupedAgreementsResponse.getData()).hasSize(nbGroupedAgreements);


			// Delete Agreement Group
			performDelete("/agreement-groups/" + groupId + "/" , HttpStatus.OK, getVoidTypeReference());


			// List Agreement Groups
			APIResponse<List<AgreementGroup>> groupsResponse = performGet("/agreement-groups" + "?customer_actor_code=" + CUSTOMER_ACTOR_CODE, HttpStatus.OK, getListOfAgreementGroupsReference());

			List<AgreementGroup> groups = groupsResponse.getData();
			assertThat(groups).isNotEmpty();
			assertThat(groups).noneMatch(ag -> ag.getGroupId().equals(groupId));

		} catch (Exception e) {
			fail(e);
		}
	}



	private static String generateGroupName(String pActorCode) {
		return pActorCode + " - " + TextUtils.generateRandomString(15);
	}

	private TypeReference<APIResponse<List<AgreementGroup>>> getListOfAgreementGroupsReference() {
		return new TypeReference<APIResponse<List<AgreementGroup>>>() {};
	}

	private TypeReference<APIResponse<AgreementGroup>> getAgreementGroupsReference() {
		return new TypeReference<APIResponse<AgreementGroup>>() {};
	}

	private TypeReference<APIResponse<List<AgreementGroupAgreement>>> getListOfAgreementGroupsByAgreementReference() {
		return new TypeReference<APIResponse<List<AgreementGroupAgreement>>>() {};
	}


}
