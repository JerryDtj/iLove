/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MariaDB
 Source Server Version : 100206
 Source Host           : localhost
 Source Database       : any

 Target Server Type    : MariaDB
 Target Server Version : 100206
 File Encoding         : utf-8

 Date: 06/07/2017 17:05:08 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`  int(11) NOT NULL AUTO_INCREMENT ,
  `username`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `password`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  `enabled`  tinyint(1) NULL DEFAULT NULL ,
  `accountNonLocked`  tinyint(1) NULL DEFAULT NULL ,
  `accountNonExpired`  tinyint(1) NULL DEFAULT NULL ,
  `roles`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'anoy', 'pwd', 'anoy', 'ROLE_USER'), ('2', 'admin', 'pwd', 'admin', 'ROLE_USER,ROLE_ADMIN');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
