package com.upgrad.proman.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upgrad.proman.service.dao.UserDAO;
import com.upgrad.proman.service.entity.UserEntity;

@Service
public class UserBusinessService {
	
	@Autowired
	private UserDAO userDao;
	
	public UserEntity getUser(final String Uuid) {
		return userDao.getUser(Uuid);
	}
}
