
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_BANKRULE`
-- ----------------------------
DROP TABLE IF EXISTS `T_BANK_RULE`;
CREATE TABLE `T_BANK_RULE` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`BANK_NAME` varchar(20) NOT NULL COMMENT '银行名称',
    	`BANK_SHORT_NAME` varchar(20) NOT NULL COMMENT '银行简称',
    	`LIMIT` double NOT NULL COMMENT '单笔限额',
    	`DAY_LIMIT` double NOT NULL COMMENT '单日限额',
    	`TIME_LIMIT_BEGIN` datetime NOT NULL COMMENT '起始时间',
    	`TIME_LIMIT_END` datetime NOT NULL COMMENT '结束时间',
    	`CHANNEL_NAME` varchar(20) NOT NULL COMMENT '渠道名称',
    	`DELETE_STATE` tinyint(4) NOT NULL COMMENT '逻辑删除标志位：1正常0删除',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
