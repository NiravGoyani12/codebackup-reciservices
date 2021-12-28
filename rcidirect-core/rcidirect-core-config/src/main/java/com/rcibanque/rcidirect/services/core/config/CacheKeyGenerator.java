package com.rcibanque.rcidirect.services.core.config;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.springframework.cache.interceptor.KeyGenerator;

import com.rcibanque.rcidirect.services.core.domain.IContext;
import com.rcibanque.rcidirect.services.core.utils.IteratorUtils;

public final class CacheKeyGenerator implements KeyGenerator {

	private static final String CLASS_METHOD_SEPARATOR = ".";

	private static final String PARAMS_START = "(";

	private static final String PARAMS_END = ")";

	private static final String PARAMS_SEPARATOR = ",";


	@Override
	public Object generate(Object pTarget, Method pMethod, Object... pParams) {

		StringBuilder sb = new StringBuilder();

		sb.append(pTarget.getClass().getSimpleName());
		sb.append(CLASS_METHOD_SEPARATOR);
		sb.append(pMethod.getName());

		sb.append(PARAMS_START);
		Iterator<Object> paramsIter = IteratorUtils.nullableIterable(pParams).iterator();
		while (paramsIter.hasNext()) {
			Object param = paramsIter.next();

			// We only take what should be part of a key
			if(param != null && ! (param instanceof IContext)) {

				sb.append(param.toString());

				if(paramsIter.hasNext()) {
					sb.append(PARAMS_SEPARATOR);
				}
			}
		}
		sb.append(PARAMS_END);

		return sb.toString();
	}

}
