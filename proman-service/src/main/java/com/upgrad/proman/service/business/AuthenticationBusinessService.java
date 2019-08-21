package com.upgrad.proman.service.business;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.upgrad.proman.service.dao.UserDAO;
import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.AuthenticationFailedException;

@Service
public class AuthenticationBusinessService {

	@Autowired
	private UserDAO userDao;

	@Autowired
	private PasswordCryptographyProvider passwordCryptographyProvider;

	@Transactional(propagation = Propagation.REQUIRED)
	public UserAuthTokenEntity authenticate(final String userName, final String password) throws AuthenticationFailedException {
		UserEntity userEntity = userDao.getUserByEmail(userName);

		if (userEntity == null) {
			throw new AuthenticationFailedException("ATH-001", "Email address incorrect!");
		}
		
		final String encryptedPassword = passwordCryptographyProvider.encrypt(password, userEntity.getSalt());

		if (encryptedPassword.equals(userEntity.getPassword())) {
			UserAuthTokenEntity userAuthTokenEntity = new UserAuthTokenEntity();
			JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(userEntity.getPassword());
			
			userAuthTokenEntity.setUser(userEntity);
			
			final ZonedDateTime issuedAt = ZonedDateTime.now();
			final ZonedDateTime expiresAt = issuedAt.plusHours(8); 
			String accessToken = jwtTokenProvider.generateToken(userEntity.getUuid(), issuedAt, expiresAt);
					
			userAuthTokenEntity.setAccessToken(accessToken);
			userAuthTokenEntity.setLoginAt(issuedAt);
			userAuthTokenEntity.setCreatedAt(issuedAt);
			userAuthTokenEntity.setCreatedBy("api-backend");
			userAuthTokenEntity.setExpiresAt(expiresAt);
			
			userDao.createAuthToken(userAuthTokenEntity);
			
			userEntity.setLastLoginAt(issuedAt);
			userDao.updateUser(userEntity);
			return userAuthTokenEntity;
		} else {
			throw new AuthenticationFailedException("ATH-002", "Password is incorrect!");
		}
		
	}
}
