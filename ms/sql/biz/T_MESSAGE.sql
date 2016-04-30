/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50617
 Source Host           : localhost
 Source Database       : db_web

 Target Server Type    : MySQL
 Target Server Version : 50617
 File Encoding         : utf-8

 Date: 01/31/2016 10:42:43 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `T_MESSAGE`
-- ----------------------------
DROP TABLE IF EXISTS `T_MESSAGE`;
CREATE TABLE `T_MESSAGE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `TITLE` varchar(20) NOT NULL COMMENT '标题',
  `CONTENT` varchar(20) NOT NULL COMMENT '消息内容',
  `CREATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `DELETE_STATE` tinyint(4) DEFAULT '1' COMMENT '逻辑删除标志位：1正常0删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
