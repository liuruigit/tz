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

 Date: 01/31/2016 10:41:41 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_notices`
-- ----------------------------
DROP TABLE IF EXISTS `t_notices`;
CREATE TABLE `t_notices` (
  `NOTICES_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(200) DEFAULT NULL COMMENT '标题',
  `CONTENT` text COMMENT '富文本内容',
  `PICTURE_URL` varchar(300) DEFAULT NULL COMMENT '轮播图片URL',
  `CREATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `DELETE_STATE` tinyint(4) DEFAULT '1' COMMENT '逻辑删除标志位：1正常0删除',
  `CONTENT_TYPE` varchar(20) DEFAULT '' COMMENT '公告内容类型：NEWS 新闻资讯 ， NOTICES 网站公告',
  `INDEX` int(4) DEFAULT NULL COMMENT '第几条',
  PRIMARY KEY (`NOTICES_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
