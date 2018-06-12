package com.fusemachines.predict.auth0.dto;

import com.google.gson.annotations.SerializedName;

public class MasterTokenRequestDto {
	@SerializedName("access_token")
	private String accessToken;
	private String scope;
	@SerializedName("expires_in")
	private String expiresIn;
	@SerializedName("token_type")
	private String tokenType;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public String toString() {
		return "MasterTokenRequestDto [accessToken=" + accessToken + ", scope=" + scope + ", expiresIn=" + expiresIn
				+ ", tokenType=" + tokenType + "]";
	}

}
