package com.rcibanque.rcidirect.services.core.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.rcibanque.rcidirect.services.core.domain.DateTime;

public class RestUtils {


	private static final RoundingMode BIG_DECIMAL_ROUNDING_MODE = RoundingMode.HALF_UP;

	private static final int BIG_DECIMAL_SCALE = 2;


	private static MappingJackson2HttpMessageConverter JSON_CONVERTER = null;

	private static ByteArrayHttpMessageConverter JPEG_CONVERTER = null;


	private RestUtils() {
		super();
	}


	public static MappingJackson2HttpMessageConverter getJSONMessageConverter() {

		if(JSON_CONVERTER == null) {

			Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
			builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
			builder.serializationInclusion(Include.NON_NULL);
			builder.failOnEmptyBeans(false);
			builder.indentOutput(true);
			builder.serializers(bigDecimalSerializer(), dateSerializer());
			builder.timeZone(TimeZone.getDefault());

			JSON_CONVERTER = new MappingJackson2HttpMessageConverter(builder.build());
		}

		return JSON_CONVERTER;
	}


	public static ByteArrayHttpMessageConverter getJPGImageMessageConverter() {

		if(JPEG_CONVERTER == null) {

			ByteArrayHttpMessageConverter imageConverter = new ByteArrayHttpMessageConverter();
			imageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.IMAGE_JPEG));

			JPEG_CONVERTER = imageConverter;
		}

		return JPEG_CONVERTER;
	}


	private static JsonSerializer<Date> dateSerializer() {

		return new JsonSerializer<Date>() {
			@Override
			public void serialize(Date pValue, JsonGenerator pGen, SerializerProvider pSerializers) throws IOException {
				String res = StringUtils.EMPTY;
				if(pValue != null) {
					if(pValue instanceof DateTime) {
						res = ConvertUtils.toString((DateTime) pValue);
					}
					else {
						res = ConvertUtils.toString(pValue);
					}
				}
				pGen.writeString(res);
			}
			@Override
			public Class<Date> handledType() {
				return Date.class;
			}
		};
	}


	private static JsonSerializer<BigDecimal> bigDecimalSerializer() {

		return new JsonSerializer<BigDecimal>() {
			@Override
			public void serialize(BigDecimal pValue, JsonGenerator pGen, SerializerProvider pSerializers) throws IOException {
				BigDecimal res = pValue;
				if(pValue != null) {
					res = pValue.setScale(BIG_DECIMAL_SCALE, BIG_DECIMAL_ROUNDING_MODE);
				}
				pGen.writeNumber(res);
			}
			@Override
			public Class<BigDecimal> handledType() {
				return BigDecimal.class;
			}
		};
	}


	/**
	 * @return JSon object mapper
	 */
	public static ObjectMapper getObjectMapper() {

		return getJSONMessageConverter().getObjectMapper();
	}


	/**
	 * Parse the double into a BigDecimal, as REST is expected to format it (scale, rounding mode)
	 *
	 * @param pValue Double value
	 * @return BigDecimal value
	 */
	public static BigDecimal valueOf(Double pValue) {
		BigDecimal res = null;
		if(pValue != null && ! pValue.equals(Double.NaN)) {
			res = BigDecimal.valueOf(pValue);
			res = res.setScale(BIG_DECIMAL_SCALE, BIG_DECIMAL_ROUNDING_MODE);
		}
		return res;
	}

}
