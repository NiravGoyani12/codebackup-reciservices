package com.rcibanque.rcidirect.services.core.test;

import java.lang.reflect.Method;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rcibanque.rcidirect.services.core.config.utils.ConfigUtils;
import com.rcibanque.rcidirect.services.core.test.annotation.IgnoreIf;
import com.rcibanque.rcidirect.services.core.test.annotation.RunIf;

public class ConditionalTestRunner implements ExecutionCondition {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConditionalTestRunner.class);


	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		if (ignoredByMarket(context)) {
			LOGGER.info("Test ignored for market");
			return ConditionEvaluationResult.disabled(String.format("Test ignored for market"));
		} else {
			return ConditionEvaluationResult.enabled(String.format("Test executed for market"));
		}
	}

	private boolean ignoredByMarket(ExtensionContext context) {

		// If a market is set in "IgnoreIf" annotation, then it is ignored even it is set in "RunIf" annotation
		return checkIgnoringMarkets(context) || checkAllowingMarkets(context);
	}

	private boolean checkIgnoringMarkets(ExtensionContext context) {

		Method method = context.getTestMethod().orElse(null);
		Class<?> clazz = context.getTestClass().orElse(null);

		return (method != null && marketIgnored(method.getAnnotation(IgnoreIf.class)))
				|| (clazz != null && marketIgnored(clazz.getAnnotation(IgnoreIf.class)));
	}

	private boolean checkAllowingMarkets(ExtensionContext context) {

		Method method = context.getTestMethod().orElse(null);
		Class<?> clazz = context.getTestClass().orElse(null);

		return (method != null && marketIgnored(method.getAnnotation(RunIf.class)))
				|| (clazz != null && marketIgnored(clazz.getAnnotation(RunIf.class)));
	}

	private boolean marketIgnored(RunIf pRunIfAnnotation) {

		return pRunIfAnnotation != null && ! ConfigUtils.isMarket(pRunIfAnnotation.value());
	}

	private boolean marketIgnored(IgnoreIf pIgnoreIfAnnotation) {

		return pIgnoreIfAnnotation != null && ConfigUtils.isMarket(pIgnoreIfAnnotation.value());
	}

}
