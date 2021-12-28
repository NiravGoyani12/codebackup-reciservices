package com.rcibanque.rcidirect.services.core.user;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class UserDetails extends org.springframework.security.core.userdetails.User implements IUserProxy {

	private static final long serialVersionUID = -7389653242248338831L;


	private final User _user;


	public UserDetails(User pUserInfo) {

		super(pUserInfo.getUsername(), pUserInfo.getPassword(),
				pUserInfo.getEnabled(), pUserInfo.getEnabled(), pUserInfo.getEnabled(), pUserInfo.getEnabled(),
				Arrays.asList(new SimpleGrantedAuthority(pUserInfo.getProfile())));

		_user = pUserInfo;
	}


	@Override
	public User getUser() {
		return _user;
	}

}
