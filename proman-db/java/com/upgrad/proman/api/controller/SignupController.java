package com.upgrad.proman.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.proman.api.model.SignupUserRequest;
import com.upgrad.proman.api.model.SignupUserResponse;

@RestController
@RequestMapping("/")
public class SignupController {

	@RequestMapping(method = RequestMethod.POST, path = "/signup", consumes = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
	public ResponseEntity<SignupUserResponse> signup(final SignupUserRequest signUpUserRequest) {
		return new ResponseEntity<SignupUserResponse>(HttpStatus.OK);
	}
}
