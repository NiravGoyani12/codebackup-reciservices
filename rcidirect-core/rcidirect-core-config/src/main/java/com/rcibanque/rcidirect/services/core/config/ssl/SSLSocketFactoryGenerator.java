package com.rcibanque.rcidirect.services.core.config.ssl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;

import org.apache.commons.lang3.StringUtils;

/**
 * This class allows us to create HTTPS connections using a key-store and trust-store selected at runtime
 * instead of using the java system properties to determine which ones are used.
 *
 * @author Roger Hill
 */
public class SSLSocketFactoryGenerator {

	private String _trustStore;
	private String _trustStorePassword;
	private String _keyStore;
	private String _keyStorePassword;
	private String _keyStoreAlias;

	public SSLSocketFactoryGenerator(String trustStore,
			String trustStorePassword,
			String keyStore,
			String keyStorePassword,
			String alias) {

		_trustStore = trustStore;
		_trustStorePassword = trustStorePassword;
		_keyStore = keyStore;
		_keyStorePassword = keyStorePassword;
		_keyStoreAlias = alias;
	}

	public SSLSocketFactory getSSLSocketFactory() throws IOException, GeneralSecurityException {

		KeyManager[] keyManagers = getKeyManagers();
		TrustManager[] trustManagers = getTrustManagers();

		// For each key manager, check if it is a X509KeyManager because we will override its functionality
		for (int i=0; i<keyManagers.length; i++) {

			if (keyManagers[i] instanceof X509KeyManager) {
				keyManagers[i] = new AliasSelectingKeyManager((X509KeyManager)keyManagers[i], _keyStoreAlias);
			}
		}

		SSLContext context=SSLContext.getInstance("SSL");
		context.init(keyManagers, trustManagers, null);

		return context.getSocketFactory();
	}

	private KeyManager[] getKeyManagers() throws IOException, GeneralSecurityException {

		KeyManager[] kms = null;

		String alg = KeyManagerFactory.getDefaultAlgorithm();
		KeyManagerFactory kmFact = KeyManagerFactory.getInstance(alg);

		try(InputStream fis = new FileInputStream(_keyStore)) {

			// Init a key store with the given file
			KeyStore ks = KeyStore.getInstance("jks");
			ks.load(fis, _keyStorePassword.toCharArray());

			// Init the key manager factory with the loaded key store
			kmFact.init(ks, _keyStorePassword.toCharArray());

			kms = kmFact.getKeyManagers();
		}
		return kms;
	}

	private TrustManager[] getTrustManagers() throws IOException, GeneralSecurityException {

		TrustManager[] retval = null;

		// The trust-store is only configured for the DEV Au10tix end-point (because that end-point does not have a proper server certificate)
		// So we must expect this value to be blank and return null when no trust-store is configured.
		if (StringUtils.isNotBlank(_trustStore)) {

			String alg=TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory tmFact = TrustManagerFactory.getInstance(alg);

			try(InputStream fis = new FileInputStream(_trustStore)) {

				KeyStore ks=KeyStore.getInstance("jks");
				ks.load(fis, _trustStorePassword.toCharArray());

				tmFact.init(ks);

				retval = tmFact.getTrustManagers();
			}
		}

		return retval;
	}
}
