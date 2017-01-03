
-- 插入超级管理员用户，密码为"admin"
insert into t_user(name, loginName, password) values ('超级管理员', 'admin', '21232f297a57a5a743894a0e4a801fc3');

/*
 插入权限数据，
 一级菜单没有url，没有parentId
 二级菜单没有icon
*/

-- 系统管理
insert into t_privilege(id, name, url, icon, parentId)	values (1, '系统管理', null, 'FUNC20082.gif', null);
insert into t_privilege(id, name, url, icon, parentId)	values (2, '岗位管理', 'role_list', null, 1);
insert into t_privilege(id, name, url, icon, parentId)	values (3, '部门管理', 'department_list', null, 1);
insert into t_privilege(id, name, url, icon, parentId)	values (4, '用户管理', 'user_list', null, 1);

insert into t_privilege(id, name, url, icon, parentId)	values (5, '岗位列表', 'role_list', null, 2);
insert into t_privilege(id, name, url, icon, parentId)	values (6, '岗位删除', 'role_delete', null, 2);
insert into t_privilege(id, name, url, icon, parentId)	values (7, '岗位添加', 'role_add', null, 2);
insert into t_privilege(id, name, url, icon, parentId)	values (8, '岗位修改', 'role_edit', null, 2);

insert into t_privilege(id, name, url, icon, parentId)	values (9, '部门列表', 'department_list', null, 3);
insert into t_privilege(id, name, url, icon, parentId)	values (10,'部门删除', 'department_delete', null, 3);
insert into t_privilege(id, name, url, icon, parentId)	values (11,'部门添加', 'department_add', null, 3);
insert into t_privilege(id, name, url, icon, parentId)	values (12,'部门修改', 'department_edit', null, 3);

insert into t_privilege(id, name, url, icon, parentId)	values (13,'用户列表', 'user_list', null, 4);
insert into t_privilege(id, name, url, icon, parentId)	values (14,'用户删除', 'user_delete', null, 4);
insert into t_privilege(id, name, url, icon, parentId)	values (15,'用户添加', 'user_add', null, 4);
insert into t_privilege(id, name, url, icon, parentId)	values (16,'用户修改', 'user_edit', null, 4);
insert into t_privilege(id, name, url, icon, parentId)	values (17,'用户初始化密码', 'user_initPassword', null, 4);


-- 网上交流
insert into t_privilege(id, name, url, icon, parentId)	values (18,'网上交流', null, 'FUNC20064.gif', null);
insert into t_privilege(id, name, url, icon, parentId)	values (19,'论坛管理', 'forumManage_list', null, 18);
insert into t_privilege(id, name, url, icon, parentId)	values (20,'论坛', 'forum_list', null, 18);


-- 审批流转
insert into t_privilege(id, name, url, icon, parentId)	values (21,'审批流转', null, 'FUNC20057.gif', null);
insert into t_privilege(id, name, url, icon, parentId)	values (22,'审批流程管理', 'processDefinition_list', null, 21);
insert into t_privilege(id, name, url, icon, parentId)	values (23,'申请模板管理', 'template_list', null, 21);
insert into t_privilege(id, name, url, icon, parentId)	values (24,'起草申请', 'flow_templateList', null, 21);
insert into t_privilege(id, name, url, icon, parentId)	values (25,'等我审批', 'flow_myTaskList', null, 21);
insert into t_privilege(id, name, url, icon, parentId)	values (26,'我的申请查询', 'flow_myApplicationList', null, 21);

--个人设置
