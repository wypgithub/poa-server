package com.poa.server.security.services;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.poa.server.entity.User;
import com.poa.server.security.LoginRequest;
import com.poa.server.security.jwt.JwtUtils;
import com.poa.server.util.AzureAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Value("${dpoa.app.jwtExpirationMs}")
	private Long time;

	private final AzureAPI azureAPI;
	private final JwtUtils jwtUtils;

	public UserDetailsServiceImpl(AzureAPI azureAPI,
								  JwtUtils jwtUtils) {
		this.azureAPI = azureAPI;
		this.jwtUtils = jwtUtils;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String key) {


		try {
			LoginRequest loginRequest = JSONUtil.toBean(key, LoginRequest.class);
			// login by source
			User user = null;
			switch (loginRequest.getSource()) {

				case "AzureAD":
					user = azureADLogin(loginRequest);
					break;

				case "refreshToken":

				default:
					throw new UsernameNotFoundException("Invalid user source!");
			}
			if (user == null) {
				throw new UsernameNotFoundException("User does not exist!");
			}

			return UserDetailsImpl.build(user);
		} catch (IOException e) {
			return null;
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}


	/**
	 * use azure ad login
	 * user azure ad token, query azure ad get user information
	 *
	 * @param loginRequest login request
	 * @return user
	 */
	private User azureADLogin(LoginRequest loginRequest) throws IOException {
		JSONObject result = azureAPI.usersMe(loginRequest.getToken());

		if (result.containsKey("error")) {
			throw new UsernameNotFoundException(result.getJSONObject("error").getStr("message"));
		}


		return null;
	}


}
