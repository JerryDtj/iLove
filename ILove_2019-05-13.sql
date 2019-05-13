# ************************************************************
# Sequel Pro SQL dump
# Version 5438
#
# https://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: tianzijiaozi.top (MySQL 5.7.24)
# Database: ILove
# Generation Time: 2019-05-13 01:00:33 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `receiver_id` varchar(11) DEFAULT NULL COMMENT '接收用户id',
  `create_at` datetime DEFAULT NULL COMMENT '接收时间',
  `status` int(11) DEFAULT '0' COMMENT '阅读等状态:0 未读 1 已读',
  `message_text_id` int(11) DEFAULT NULL COMMENT '消息内容Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;

INSERT INTO `message` (`id`, `receiver_id`, `create_at`, `status`, `message_text_id`)
VALUES
	(8,'111','2019-04-04 08:30:52',0,333),
	(9,'111','2019-04-04 08:31:36',0,333),
	(10,'111','2019-04-04 08:34:34',0,333),
	(11,'8','2019-04-05 03:45:02',0,2),
	(12,'8','2019-04-06 13:12:38',0,5),
	(13,'11','2019-04-09 03:19:05',0,333),
	(14,'11','2019-05-11 22:44:10',0,333),
	(15,'11','2019-05-11 22:45:36',0,333),
	(16,'11','2019-05-12 19:40:12',0,333);

/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table message_text
# ------------------------------------------------------------

DROP TABLE IF EXISTS `message_text`;

CREATE TABLE `message_text` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `content` varchar(500) DEFAULT NULL COMMENT '内容',
  `create_at` datetime DEFAULT NULL COMMENT '发送时间',
  `message_type` int(11) DEFAULT NULL COMMENT '消息类型',
  `send_type` int(11) DEFAULT NULL COMMENT '接收者类型单用户/用户群组',
  `creator_name` varchar(11) DEFAULT NULL COMMENT '发送者姓名 系统管理员是 sysadmin',
  `link` varchar(255) DEFAULT NULL COMMENT '详情链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `message_text` WRITE;
/*!40000 ALTER TABLE `message_text` DISABLE KEYS */;

INSERT INTO `message_text` (`id`, `title`, `content`, `create_at`, `message_type`, `send_type`, `creator_name`, `link`)
VALUES
	(3,'系统提示','请尽快补充完成用户信息','2019-04-05 03:55:45',0,0,'测试用户','http://tianzijiaozi.top/addUserDetail.html'),
	(4,'系统提示','请尽快补充完成用户信息','2019-04-05 04:08:17',0,0,'测试用户','http://tianzijiaozi.top/addUserDetail.html'),
	(5,'系统提示','请尽快补充完成用户信息','2019-04-06 13:12:38',0,0,'sysAdmin','http://tianzijiaozi.top/addUserDetail.html'),
	(6,'系统提示','请尽快补充完成用户信息','2019-04-09 03:19:05',0,0,'测试用户','http://tianzijiaozi.top/addUserDetail.html'),
	(7,'系统提示','请尽快补充完成用户信息','2019-05-11 22:44:11',0,0,'测试用户','http://tianzijiaozi.top/addUserDetail.html'),
	(8,'系统提示','请尽快补充完成用户信息','2019-05-11 22:45:36',0,0,'测试用户','http://tianzijiaozi.top/addUserDetail.html'),
	(9,'系统提示','请尽快补充完成用户信息','2019-05-12 19:40:12',0,0,'测试用户','http://tianzijiaozi.top/addUserDetail.html');

/*!40000 ALTER TABLE `message_text` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `permission_id` varchar(40) NOT NULL COMMENT '权限编号',
  `permission_url` varchar(255) NOT NULL COMMENT '授权url',
  `permission_comment` varchar(255) DEFAULT NULL COMMENT '描述',
  `permission_status` varchar(255) NOT NULL DEFAULT '0' COMMENT '权限状态,0正常，-1删除',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='权限表';



# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `name` varchar(25) NOT NULL DEFAULT '' COMMENT '角色名称',
  `status` varchar(1) NOT NULL DEFAULT '0' COMMENT '角色状态，0正常，-1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色表';

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`id`, `name`, `status`)
VALUES
	(1,'ADMIN','0'),
	(2,'REPORTADMIN','0');

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role_permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `role_permission_id` varchar(40) NOT NULL COMMENT '角色权限编号',
  `role_id` varchar(40) NOT NULL COMMENT '角色编号',
  `permission_id` varchar(40) NOT NULL COMMENT '权限编号',
  `role_permission_status` varchar(1) NOT NULL DEFAULT '0' COMMENT '角色权限状态',
  PRIMARY KEY (`role_permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色权限表';



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用 0:未启用 1:以启用',
  `account_non_locked` tinyint(1) DEFAULT '0' COMMENT '是否锁定 0:未锁定 1:已锁定',
  `account_non_expired` tinyint(1) DEFAULT '0' COMMENT '是否过期 0:未过期 1:以过期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `username`, `password`, `enabled`, `account_non_locked`, `account_non_expired`)
VALUES
	(3,'1','$2a$10$Q3iRHP/wCBM2uvRG7dh.ku1F2oFdgasaEPXe4Cfohy.BrQaVIDTji',1,0,0),
	(4,'11','$2a$10$8EQ0kAXof2v3qldmgPYzhO1QpkwwMZhEAOtom64HAvG..88SfNs72',1,0,0),
	(8,'2','$2a$10$vhq.H4d.t.vgBd/D9pcUFOwTYLl.gzQ5arkpgHdMHt5VcvGr0cOOK',1,1,1),
	(19,'3','$2a$10$QKcHXiukZ0j9As3RKvQY1eOK5Npihb4uMuCiKHXOShknrau6bubTC',0,0,0),
	(20,'D70116D14219DB5260B461CA7AE6BE02','$2a$10$8MBc8p7Bu0NHB/HfK0wHfeNvM14iXavvH8uq34f.gkb81hkuCu7Tu',0,0,0);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_detail`;

CREATE TABLE `user_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户表id',
  `email` varchar(255) DEFAULT NULL COMMENT 'email 地址',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `nick_name` varchar(45) DEFAULT NULL COMMENT '昵称',
  `gender` varchar(45) DEFAULT NULL COMMENT '性别',
  `province` varchar(45) DEFAULT NULL COMMENT '省份',
  `year` varchar(45) DEFAULT NULL COMMENT '出生年份',
  `avatar` varchar(45) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详情表';



# Dump of table user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色编号',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `user_role_status` int(1) NOT NULL DEFAULT '0' COMMENT '用户角色状态，0正常，-1删除',
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;

INSERT INTO `user_role` (`user_role_id`, `user_id`, `role_id`, `user_role_status`)
VALUES
	(1,8,1,0),
	(2,8,2,0),
	(3,19,2,0),
	(4,20,2,0);

/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
