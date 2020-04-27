/**
 * Copyright nasrpi 2020
 */
package com.nasrpi.authenticationserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * @author grandolf49
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Api
@RequestMapping
public class AuthServerController {
	@Autowired
	private AuthServerRepository authServerRepository;
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ClientAuthModel auth(@RequestBody ClientAuthModel clientAuthModel) {
		return authServerRepository.auth(clientAuthModel);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public AuthCodeModel login(@RequestBody LoginModel loginModel) {
		return authServerRepository.login(loginModel);
	}
	
	@RequestMapping(value = "/getAccessToken", method = RequestMethod.POST)
	public AccessTokenModel getAccessToken(@RequestBody String authCode) {
		return authServerRepository.getAccessToken(authCode);
	}
}
