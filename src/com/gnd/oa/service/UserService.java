package com.gnd.oa.service;

import com.gnd.oa.base.DaoSupport;
import com.gnd.oa.domain.User;

public interface UserService extends DaoSupport<User> {

	User findByLoginNameAndPassword(String loginName, String password);

}
