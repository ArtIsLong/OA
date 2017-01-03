package com.gnd.oa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnd.oa.base.DaoSupportImpl;
import com.gnd.oa.domain.Role;
import com.gnd.oa.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl extends DaoSupportImpl<Role> implements RoleService {
}
