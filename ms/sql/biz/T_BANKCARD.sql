
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_BANKCARD`
-- ----------------------------
DROP TABLE IF EXISTS `T_BANKCARD`;
CREATE TABLE `T_BANKCARD` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`BANK_NAME` varchar(20) NOT NULL COMMENT '银行名称',
    	`NAME` varchar(20) NOT NULL COMMENT '名称',
    	`USER_ID` tinyint(4) NOT NULL COMMENT '用户ID',
    	`BANK_CARD_NO` varchar(20) NOT NULL COMMENT '银行卡号',
    	`PRO` varchar(20) NOT NULL COMMENT '省份',
    	`CITY` varchar(20) NOT NULL COMMENT '城市',
    	`BRANCH` varchar(20) NOT NULL COMMENT '支行',
    	`BANK_CODE` varchar(20) NOT NULL COMMENT 'code',
    	`IS_DEFAULT` tinyint(4) NOT NULL COMMENT '是否默认卡',
    	`PRCPTCD` varchar(20) NOT NULL COMMENT '协议号',
    	`NO_AGREE` varchar(20) NOT NULL COMMENT '签约协议号',
    	`MOBILE` varchar(20) NOT NULL COMMENT '绑定的手机号',
    	`CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    	`DELETE_STATE` tinyint(4) NOT NULL COMMENT '逻辑删除标志位：1正常0删除',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
