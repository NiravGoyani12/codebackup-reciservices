package com.rcibanque.rcidirect.services.core.validation.tests.validator;

import org.springframework.beans.factory.BeanFactory;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.validation.ValidationBuilder;
import com.rcibanque.rcidirect.services.core.validation.ValidationContext;
import com.rcibanque.rcidirect.services.core.validation.config.IValidationConfig;
import com.rcibanque.rcidirect.services.core.validation.key.IProcessStage;
import com.rcibanque.rcidirect.services.core.validation.tests.TestValidation;
import com.rcibanque.rcidirect.services.core.validation.tests.config.TestValidationConfig;
import com.rcibanque.rcidirect.services.core.validation.tests.domain.TestClass;
import com.rcibanque.rcidirect.services.core.validation.tests.key.TestFields;
import com.rcibanque.rcidirect.services.core.validation.validator.AbstractValidator;

public class TestValidator extends AbstractValidator<TestClass> {


	private IValidationConfig _validationConfig;


	public TestValidator(BeanFactory pBeanFactory, String pConfigurationName) {

		super();

		_beanFactory = pBeanFactory;

		_validationConfig = new TestValidationConfig(pConfigurationName);
	}


	@Override
	public void validate(IContext pContext, TestClass pObject, IProcessStage pProcessStage) {

		ValidationBuilder vcb = new ValidationBuilder().withConfig(_validationConfig).inProcessStage(pProcessStage);
		vcb.withReference(pObject).withReferenceKey(pObject.getClass().getName());
		vcb.addToValidation(TestValidation.TEST_VALIDATION);


		applyAbstractValidationRules(pContext, pObject.getField1(), vcb.forField(TestFields.FIELD_1).build());
		applyAbstractValidationRules(pContext, pObject.getField2(), vcb.forField(TestFields.FIELD_2).build());
		applyAbstractValidationRules(pContext, pObject.getField3(), vcb.forField(TestFields.FIELD_3).build());
		applyAbstractValidationRules(pContext, pObject.getField4(), vcb.forField(TestFields.FIELD_4).build());
		applyAbstractValidationRules(pContext, pObject.getField5(), vcb.forField(TestFields.FIELD_5).build());
		applyAbstractValidationRules(pContext, pObject.getField6(), vcb.forField(TestFields.FIELD_6).build());

		applyAbstractValidationRules(pContext, pObject.getField21(), vcb.forField(TestFields.FIELD_21).build());
		applyAbstractValidationRules(pContext, pObject.getField22(), vcb.forField(TestFields.FIELD_22).build());
		applyAbstractValidationRules(pContext, pObject.getField23(), vcb.forField(TestFields.FIELD_23).build());
		applyAbstractValidationRules(pContext, pObject.getField24(), vcb.forField(TestFields.FIELD_24).build());
		applyAbstractValidationRules(pContext, pObject.getField25(), vcb.forField(TestFields.FIELD_25).build());

		applyAbstractValidationRules(pContext, pObject.getField31(), vcb.forField(TestFields.FIELD_31).build());
		applyAbstractValidationRules(pContext, pObject.getField32(), vcb.forField(TestFields.FIELD_32).build());
		applyAbstractValidationRules(pContext, pObject.getField33(), vcb.forField(TestFields.FIELD_33).build());

		applyAbstractValidationRules(pContext, pObject.getField31b(), vcb.forField(TestFields.FIELD_31b).build());
		applyAbstractValidationRules(pContext, pObject.getField32b(), vcb.forField(TestFields.FIELD_32b).build());
		applyAbstractValidationRules(pContext, pObject.getField33b(), vcb.forField(TestFields.FIELD_33b).build());

		applyAbstractValidationRules(pContext, pObject.getField41(), vcb.forField(TestFields.FIELD_41).build());
		applyAbstractValidationRules(pContext, pObject.getField42(), vcb.forField(TestFields.FIELD_42).build());
		applyAbstractValidationRules(pContext, pObject.getField43(), vcb.forField(TestFields.FIELD_43).build());
		applyAbstractValidationRules(pContext, pObject.getField44(), vcb.forField(TestFields.FIELD_44).build());
		applyAbstractValidationRules(pContext, pObject.getField45(), vcb.forField(TestFields.FIELD_45).build());

		applyAbstractValidationRules(pContext, pObject.getField51(), vcb.forField(TestFields.FIELD_51).build());
		applyAbstractValidationRules(pContext, pObject.getField52(), vcb.forField(TestFields.FIELD_52).build());

		applyAbstractValidationRules(pContext, pObject.getField61(), vcb.forField(TestFields.FIELD_61).build());
		applyAbstractValidationRules(pContext, pObject.getField62(), vcb.forField(TestFields.FIELD_62).build());
		applyAbstractValidationRules(pContext, pObject.getField63(), vcb.forField(TestFields.FIELD_63).build());
		applyAbstractValidationRules(pContext, pObject.getField64(), vcb.forField(TestFields.FIELD_64).build());

		applyAbstractValidationRules(pContext, pObject.getField71(), vcb.forField(TestFields.FIELD_71).build());
		applyAbstractValidationRules(pContext, pObject.getField72(), vcb.forField(TestFields.FIELD_72).build());

		applyAbstractValidationRules(pContext, pObject.getField81(), vcb.forField(TestFields.FIELD_81).build());
		applyAbstractValidationRules(pContext, pObject.getField82(), vcb.forField(TestFields.FIELD_82).build());
		applyAbstractValidationRules(pContext, pObject.getField83(), vcb.forField(TestFields.FIELD_83).build());

		applyAbstractValidationRules(pContext, pObject.getField91(), vcb.forField(TestFields.FIELD_91).build());
		applyAbstractValidationRules(pContext, pObject.getField92(), vcb.forField(TestFields.FIELD_92).build());
		applyAbstractValidationRules(pContext, pObject.getField93(), vcb.forField(TestFields.FIELD_93).build());
		applyAbstractValidationRules(pContext, pObject.getField94(), vcb.forField(TestFields.FIELD_94).build());
		applyAbstractValidationRules(pContext, pObject.getField95(), vcb.forField(TestFields.FIELD_95).build());
	}


	@Override
	protected boolean isFieldActuallyDefined(IContext pContext, ValidationContext pValidationContext, Object pObject) {

		if(TestFields.FIELD_61.equals(pValidationContext.getValidationField())
				|| TestFields.FIELD_64.equals(pValidationContext.getValidationField())) {

			return ! "-1".equals(pObject);
		}

		return super.isFieldActuallyDefined(pContext, pValidationContext, pObject);
	}


}