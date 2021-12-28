package com.rcibanque.rcidirect.services.core.config.ssl;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509KeyManager;

/**
 * This class operates with SSLSocketFactoryGenerator and allows us to pick a particular
 * client certificate within a key-store using its alias.
 *
 * @author Roger Hill
 */
public class AliasSelectingKeyManager implements X509KeyManager {

	private X509KeyManager _sourceKeyManager = null;
	private String _alias;

	@SuppressWarnings("unused")
	private AliasSelectingKeyManager() {

	}

	public AliasSelectingKeyManager(X509KeyManager keyManager, String alias) {

		_sourceKeyManager = keyManager;
		_alias = alias;
	}

	@Override
	public String[] getClientAliases(String keyType, Principal[] issuers) {
		return _sourceKeyManager.getClientAliases(keyType, issuers);
	}

	@Override
	public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {

		boolean aliasFound=false;

		//Get all aliases from the key manager. If any matches with the managed alias,
		//then return it.
		//If the alias has not been found, return null (and let the API to handle it, causing the handshake to fail).
		for (int i = 0; i < keyType.length && !aliasFound; i++) {

			String[] validAliases=_sourceKeyManager.getClientAliases(keyType[i], issuers);
			if (validAliases != null) {
				for (int j = 0; j < validAliases.length && !aliasFound; j++) {
					if (validAliases[j].equals(_alias)) {
						aliasFound = true;
					}
				}
			}
		}

		if (aliasFound) {
			return _alias;
		} else {
			return null;
		}
	}

	@Override
	public String[] getServerAliases(String keyType, Principal[] issuers) {
		return _sourceKeyManager.getServerAliases(keyType, issuers);
	}

	@Override
	public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
		return _sourceKeyManager.chooseServerAlias(keyType, issuers, socket);
	}

	@Override
	public X509Certificate[] getCertificateChain(String alias) {
		return _sourceKeyManager.getCertificateChain(alias);
	}

	@Override
	public PrivateKey getPrivateKey(String alias) {
		return _sourceKeyManager.getPrivateKey(alias);
	}
}
