package com.sifast.socle.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends
		AuthorizationServerConfigurerAdapter {

	private static final String CLIENT_CREDENTIALS = "client_credentials";

	static final String CLIEN_ID = "client-id";

	static final String CLIENT_SECRET = "client-secret";

	static final String GRANT_TYPE_PASSWORD = "password";

	static final String AUTHORIZATION_CODE = "authorization_code";

	static final String REFRESH_TOKEN = "refresh_token";

	static final String IMPLICIT = "implicit";

	static final String SCOPE_READ = "read";

	static final String SCOPE_WRITE = "write";

	static final String TRUST = "trust";

	static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 60 * 60;

	static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60 * 60;

	static final String SERVER_RESOURCE_ID = "oauth2-sifast-server";

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer)
			throws Exception {

		configurer
				.inMemory()
				.withClient(CLIEN_ID)
				.secret(CLIENT_SECRET)
				.authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE,
						REFRESH_TOKEN, IMPLICIT, CLIENT_CREDENTIALS)
				.scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
				.accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
				.refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
	}


	@SuppressWarnings("deprecation")
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security)
			throws Exception {
		security.allowFormAuthenticationForClients()
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()")
				.passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		endpoints.tokenStore(tokenStore).authenticationManager(
				authenticationManager);
	}
}