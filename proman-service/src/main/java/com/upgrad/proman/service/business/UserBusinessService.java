package com.upgrad.proman.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upgrad.proman.service.dao.UserDAO;
import com.upgrad.proman.service.entity.RoleEntity;
import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.ResourceNotFoundException;
import com.upgrad.proman.service.exception.UnauthorizedException;

@Service
public class UserBusinessService {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private PasswordCryptographyProvider cryptographyProvider;
	
	public UserEntity getUser(final String Uuid, final String authorizationToken) throws ResourceNotFoundException, UnauthorizedException {
		UserAuthTokenEntity userAuthTokenEntity = userDao.getUserAuthToken(authorizationToken);
		RoleEntity roleEntity = userAuthTokenEntity.getUser().getRole();
		
		if(roleEntity != null && roleEntity.getUuid() == 101) {
			UserEntity userEntity = userDao.getUser(Uuid);
			if(userEntity == null) {
				throw new ResourceNotFoundException("USR-001", "User not found!");
			}
			return userEntity;	
		} 
		
		throw new UnauthorizedException("ATHZ-001", "You are not authorized to carry out this task!");
	}
	
	public UserEntity createUser(final UserEntity userEntity) {
		String password = userEntity.getPassword();
		
		if(password == null) {
			password = "abc@123";
		}
		
		String[] encrytpedText = cryptographyProvider.encrypt(password);
		userEntity.setSalt(encrytpedText[0]);
		userEntity.setPassword(encrytpedText[1]);
		
		return userDao.createUser(userEntity);
	}
}
