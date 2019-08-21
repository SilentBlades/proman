package com.upgrad.proman.api.controller;

import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.proman.api.model.AuthorizedUserResponse;
import com.upgrad.proman.service.business.AuthenticationBusinessService;
import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.AuthenticationFailedException;

@RestController
@RequestMapping("/")
public class AuthenticationController {

	@Autowired
	private AuthenticationBusinessService authenticationService;

	@RequestMapping(method = RequestMethod.POST, path = "/auth/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<AuthorizedUserResponse> login(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException {
		byte[] decodedValue = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
		
		String decodedText = new String(decodedValue);
		String[] decodedArray = decodedText.split(":");
		
		UserAuthTokenEntity userAuthTokenEntity = authenticationService.authenticate(decodedArray[0], decodedArray[1]);
		UserEntity userEntity = userAuthTokenEntity.getUser();
		
		AuthorizedUserResponse authorizedUserResponse =
				new AuthorizedUserResponse().id(UUID.fromString(userEntity.getUuid()))
									.firstName(userEntity.getFirstName())
									.lastName(userEntity.getLastName())
									.emailAddress(userEntity.getEmail())
									.mobilePhone(userEntity.getMobilePhone())
									.lastLoginTime(userEntity.getLastLoginAt());
									
		HttpHeaders header = new HttpHeaders();
		header.add("access-token", userAuthTokenEntity.getAccessToken());
									
		return new ResponseEntity<AuthorizedUserResponse>(authorizedUserResponse, header, HttpStatus.OK);
	}
}
