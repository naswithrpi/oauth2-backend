/**
 * Copyright nasrpi 2020
 */
package com.nasrpi.authenticationserver;

import java.io.FileReader;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.nasrpi.common.KeyConstants;

/**
 * @author grandolf49
 */
@Repository
public class AuthServerRepository {

	@Value("${server.address}")
	private String serverAddress;

	@Value("${server.port}")
	private String serverPort;

	@Value("${client.web.port}")
	private String clientUrl;

	private static String authCode = "";
	public static String accessToken = "";
	public static String userID = "";

	public ClientAuthModel auth(ClientAuthModel clientAuthModel) {

		JSONParser parser = new JSONParser();
		Object obj = null;

		Base64 base64 = new Base64();
		String encodedString = new String(base64.encode(clientAuthModel.getClientID().toString().getBytes()));

		try {
			obj = parser.parse(new FileReader(KeyConstants.BASE_PATH + AuthConstants.CLIENT_DETAILS_JSON));

			JSONObject jsonObject = (JSONObject) obj;
			List<JSONObject> clientObject = (List<JSONObject>) jsonObject.get(AuthConstants.CLIENT_DETAILS);

			for (JSONObject client : clientObject) {
				if (client.get(AuthConstants.CLIENT_ID).toString().equals(encodedString)) {
					clientAuthModel.setRedirectionURL(constructUrl(serverAddress, serverPort));
					break;
				} else {
					clientAuthModel.setRedirectionURL(AuthConstants.REDIRECTION_URL_ERROR);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return clientAuthModel;
	}

	private String constructUrl(String serverAddress2, String serverPort2) {
		// TODO Auto-generated method stub
		return "http://" + serverAddress2 + ":" + clientUrl + "/";
	}

	public AuthCodeModel login(LoginModel loginModel) {

		JSONParser parser = new JSONParser();
		Object obj = null;

		AuthCodeModel authCodeModel = new AuthCodeModel();

		Base64 base64 = new Base64();
		String encodedUsername = new String(base64.encode(loginModel.getUsername().toString().getBytes()));
		String encodedPassword = new String(base64.encode(loginModel.getPassword().toString().getBytes()));
		authCode = "";

		try {
			obj = parser.parse(new FileReader(KeyConstants.BASE_PATH + AuthConstants.USER_CREDENTIALS_JSON));

			JSONObject jsonObject = (JSONObject) obj;
			List<JSONObject> userCredList = (List<JSONObject>) jsonObject.get(AuthConstants.USER_CREDENTIALS);

			for (JSONObject userCred : userCredList) {
				// System.out.println(userCred.get("username").toString() + " " +
				// userCred.get("password").toString());
				// System.out.println(encodedUsername + " " + encodedPassword);

				if (userCred.get(AuthConstants.USERNAME).toString().equals(encodedUsername)) {
					if (userCred.get(AuthConstants.PASSWORD).toString().equals(encodedPassword)) {
						userID = userCred.get(KeyConstants.USER_ID).toString();
						// System.out.println("userID in auth repo " + userID);
						authCode = generateCode();
						authCodeModel.setAuthCode(authCode);
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return authCodeModel;
	}

	private String generateCode() {
		int count = 16;
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * KeyConstants.ALPHA_NUMERIC_STRING.length());
			builder.append(KeyConstants.ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();

	}

	public AccessTokenModel getAccessToken(String clientAuthCode) {

		accessToken = "";
		AccessTokenModel accessTokenModel = new AccessTokenModel();

		// System.out.println(clientAuthCode + " " + authCode);
		if (clientAuthCode.equals(authCode)) {
			accessToken = generateCode();
			accessTokenModel.setAccessToken(accessToken);
		}

		return accessTokenModel;
	}

}
