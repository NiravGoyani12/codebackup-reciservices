package com.rcibanque.rcidirect.services.core.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rcibanque.rcidirect.services.core.exception.ExceptionUtils;

public class ZipUtils {

	private ZipUtils() {}

	private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtils.class);

	public static byte[] zip(String pText) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gzipStream;
		if (StringUtils.isNotBlank(pText)) {
			try {
				gzipStream = new GZIPOutputStream(baos);
				gzipStream.write(pText.getBytes(StandardCharsets.UTF_8));
				gzipStream.close();
			} catch (IOException e) {
				LOGGER.error("Error zipping content: {}", ExceptionUtils.getStackTrace(e));
			}
		}
		return baos.toByteArray();

	}

	public static String unZip(byte[] pText) {

		String unzippedContent = "";

		if (null != pText) {
			BufferedReader buffer;
			try {
				buffer = new BufferedReader(new InputStreamReader(new GZIPInputStream(new ByteArrayInputStream(pText)), StandardCharsets.UTF_8));
				unzippedContent = buffer.lines().collect(Collectors.joining(StringUtils.LF));
			} catch (IOException e) {
				LOGGER.error("Error unzipping content: {}", ExceptionUtils.getStackTrace(e));
			}
		}
		return unzippedContent;
	}

}