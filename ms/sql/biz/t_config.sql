/*
Navicat MySQL Data Transfer

Source Server         : 本地连接
Source Server Version : 50171
Source Host           : localhost:3306
Source Database       : db_web

Target Server Type    : MYSQL
Target Server Version : 50171
File Encoding         : 65001

Date: 2016-01-24 01:35:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_config
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `KEY` varchar(20) NOT NULL COMMENT '配置KEY',
  `VALUE` varchar(100) NOT NULL COMMENT '配置value',
  `DESR` varchar(50) NOT NULL COMMENT '描述',
  `DELETE_STATE` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_config
-- ----------------------------
INSERT INTO `t_config` VALUES ('1', 'secretKey', 'JIsaNHs2ge6yj231ULU', '数据加密KEY', '1');
INSERT INTO `t_config` VALUES ('2', 'domain.debug', 'http://localhost:8080/', '测试服域名', '1');
INSERT INTO `t_config` VALUES ('3', 'static.domain.debug', 'http://localhost:8080/ms/', '测试资源服地址', '1');
