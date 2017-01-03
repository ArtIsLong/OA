package com.gnd.oa.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnd.oa.base.DaoSupportImpl;
import com.gnd.oa.domain.User;
import com.gnd.oa.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends DaoSupportImpl<User> implements
		UserService {

	public User findByLoginNameAndPassword(String loginName, String password) {
		String md5disag = DigestUtils.md5Hex(password);
		return (User) getSession()
				.createQuery("from User u where u.loginName=? and u.password=?")//
				.setParameter(0, loginName)//
				.setParameter(1, md5disag)//
				.uniqueResult();
	}

}
