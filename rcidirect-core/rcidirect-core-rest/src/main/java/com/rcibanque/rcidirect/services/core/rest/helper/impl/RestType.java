package com.rcibanque.rcidirect.services.core.rest.helper.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.core.ParameterizedTypeReference;

import com.rcibanque.rcidirect.services.core.response.APIResponse;

public abstract class RestType<T> extends ParameterizedTypeReference<APIResponse<T>> {


	public static final RestType<Void> VOID = new RestType<Void>(){};

	public static final RestType<Boolean> BOOLEAN = new RestType<Boolean>(){};

	public static final RestType<Integer> INTEGER = new RestType<Integer>(){};

	public static final RestType<String> STRING = new RestType<String>(){};

	public static final RestType<byte[]> BYTES = new RestType<byte[]>(){};


	private final Type _type;

	public RestType() {
		super();
		_type = new ParameterizedTypeImpl(getClass());
	}

	@Override
	public Type getType() {
		return _type;
	}

	private class ParameterizedTypeImpl implements ParameterizedType {

		private final Type[] _actualType;

		protected ParameterizedTypeImpl(Class<?> pClass) {
			_actualType =  ((ParameterizedType) pClass.getGenericSuperclass()).getActualTypeArguments();
		}

		@Override
		public Type getRawType() {
			return APIResponse.class;
		}

		@Override
		public Type[] getActualTypeArguments() {
			return _actualType;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}

	}

}
