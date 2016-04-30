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

 Date: 02/04/2016 15:28:43 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_trans_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_trans_order`;
CREATE TABLE `t_trans_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建日期',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新日期',
  `INVEST_ORDER_ID` int(11) DEFAULT NULL COMMENT '投资表订单号',
  `AMOUNT` bigint(20) DEFAULT NULL COMMENT '投资金额',
  `ACC_ID` int(11) DEFAULT NULL COMMENT '用户ID',
  `ACC_NAME` varchar(40) DEFAULT NULL COMMENT '用户名称',
  `PRO_ID` int(11) DEFAULT NULL COMMENT '标的ID',
  `DELETE_STATE` tinyint(4) DEFAULT NULL COMMENT '删除状态',
  `DISCOUNT` tinyint(4) DEFAULT NULL COMMENT '折扣，单位%',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '状态:0发起认购1认购成功',
  `CONTRACT_NO` varchar(20) DEFAULT NULL COMMENT '合同号',
  `TRANS_AMOUNT` bigint(20) DEFAULT NULL COMMENT '转让金额:原认购金额折扣后的金额',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
