package com.upgrad.proman.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.upgrad.proman.service.dao.UserDAO;
import com.upgrad.proman.service.entity.UserEntity;

@Service
public class SignupBusinessService {	
	@Autowired
	private UserBusinessService userBusinessService;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UserEntity signup(UserEntity userEntity) {
		return userBusinessService.createUser(userEntity);
	}
}
