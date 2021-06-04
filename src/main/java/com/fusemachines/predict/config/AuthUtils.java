/**
 * 
 */
package com.fusemachines.predict.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class AuthUtils {

	private static final Logger logger = LoggerFactory.getLogger(AuthUtils.class);


	public String getAuth0Id() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}