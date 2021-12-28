package com.rcibanque.rcidirect.services.agreementgroups.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.rcibanque.rcidirect.services.agreementgroups.dao.IAgreementGroupsDao;
import com.rcibanque.rcidirect.services.agreementgroups.dao.criteria.AgreementGroupCriteria;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroup;
import com.rcibanque.rcidirect.services.agreementgroups.domain.AgreementGroupAgreement;
import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.exception.EntityNotFoundException;
import com.rcibanque.rcidirect.services.core.exception.InvalidRequestException;
import com.rcibanque.rcidirect.services.core.test.AbstractUnitTest;

class AgreementGroupServiceTest extends AbstractUnitTest {

	private static final boolean GROUPED = true;
	private static final boolean UNGROUPED = false;

	private static final boolean WORKING_QUOTES = true;
	private static final boolean NOT_WORKING_QUOTES = false;


	@InjectMocks
	@Spy
	private AgreementGroupsService _agreementGroupsService;

	@Mock
	private IAgreementGroupsDao _agreementGroupsDao;


	@BeforeEach
	void init()  {

		MockitoAnnotations.initMocks(this);
	}


	@Test
	void shouldOnlySeeValidGroupsForCandidateAgreement() {

		// ARRANGE
		givenContext();

		Long candidateAgreementId = 123L;

		AgreementGroup group1 = new AgreementGroup();
		group1.setGroupId(1L);

		AgreementGroup group2 = new AgreementGroup();
		group1.setGroupId(2L);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(group1, group2));

		// There are two groups, but only group 2 contains the candidate agreement in its ungrouped agreements

		doReturn(Arrays.asList()).when(_agreementGroupsService).getAgreementGroupAgreements(any(IContext.class), eq(group1.getGroupId()), eq(Boolean.FALSE));

		AgreementGroupAgreement group2UngroupedAgreement = new AgreementGroupAgreement();
		group2UngroupedAgreement.setAgreementId(candidateAgreementId);

		doReturn(Arrays.asList(group2UngroupedAgreement)).when(_agreementGroupsService).getAgreementGroupAgreements(any(IContext.class), eq(group2.getGroupId()), eq(Boolean.FALSE));

		// ACT
		AgreementGroupCriteria criteria = new AgreementGroupCriteria();
		criteria.setCandidateAgreementId(candidateAgreementId);

		List<AgreementGroup> groups = _agreementGroupsService.getAgreementGroups(_context, criteria);

