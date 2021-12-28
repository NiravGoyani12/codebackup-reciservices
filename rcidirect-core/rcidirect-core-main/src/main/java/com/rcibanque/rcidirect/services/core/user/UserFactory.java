package com.rcibanque.rcidirect.services.core.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

/**
 * Creates users or gets them from the session
 */
public final class UserFactory {


	private UserFactory() {}


	public static IUser getUser() {

		Authentication authentication = getSecurityContext().getAuthentication();

		IUser res = null;

		if(authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() instanceof IUserProxy) {
			res = ((IUserProxy) authentication.getPrincipal()).getUser();
		}

		return res;
	}


	private static SecurityContext getSecurityContext() {

		return SecurityContextHolder.getContext();
	}


	public static void setAuthentication(UserDetails pUser) {

		if(pUser != null) {
			getSecurityContext().setAuthentication(new UsernamePasswordAuthenticationToken(pUser, null, pUser.getAuthorities()));
		}
	}


	public static void updateSessionUser(WebRequest pWebRequest) {

		pWebRequest.setAttribute(
				HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				getSecurityContext(),
				RequestAttributes.SCOPE_SESSION
				);
	}

}
