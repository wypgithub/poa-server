package com.poa.server.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	private String username;

	private String token;

	private String password;

	/**
	 * user source
	 */
	private String source;

}
