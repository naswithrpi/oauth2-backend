/**
 * Copyright nasrpi 2020
 */

package com.nasrpi.resourceserver;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nasrpi.authenticationserver.AuthServerRepository;
import com.nasrpi.authenticationserver.AuthServerUtils;
import com.nasrpi.common.KeyConstants;

/**
 * @author zuilee
 */

@Repository
public class ResourceServerRepository {

	@Autowired
	AuthServerUtils authServerUtils;

	@Autowired
	AuthServerRepository authServerRepository;

	public UserDetailsModel getUserDetails(String accessToken) {

		UserDetailsModel userDetailsModel = new UserDetailsModel();

		if (authServerUtils.verifyAccessToken(accessToken)) {
			JSONParser parser = new JSONParser();
			Object obj = null;

			try {
				obj = parser.parse(new FileReader(KeyConstants.BASE_PATH + ResourceConstants.USER_DETAILS_JSON));

				JSONObject jsonObject = (JSONObject) obj;
				List<JSONObject> userDetailList = (List<JSONObject>) jsonObject.get(ResourceConstants.USER_DETAILS);

				for (JSONObject userDetail : userDetailList) {

					// System.out.println(userDetail.get("userID").toString() + " " +
					// authServerRepository.userID);

					if (userDetail.get(KeyConstants.USER_ID).toString().equals(authServerRepository.userID)) {

						userDetailsModel.setUserName(userDetail.get(ResourceConstants.NAME).toString());
						userDetailsModel.setEmail(userDetail.get(ResourceConstants.EMAIL).toString());
						userDetailsModel.setMobile(userDetail.get(ResourceConstants.MOBILE).toString());
						userDetailsModel.setDateOfBirth(userDetail.get(ResourceConstants.DOB).toString());

						break;
					}
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		return userDetailsModel;
	}
}
