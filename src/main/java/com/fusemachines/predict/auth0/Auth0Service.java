package com.fusemachines.predict.auth0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.http.HttpHeaders;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.users.User;
import com.auth0.json.mgmt.users.UsersPage;
import com.auth0.net.Request;
import com.fusemachines.predict.auth0.dto.MasterTokenRequestDto;
import com.fusemachines.predict.utils.JsonUtils;
import com.google.gson.JsonObject;

@Component
public class Auth0Service {
	
	private static String AUTH0_DOMAIN;
	private static String AUTH0_API_EXPLORER_CLIENTID;
	private static String AUTH0_API_EXPLORER_CLIENTSECRET;
	private static String AUTH0_API_EXPLORER_AUDIENCE;
	private static String AUTH0_TOKEN_URL;
	private static String masterToken;
	private static final String GRANT_TYPE = "client_credentials";
	
	private static final Logger logger = LoggerFactory.getLogger(Auth0Service.class);
	
	@Value("${auth0.domain}")
	private void setAuth0Domain(String auth0Domain) {
		AUTH0_DOMAIN = auth0Domain;
	}
	
	@Value("${auth0.apiExplorer.clientId}")
	private void setAuth0ClientId(String clientId) {
		AUTH0_API_EXPLORER_CLIENTID = clientId;
	}
	
	@Value("${auth0.apiExplorer.clientSecret}")
	private void setAuth0ClientSecret(String clientSecret) {
		AUTH0_API_EXPLORER_CLIENTSECRET = clientSecret;
	}
	
	@Value("${auth0.apiExplorer.audience}")
	private void setAuth0Audience(String audience) {
		AUTH0_API_EXPLORER_AUDIENCE = audience;
	}
	
	@Value("${auth0.token.url}")
	private void setTokenUrl(String tokenUrl) {
		AUTH0_TOKEN_URL = tokenUrl;
	}
	
	@PostConstruct
	@Scheduled(cron = "0 0/50 * * * *")
	public static void getMasterToken() {
		logger.info("Generating master token");
		CredentialsProvider provider = new BasicCredentialsProvider();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

		HttpPost httpPost = new HttpPost(AUTH0_TOKEN_URL);
		httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

		JsonObject object = new JsonObject();
		object.addProperty("grant_type", GRANT_TYPE);
		object.addProperty("client_id", AUTH0_API_EXPLORER_CLIENTID);
		object.addProperty("client_secret", AUTH0_API_EXPLORER_CLIENTSECRET);
		object.addProperty("audience", AUTH0_API_EXPLORER_AUDIENCE);
		try {
			StringEntity userEntity = new StringEntity(JsonUtils.toString(object));
			httpPost.setEntity(userEntity);
			CloseableHttpResponse resposne = client.execute(httpPost);
			MasterTokenRequestDto masterTokenRequestDto = JsonUtils.toObject(EntityUtils.toString(resposne.getEntity()), MasterTokenRequestDto.class);
			masterToken = masterTokenRequestDto.getAccessToken();
		} catch (IOException e) {
			logger.error("Exception occured while fetching master token. Exception: ", e);
		}
	}
	
	public static ManagementAPI getManagementAPI() {
		return new ManagementAPI(AUTH0_DOMAIN, masterToken);
	}
	
	public static List<User> getAllUsers() {
		UserFilter filter = new UserFilter();
		filter.withPage(0, 50);
		Request<UsersPage> request = getManagementAPI().users().list(filter);
		List<User> users = new ArrayList<>();
		
		UsersPage usersPage = null;
		try {
			usersPage = request.execute();
			users.addAll(usersPage.getItems());
			if(usersPage.getItems().size() >= 50) {
				filter.withPage(1, 50);
				request = getManagementAPI().users().list(filter);
				usersPage = request.execute();
				users.addAll(usersPage.getItems());
			}
				
		} catch (Auth0Exception exception) {
			logger.info("Exception occured while updating auth0 user. Exception: {}", exception);
		}
		
		return users;
	}
	
	public static User getUser(String userId) {
		Request<User> request = getManagementAPI().users().get(userId, null);
		User user = new User();
		try {
			user = request.execute();
		} catch (Auth0Exception exception) {
			logger.info("Exception occured while updating auth0 user. Exception: {}", exception);
		}
		
		return user;
	}
	
}
