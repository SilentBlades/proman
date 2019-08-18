package com.upgrad.proman.service.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		return entityManager.createNamedQuery("userByUuid", UserEntity.class)
							.setParameter("uuid", Uuid)
							.getSingleResult();
	}
}
