package com.upgrad.proman.service.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Repository;

import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.entity.UserEntity;

@Repository
public class UserDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public UserEntity createUser(UserEntity userEntity) {
		entityManager.persist(userEntity);
		return userEntity;
	}
	
	public UserEntity getUser(final String Uuid) {
		try {
			return entityManager.createNamedQuery("userByUuid", UserEntity.class)
					.setParameter("uuid", Uuid)
					.getSingleResult();	
		}catch (NoResultException nre) {
			return null;
		}
	}
	
	public UserEntity getUserByEmail(final String email) {
		try {
			return entityManager.createNamedQuery("userByEmail", UserEntity.class)
								.setParameter("email", email)
								.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	@Transactional
	public UserAuthTokenEntity createAuthToken(final UserAuthTokenEntity userAuthTokenEntity) {
		entityManager.persist(userAuthTokenEntity);
		return userAuthTokenEntity;
	}
	
	@Transactional
	public void updateUser(final UserEntity userEntity) {
		entityManager.merge(userEntity);
	}
	
	public UserAuthTokenEntity getUserAuthToken(final String authorizationToken) {
		try {
			return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class)
					 .setParameter("accessToken", authorizationToken).getSingleResult();	
		}catch (NoResultException nre) {
			return null;
		}
		
	}
}