		// ASSERT
		assertThat(groups).hasSize(1);
		assertThat(groups.get(0).getGroupId()).isEqualTo(group2.getGroupId());
	}


	@Test
	void shouldCreateGroup() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);
		group.setGroupId(null);

		given(_agreementGroupsDao.checkCustomerActorAccess(any(IContext.class), eq(group.getCustomerActorCode()))).willReturn(true);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList());

		// ACT
		_agreementGroupsService.createAgreementGroup(_context, group);

		// ASSERT
		verify(_agreementGroupsDao, times(1)).createAgreementGroup(any(IContext.class), eq(group));
	}

	@Test
	void shouldFailToCreateGroupIfCustomerNotAccessible() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);
		group.setGroupId(null);

		given(_agreementGroupsDao.checkCustomerActorAccess(any(IContext.class), eq(group.getCustomerActorCode()))).willReturn(false);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList());

		// ACT
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> {
			_agreementGroupsService.createAgreementGroup(_context, group);
		});

		// ASSERT
		verify(_agreementGroupsDao, times(0)).createAgreementGroup(any(IContext.class), any(AgreementGroup.class));
	}

	@Test
	void shouldFailToCreateGroupIfCustomerHasAnotherGroupWithSameName() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);
		group.setGroupId(null);

		given(_agreementGroupsDao.checkCustomerActorAccess(any(IContext.class), eq(group.getCustomerActorCode()))).willReturn(true);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(getAgreementGroup(_context)));

		// ACT
		assertThatExceptionOfType(InvalidRequestException.class).isThrownBy(() -> {
			_agreementGroupsService.createAgreementGroup(_context, group);
		});

		// ASSERT
		verify(_agreementGroupsDao, times(0)).createAgreementGroup(any(IContext.class), any(AgreementGroup.class));
	}


	@Test
	void shouldUpdateGroup() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(group));

		// ACT
		_agreementGroupsService.updateAgreementGroup(_context, group);

		// ASSERT
		verify(_agreementGroupsDao, times(1)).updateAgreementGroup(any(IContext.class), eq(group));
	}

	@Test
	void shouldFailToUpdateGroupIfGroupNotAccessible() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList());

		// ACT
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> {
			_agreementGroupsService.updateAgreementGroup(_context, group);
		});

		// ASSERT
		verify(_agreementGroupsDao, times(0)).updateAgreementGroup(any(IContext.class), any(AgreementGroup.class));
	}

	@Test
	void shouldFailToUpdateGroupIfCustomerHasAnotherGroupWithSameName() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup updatedGroup = getAgreementGroup(_context);
		AgreementGroup groupWithSameName = getAgreementGroup(_context);
		groupWithSameName.setGroupId(2L);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(updatedGroup, groupWithSameName));

		// ACT
		assertThatExceptionOfType(InvalidRequestException.class).isThrownBy(() -> {
			_agreementGroupsService.updateAgreementGroup(_context, updatedGroup);
		});

		// ASSERT
		verify(_agreementGroupsDao, times(0)).updateAgreementGroup(any(IContext.class), any(AgreementGroup.class));
	}


	@Test
	void shouldDeleteGroup() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(group));

		// ACT
		_agreementGroupsService.deleteAgreementGroup(_context, group.getGroupId());

		// ASSERT
		verify(_agreementGroupsDao, times(1)).deleteAgreementGroup(any(IContext.class), eq(group.getGroupId()));
	}

	@Test
	void shouldFailToDeleteGroupIfGroupNotAccessible() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList());

		// ACT
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> {
			_agreementGroupsService.deleteAgreementGroup(_context, group.getGroupId());
		});

		// ASSERT
		verify(_agreementGroupsDao, times(0)).deleteAgreementGroup(any(IContext.class), any(Long.class));
	}

	@Test
	void shouldFailToDeleteGroupIfGroupNotEmpty() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(group));

		// Grouped agreements
		AgreementGroupCriteria groupedAgreementsCriteria = getAgreementsCriteria(_context, group, GROUPED);
		List<AgreementGroupAgreement> groupedAgreements = getAgreements(_context, group, GROUPED, WORKING_QUOTES);

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(groupedAgreementsCriteria))).willReturn(groupedAgreements);

		// ACT
		assertThatExceptionOfType(InvalidRequestException.class).isThrownBy(() -> {
			_agreementGroupsService.deleteAgreementGroup(_context, group.getGroupId());
		});

		// ASSERT
		verify(_agreementGroupsDao, times(0)).deleteAgreementGroup(any(IContext.class), any(Long.class));
	}


	@Test
	void shouldGetAgreementGroupGroupedAgreements() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(group));

		// Grouped agreements
		AgreementGroupCriteria groupedAgreementsCriteria = getAgreementsCriteria(_context, group, GROUPED);
		List<AgreementGroupAgreement> groupedAgreements = getAgreements(_context, group, GROUPED, WORKING_QUOTES);

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(groupedAgreementsCriteria))).willReturn(groupedAgreements);

		// ACT
		List<AgreementGroupAgreement> result = _agreementGroupsService.getAgreementGroupAgreements(_context, group.getGroupId(), GROUPED);

		// ASSERT
		assertThat(result).hasSameSizeAs(groupedAgreements);
	}

	@Test
	void shouldGetAgreementGroupUngroupedAgreements() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(group));

		// Ungrouped agreements
		AgreementGroupCriteria ungroupedAgreementsCriteria = getAgreementsCriteria(_context, group, UNGROUPED);
		List<AgreementGroupAgreement> ungroupedAgreements = getAgreements(_context, group, UNGROUPED, WORKING_QUOTES);

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(ungroupedAgreementsCriteria))).willReturn(ungroupedAgreements);

		// ACT
		List<AgreementGroupAgreement> result = _agreementGroupsService.getAgreementGroupAgreements(_context, group.getGroupId(), UNGROUPED);

		// ASSERT
		assertThat(result).hasSameSizeAs(ungroupedAgreements);
	}

	@Test
	void shouldFailToGetAgreementGroupAgreementsIfGroupNotAccessible() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList());

		// ACT
		assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> {
			_agreementGroupsService.deleteAgreementGroup(_context, group.getGroupId());
		});
	}


	@Test
	void shouldAddAgreementToGroup() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(group));

		// Grouped agreements
		AgreementGroupCriteria groupedAgreementsCriteria = getAgreementsCriteria(_context, group, GROUPED);
		List<AgreementGroupAgreement> groupedAgreements = getAgreements(_context, group, GROUPED, WORKING_QUOTES);

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(groupedAgreementsCriteria))).willReturn(groupedAgreements);

		// Ungrouped agreements
		AgreementGroupCriteria ungroupedAgreementsCriteria = getAgreementsCriteria(_context, group, UNGROUPED);
		List<AgreementGroupAgreement> ungroupedAgreements = getAgreements(_context, group, UNGROUPED, WORKING_QUOTES);

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(ungroupedAgreementsCriteria))).willReturn(ungroupedAgreements);

		// ACT
		_agreementGroupsService.addAgreementToAgreementGroup(_context, group.getGroupId(), ungroupedAgreements.get(0).getAgreementId());

		// ASSERT
		verify(_agreementGroupsDao, times(1)).addAgreementToAgreementGroup(any(IContext.class), eq(group.getGroupId()), eq(ungroupedAgreements.get(0).getAgreementId()));
	}

	@Test
	void shouldFailToAddNormalRunningAgreementToWorkingQuoteGroup() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(group));

		// Grouped agreements
		AgreementGroupCriteria groupedAgreementsCriteria = getAgreementsCriteria(_context, group, GROUPED);
		List<AgreementGroupAgreement> groupedAgreements = getAgreements(_context, group, GROUPED, WORKING_QUOTES);

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(groupedAgreementsCriteria))).willReturn(groupedAgreements);

		// Ungrouped agreements
		AgreementGroupCriteria ungroupedAgreementsCriteria = getAgreementsCriteria(_context, group, UNGROUPED);
		List<AgreementGroupAgreement> ungroupedAgreements = getAgreements(_context, group, UNGROUPED, NOT_WORKING_QUOTES);

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(ungroupedAgreementsCriteria))).willReturn(ungroupedAgreements);

		// ACT
		assertThatExceptionOfType(InvalidRequestException.class).isThrownBy(() -> {
			_agreementGroupsService.addAgreementToAgreementGroup(_context, group.getGroupId(), ungroupedAgreements.get(0).getAgreementId());
		});

		// ASSERT
		verify(_agreementGroupsDao, times(0)).addAgreementToAgreementGroup(any(IContext.class), any(Long.class), any(Long.class));
	}

	@Test
	void shouldFailToAddWorkingQuoteAgreementToNormalRunningGroup() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(group));

		// Grouped agreements
		AgreementGroupCriteria groupedAgreementsCriteria = getAgreementsCriteria(_context, group, GROUPED);
		List<AgreementGroupAgreement> groupedAgreements = getAgreements(_context, group, GROUPED, NOT_WORKING_QUOTES);

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(groupedAgreementsCriteria))).willReturn(groupedAgreements);

		// Ungrouped agreements
		AgreementGroupCriteria ungroupedAgreementsCriteria = getAgreementsCriteria(_context, group, UNGROUPED);
		List<AgreementGroupAgreement> ungroupedAgreements = getAgreements(_context, group, UNGROUPED, WORKING_QUOTES);

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(ungroupedAgreementsCriteria))).willReturn(ungroupedAgreements);

		// ACT
		assertThatExceptionOfType(InvalidRequestException.class).isThrownBy(() -> {
			_agreementGroupsService.addAgreementToAgreementGroup(_context, group.getGroupId(), ungroupedAgreements.get(0).getAgreementId());
		});

		// ASSERT
		verify(_agreementGroupsDao, times(0)).addAgreementToAgreementGroup(any(IContext.class), any(Long.class), any(Long.class));
	}

	@Test
	void shouldFailToAddAgreementToGroupIfNotValidCandidate() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(group));

		// Grouped agreements
		AgreementGroupCriteria groupedAgreementsCriteria = getAgreementsCriteria(_context, group, GROUPED);
		List<AgreementGroupAgreement> groupedAgreements = getAgreements(_context, group, GROUPED, NOT_WORKING_QUOTES);

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(groupedAgreementsCriteria))).willReturn(groupedAgreements);

		// Ungrouped agreements
		AgreementGroupCriteria ungroupedAgreementsCriteria = getAgreementsCriteria(_context, group, UNGROUPED);
		List<AgreementGroupAgreement> ungroupedAgreements = Arrays.asList();

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(ungroupedAgreementsCriteria))).willReturn(ungroupedAgreements);

		// ACT
		assertThatExceptionOfType(InvalidRequestException.class).isThrownBy(() -> {
			_agreementGroupsService.addAgreementToAgreementGroup(_context, group.getGroupId(), 0L);
		});

		// ASSERT
		verify(_agreementGroupsDao, times(0)).addAgreementToAgreementGroup(any(IContext.class), any(Long.class), any(Long.class));
	}


	@Test
	void shouldRemoveAgreementFromGroup() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(group));

		// Grouped agreements
		AgreementGroupCriteria groupedAgreementsCriteria = getAgreementsCriteria(_context, group, GROUPED);
		List<AgreementGroupAgreement> groupedAgreements = getAgreements(_context, group, GROUPED, NOT_WORKING_QUOTES);

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(groupedAgreementsCriteria))).willReturn(groupedAgreements);

		// ACT
		_agreementGroupsService.removeAgreementFromAgreementGroup(_context, group.getGroupId(), groupedAgreements.get(0).getAgreementId());

		// ASSERT
		verify(_agreementGroupsDao, times(1)).removeAgreementFromAgreementGroup(any(IContext.class), eq(group.getGroupId()), eq(groupedAgreements.get(0).getAgreementId()));
	}

	@Test
	void shouldFailToRemoveAgreementFromGroupIfNotInGroup() {

		// ARRANGE
		givenContext();

		// Group
		AgreementGroup group = getAgreementGroup(_context);

		given(_agreementGroupsDao.getAgreementGroups(any(IContext.class), any(AgreementGroupCriteria.class))).willReturn(Arrays.asList(group));

		// Grouped agreements
		AgreementGroupCriteria groupedAgreementsCriteria = getAgreementsCriteria(_context, group, GROUPED);
		List<AgreementGroupAgreement> groupedAgreements = Arrays.asList();

		given(_agreementGroupsDao.getAgreementGroupAgreements(any(IContext.class), eq(groupedAgreementsCriteria))).willReturn(groupedAgreements);

		// ACT
		assertThatExceptionOfType(InvalidRequestException.class).isThrownBy(() -> {
			_agreementGroupsService.removeAgreementFromAgreementGroup(_context, group.getGroupId(), 0L);
		});

		// ASSERT
		verify(_agreementGroupsDao, times(0)).removeAgreementFromAgreementGroup(any(IContext.class), any(Long.class), any(Long.class));
	}


	private static AgreementGroup getAgreementGroup(IContext pContext) {

		AgreementGroup group = new AgreementGroup();

		group.setGroupId(1L);
		group.setCustomerActorCode("CAC");
		group.setFinancialCompanyCode("FCC");
		group.setMasterAgreement(Boolean.TRUE);
		group.setGroupName("GN");
		group.setInvoicesGrouped(Boolean.TRUE);
		group.setDirectDebitsGrouped(Boolean.TRUE);

		return group;
	}

	private static AgreementGroupCriteria getAgreementsCriteria(IContext pContext, AgreementGroup pGroup, boolean pGrouped) {

		AgreementGroupCriteria criteria = new AgreementGroupCriteria();

		criteria.setLanguageCode(pContext.getLanguageCode());
		criteria.setCustomerActorCode(pGroup.getCustomerActorCode());
		criteria.setFinancialCompanyCode(pGroup.getFinancialCompanyCode());
		criteria.setGroupId(pGrouped ? pGroup.getGroupId() : null);

		return criteria;
	}

	private static List<AgreementGroupAgreement> getAgreements(IContext pContext, AgreementGroup pGroup, boolean pGrouped, boolean pWorkingQuote) {

		List<AgreementGroupAgreement> res = new ArrayList<>();

		int start = pGrouped ? 1 : 100;
		for(int i=start; i<start+5; i++) {

			AgreementGroupAgreement agreement = new AgreementGroupAgreement();

			agreement.setAgreementId(1L);
			agreement.setAgreementStatusCode(pWorkingQuote ? 1 : 10);

			res.add(agreement);
		}

		return res;
	}

}
