/**
 * Copyright nasrpi 2020
 */

package com.nasrpi.resourceserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * @author zuilee
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Api
@RequestMapping
public class ResourceServerController {

	@Autowired ResourceServerRepository resourceServerRepository;
	
	@RequestMapping(value = "/getUserDetails", method = RequestMethod.POST)
	public UserDetailsModel getUserDetails(@RequestBody final String accessToken) {
		return resourceServerRepository.getUserDetails(accessToken);
	}
}
