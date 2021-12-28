package com.rcibanque.rcidirect.services.core.utils;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

public class ProxyUtils {


	private static final String HTTP_PROXY_HOST 		= "http.proxyHost";
	private static final String HTTP_PROXY_PORT 		= "http.proxyPort";
	private static final String HTTP_PROXY_USER 		= "http.proxyUser";
	private static final String HTTP_PROXY_PASSWORD		= "http.proxyPassword"; //NOSONAR

	private static final String HTTPS_PROXY_HOST 		= "https.proxyHost";
	private static final String HTTPS_PROXY_PORT 		= "https.proxyPort";
	private static final String HTTPS_PROXY_USER 		= "https.proxyUser";
	private static final String HTTPS_PROXY_PASSWORD	= "https.proxyPassword"; //NOSONAR

	private static final String HTTP_NON_PROXY_HOSTS 	= "http.nonProxyHosts";


	private ProxyUtils() {
		super();
	}


	public static void setupProxy(Environment pEnv) {

		System.setProperty(HTTP_PROXY_HOST, pEnv.getProperty(HTTP_PROXY_HOST, StringUtils.EMPTY));
		System.setProperty(HTTP_PROXY_PORT, pEnv.getProperty(HTTP_PROXY_PORT, StringUtils.EMPTY));
		System.setProperty(HTTP_PROXY_USER, pEnv.getProperty(HTTP_PROXY_USER, StringUtils.EMPTY));
		System.setProperty(HTTP_PROXY_PASSWORD, pEnv.getProperty(HTTP_PROXY_PASSWORD, StringUtils.EMPTY));

		System.setProperty(HTTPS_PROXY_HOST, pEnv.getProperty(HTTPS_PROXY_HOST, StringUtils.EMPTY));
		System.setProperty(HTTPS_PROXY_PORT, pEnv.getProperty(HTTPS_PROXY_PORT, StringUtils.EMPTY));
		System.setProperty(HTTPS_PROXY_USER, pEnv.getProperty(HTTPS_PROXY_USER, StringUtils.EMPTY));
		System.setProperty(HTTPS_PROXY_PASSWORD, pEnv.getProperty(HTTPS_PROXY_PASSWORD, StringUtils.EMPTY));

		System.setProperty(HTTP_NON_PROXY_HOSTS, pEnv.getProperty(HTTP_NON_PROXY_HOSTS, StringUtils.EMPTY));


		Authenticator.setDefault(new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {

				// Only communicate credential to a proxy
				if (getRequestorType() == RequestorType.PROXY) {

					String prot = getRequestingProtocol().toLowerCase();

					String host = System.getProperty(prot + ".proxyHost");
					String port = System.getProperty(prot + ".proxyPort");

					// Only communicate credentials to referenced proxy
					if (getRequestingHost().equalsIgnoreCase(host)) {
						if (getRequestingPort() == Integer.parseInt(port)) {

							String user = System.getProperty(prot + ".proxyUser");
							String password = System.getProperty(prot + ".proxyPassword");

							return new PasswordAuthentication(user, password.toCharArray());
						}
					}
				}
				return null;
			}
		});
	}


}
