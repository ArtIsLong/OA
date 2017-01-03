/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.0.22-community-nt : Database - oa
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`oa` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `oa`;

/*Table structure for table `t_department` */

DROP TABLE IF EXISTS `t_department`;

CREATE TABLE `t_department` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `description` varchar(255) default NULL,
  `parentId` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKEFEB87FD447369C3` (`parentId`),
  CONSTRAINT `FKEFEB87FD447369C3` FOREIGN KEY (`parentId`) REFERENCES `t_department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_department` */

insert  into `t_department`(`id`,`name`,`description`,`parentId`) values (1,'财务部','算账',NULL),(9,'项目部','',NULL),(10,'开发组','写代码',9),(11,'测试组','搞测试',9),(12,'会计','做账',1),(13,'核算','',1);

/*Table structure for table `t_forum` */

DROP TABLE IF EXISTS `t_forum`;

CREATE TABLE `t_forum` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `description` varchar(255) default NULL,
  `position` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_forum` */

/*Table structure for table `t_privilege` */

DROP TABLE IF EXISTS `t_privilege`;

CREATE TABLE `t_privilege` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `url` varchar(255) default NULL,
  `parentId` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKC0DCF0A6E657E14A` (`parentId`),
  CONSTRAINT `FKC0DCF0A6E657E14A` FOREIGN KEY (`parentId`) REFERENCES `t_privilege` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_privilege` */

insert  into `t_privilege`(`id`,`name`,`url`,`parentId`) values (1,'系统管理',NULL,NULL),(2,'岗位管理','/role_list',1),(3,'部门管理','/department_list',1),(4,'用户管理','/user_list',1),(5,'岗位列表','/role_list',2),(6,'岗位删除','/role_delete',2),(7,'岗位添加','/role_add',2),(8,'岗位修改','/role_edit',2),(9,'部门列表','/department_list',3),(10,'部门删除','/department_delete',3),(11,'部门添加','/department_add',3),(12,'部门修改','/department_edit',3),(13,'用户列表','/user_list',4),(14,'用户删除','/user_delete',4),(15,'用户添加','/user_add',4),(16,'用户修改','/user_edit',4),(17,'初始化密码','/user_initPassword',4),(18,'网上交流',NULL,NULL),(19,'论坛管理','/forumManage_list',18),(20,'论坛','/forum_list',18),(21,'审批流转',NULL,NULL),(22,'审批流程管理','/processDefinition_list',21),(23,'申请模板管理','/template_list',21),(24,'起草申请','/flow_templateList',21),(25,'待我审批','/flow_myTaskList',21),(26,'我的申请查询','/flow_myApplicationList',21);

/*Table structure for table `t_privilege_role` */

DROP TABLE IF EXISTS `t_privilege_role`;

CREATE TABLE `t_privilege_role` (
  `roleId` bigint(20) NOT NULL,
  `privilegeId` bigint(20) NOT NULL,
  PRIMARY KEY  (`privilegeId`,`roleId`),
  KEY `FK146DC06FAF646991` (`privilegeId`),
  KEY `FK146DC06F8FAF2CB3` (`roleId`),
  CONSTRAINT `FK146DC06F8FAF2CB3` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FK146DC06FAF646991` FOREIGN KEY (`privilegeId`) REFERENCES `t_privilege` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_privilege_role` */

insert  into `t_privilege_role`(`roleId`,`privilegeId`) values (3,1),(4,1),(3,2),(4,2),(3,3),(4,3),(3,4),(4,4),(3,5),(4,5),(3,6),(3,7),(4,7),(3,8),(3,9),(3,10),(3,11),(4,11),(3,12),(3,13),(4,13),(3,14),(3,15),(3,16),(3,17),(3,18),(4,18),(3,19),(4,19),(3,20),(4,20),(3,21),(3,22),(3,23),(3,24),(3,25),(3,26);

/*Table structure for table `t_privileges_roles` */

DROP TABLE IF EXISTS `t_privileges_roles`;

CREATE TABLE `t_privileges_roles` (
  `roleId` bigint(20) NOT NULL,
  `privilegeId` bigint(20) NOT NULL,
  PRIMARY KEY  (`privilegeId`,`roleId`),
  KEY `FKB1BBB88BAF646991` (`privilegeId`),
  KEY `FKB1BBB88B8FAF2CB3` (`roleId`),
  CONSTRAINT `FKB1BBB88B8FAF2CB3` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FKB1BBB88BAF646991` FOREIGN KEY (`privilegeId`) REFERENCES `t_privilege` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_privileges_roles` */

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `description` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`name`,`description`) values (3,'总经理','纵观全局'),(4,'程序员','21341235');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `loginName` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `gender` varchar(255) default NULL,
  `phoneNumber` varchar(255) default NULL,
  `email` varchar(255) default NULL,
  `description` varchar(255) default NULL,
  `departmentId` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKCB63CCB6C6729A6B` (`departmentId`),
  CONSTRAINT `FKCB63CCB6C6729A6B` FOREIGN KEY (`departmentId`) REFERENCES `t_department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`loginName`,`password`,`name`,`gender`,`phoneNumber`,`email`,`description`,`departmentId`) values (12,'admin','81dc9bdb52d04dc20036dbd8313ed055','超级管理员',NULL,'','','',NULL),(13,'zs','81dc9bdb52d04dc20036dbd8313ed055','张三','男','qewrewr','wqerwqer','第三方大师',9);

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY  (`roleId`,`userId`),
  KEY `FK331DEE5F8FAF2CB3` (`roleId`),
  KEY `FK331DEE5F9504821D` (`userId`),
  CONSTRAINT `FK331DEE5F8FAF2CB3` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FK331DEE5F9504821D` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`userId`,`roleId`) values (12,3),(13,4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
