/**
 * Copyright nasrpi 2020
 */

package com.nasrpi.authenticationserver;

import org.springframework.stereotype.Service;

/**
 * @author zuilee
 */

@Service
public class AuthServerUtils {

	public boolean verifyAccessToken(String accessToken) {

		AuthServerRepository authServerRepository = new AuthServerRepository();

		if (accessToken.equals(authServerRepository.accessToken)) 
			return true;
		else
			return false;
	}
}
