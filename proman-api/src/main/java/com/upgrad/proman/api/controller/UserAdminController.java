package com.upgrad.proman.api.controller;

import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.proman.api.model.UserDetailsResponse;
import com.upgrad.proman.api.model.UserStatusType;
import com.upgrad.proman.service.business.UserBusinessService;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.type.UserStatus;

@RestController
@RequestMapping("/")
public class UserAdminController {
	
	@Autowired
	private UserBusinessService userBusinessService;
	
	@RequestMapping(method = RequestMethod.GET, path = "/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("id") final String Uuid) {
		UserEntity userEntity = userBusinessService.getUser(Uuid);
		
		UserDetailsResponse userDetailsResponse = 
				new UserDetailsResponse().id(userEntity.getUuid())
										 .firstName(userEntity.getFirstName())
										 .lastName(userEntity.getLastName())
										 .emailAddress(userEntity.getEmail())
										 .mobileNumber(userEntity.getMobilePhone())
										 .status(UserStatusType.valueOf(UserStatus.getEnum(userEntity.getStatus()).name()));
		
		return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
	}
}
