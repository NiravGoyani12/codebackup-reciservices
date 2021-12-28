package com.rcibanque.rcidirect.services.core.utils.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public class HttpUtils {

	private HttpUtils() {}


	public static HttpResponse post(String pUrl, String pOutputMessage, String pContentType, int pTimeoutMilliseconds, SSLSocketFactory pSSLSocketFactory) {

		return process(pUrl, HttpMethod.POST, pContentType, pOutputMessage, pTimeoutMilliseconds, pSSLSocketFactory, null);
	}

	public static HttpResponse post(String pUrl, String pOutputMessage, String pContentType, int pTimeoutMilliseconds, SSLSocketFactory pSSLSocketFactory, Map<String, String> pRequestProperties) {

		return process(pUrl, HttpMethod.POST, pContentType, pOutputMessage, pTimeoutMilliseconds, pSSLSocketFactory, pRequestProperties);
	}


	public static HttpResponse get(String pUrl, int pTimeoutMilliseconds, SSLSocketFactory pSSLSocketFactory) {

		return process(pUrl, HttpMethod.GET, null, null, pTimeoutMilliseconds, pSSLSocketFactory, null);
	}


	private static HttpResponse process(String pUrl, HttpMethod pHttpMethod, String pContentType, String pHttpBody, int pTimeoutMilliseconds, SSLSocketFactory pSSLSocketFactory, Map<String, String> pRequestProperties) {

		HttpResponse retval = new HttpResponse();
		HttpURLConnection httpUrlConnection = null;

		try {

			URL url = new URL(pUrl);
			httpUrlConnection = (HttpURLConnection) url.openConnection();

			if (httpUrlConnection instanceof HttpsURLConnection && pSSLSocketFactory != null) {
				((HttpsURLConnection) httpUrlConnection).setSSLSocketFactory(pSSLSocketFactory);
			}

			if(StringUtils.isNotBlank(pContentType)) {
				httpUrlConnection.setRequestProperty(HttpHeaders.CONTENT_TYPE, pContentType);
			}

			if (pRequestProperties != null) {

				for (String key : pRequestProperties.keySet()) {

					httpUrlConnection.setRequestProperty(key,  pRequestProperties.get(key));
				}
			}

			httpUrlConnection.setRequestMethod(pHttpMethod.toString());
			httpUrlConnection.setConnectTimeout(pTimeoutMilliseconds);
			httpUrlConnection.setReadTimeout(pTimeoutMilliseconds);
			httpUrlConnection.setInstanceFollowRedirects(false);

			if(StringUtils.isNotBlank(pHttpBody)) {
				httpUrlConnection.setDoOutput(true);
				IOUtils.write(pHttpBody, httpUrlConnection.getOutputStream(), (Charset) null);
			}

			retval.setHttpResponseCode(httpUrlConnection.getResponseCode());
			retval.setHttpResponseMessage(httpUrlConnection.getResponseMessage());

			try {
				retval.setOutput(IOUtils.toString(httpUrlConnection.getInputStream(), (Charset) null));
			}
			catch (SocketTimeoutException e) {
				retval.setError(true);
				retval.setErrorMessage(e.getMessage());
			}
			catch (IOException e) {
				retval.setError(true);
				retval.setErrorMessage(e.getMessage());
				retval.setOutput(IOUtils.toString(httpUrlConnection.getErrorStream(), (Charset) null));
			}
		}
		catch (Exception e) {
			retval.setError(true);
			retval.setErrorMessage(e.getMessage());
		}
		finally {

			if (httpUrlConnection != null) {
				httpUrlConnection.disconnect();
			}
		}

		return retval;
	}

}
