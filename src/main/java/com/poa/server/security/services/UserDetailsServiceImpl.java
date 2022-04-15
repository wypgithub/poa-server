package com.poa.server.security.services;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dpoa.server.models.Role;
import com.dpoa.server.models.User;
import com.dpoa.server.models.UserEntity;
import com.dpoa.server.payload.request.LoginRequest;
import com.dpoa.server.repository.RoleRepository;
import com.dpoa.server.security.jwt.JwtUtils;
import com.dpoa.server.service.AzureAPI;
import com.dpoa.server.service.user.UserService;
import com.dpoa.server.util.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Value("${dpoa.app.jwtExpirationMs}")
	private Long time;

	private final AzureAPI azureAPI;
	private final BoundHashOperations<String, String, Object> azureAdUserIdRedis;
	private final UserService userService;
	private final RoleRepository roleRepository;
	private final JwtUtils jwtUtils;

	public UserDetailsServiceImpl(AzureAPI azureAPI, UserService userService, RoleRepository roleRepository,
								  @Qualifier("azureAdUserId") BoundHashOperations<String, String, Object> azureAdUserIdRedis,
								  JwtUtils jwtUtils) {
		this.azureAPI = azureAPI;
		this.azureAdUserIdRedis = azureAdUserIdRedis;
		this.userService = userService;
		this.roleRepository = roleRepository;
		this.jwtUtils = jwtUtils;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String key) {

		if (azureAdUserIdRedis.hasKey(key)) {
			return JSONUtil.parseObj(azureAdUserIdRedis.get(key)).toBean(UserDetailsImpl.class);
		}

		try {
			LoginRequest loginRequest = JSONUtil.toBean(key, LoginRequest.class);
			// login by source
			UserEntity user = null;
			switch (loginRequest.getSource()) {
				case "LocalSystem":
					user = localSystemLogin(loginRequest);
					break;
				case "AzureAD":
					user = azureADLogin(loginRequest);
					break;
				case "Google":
					user = googleLogin(loginRequest);
					break;
				case "Facebook":
					user = facebookLogin(loginRequest);
					break;
				case "refreshToken":

				default:
					throw new UsernameNotFoundException("Invalid user source!");
			}
			if (user == null) {
				throw new UsernameNotFoundException("User does not exist!");
			}
			// query role relation
			List<Role> roleList = roleRepository.findRolesByUserId(user.getId());
			user.setRoles(roleList);
			azureAdUserIdRedis.expire(time + 1800000, TimeUnit.MILLISECONDS);
			azureAdUserIdRedis.put(user.getId(), JSONUtil.parse(user).toString());

			return UserDetailsImpl.build(user);
		} catch (IOException e) {
			return null;
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

	/**
	 * use facebook login
	 * if user does not exists, use facebook information create user
	 *
	 * @param loginRequest login request
	 * @return user
	 */
	private UserEntity facebookLogin(LoginRequest loginRequest) {
		String jwtSubject = jwtUtils.getUserNameFromJwtToken(loginRequest.getToken());
		JSONObject subjectObj = JSONUtil.parseObj(jwtSubject);


		return null;
	}

	/**
	 * use google login
	 * if user does not exists, use google information create user
	 *
	 * @param loginRequest login request
	 * @return user
	 */
	private UserEntity googleLogin(LoginRequest loginRequest) {
		try {
			String token = loginRequest.getToken();
			// get subject by token
			String subStr1 = token.substring(token.indexOf(".") + 1);

			String json = new String(Base64Decoder.decode(subStr1.substring(0, subStr1.indexOf("."))), "UTF-8");

			Date date = new Date();

			JSONObject googleUser = JSONUtil.parseObj(json);

			UserEntity userEntity = new UserEntity();
			userEntity.setFirstName(googleUser.getStr("family_name"));
			userEntity.setLastName(googleUser.getStr("given_name"));
			userEntity.setEmail(googleUser.getStr("email"));
			userEntity.setUsername(googleUser.getStr("email"));
			userEntity.setPassword("$2a$10$QCg/mh/0rDq8Dpzb9tIb1e9LxjiK8.5TEPeNCiPBFbJUPyB45WNwW");
			userEntity.setStatus(Constants.UserStatusEnum.ACTIVATED.getCode());
			userEntity.setCreatedTime(date);
			userEntity.setUpdatedTime(date);
			userEntity.setSource(Constants.UserSourceEnum.GOOGLE.getCode());

			userService.save(userEntity);

			return userEntity;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * use azure ad login
	 * user azure ad token, query azure ad get user information
	 *
	 * @param loginRequest login request
	 * @return user
	 */
	private UserEntity azureADLogin(LoginRequest loginRequest) throws IOException {
		JSONObject result = azureAPI.usersMe(loginRequest.getToken());

		if (result.containsKey("error")) {
			throw new UsernameNotFoundException(result.getJSONObject("error").getStr("message"));
		}

		User user = new User();
		BeanUtil.copyProperties(result, user);
		user.setPassword("$2a$10$QCg/mh/0rDq8Dpzb9tIb1e9LxjiK8.5TEPeNCiPBFbJUPyB45WNwW");
		user.setUsername(loginRequest.getToken());
		UserEntity userEntity = null/*UserUtil.userToUserEntity(user, new UserEntity())*/;

		userService.save(userEntity);

		return userEntity;
	}

	/**
	 * local user login
	 *
	 * @param loginRequest login request
	 * @return user
	 */
	private UserEntity localSystemLogin(LoginRequest loginRequest) {
		// find user name
		return userService.findByUsername(loginRequest.getUsername()).orElseThrow(() ->
				new UsernameNotFoundException("User Not Found with username: " + loginRequest.getUsername()));

	}

}
