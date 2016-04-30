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

 Date: 02/21/2016 23:52:54 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_month_billing`
-- ----------------------------
DROP TABLE IF EXISTS `t_month_billing`;
CREATE TABLE `t_month_billing` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ACC_ID` int(11) NOT NULL COMMENT '用户ID',
  `MONEY` bigint(20) DEFAULT '0' COMMENT '余额',
  `PROPERTY` bigint(20) DEFAULT '0' COMMENT '总资产',
  `ACC_INCOME` bigint(20) DEFAULT '0' COMMENT '收益',
  `CHARGE` bigint(20) DEFAULT '0' COMMENT '充值',
  `CASH` bigint(20) DEFAULT '0' COMMENT '提现',
  `CREATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `UPDATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '使用日期',
  `DELETE_STATE` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
