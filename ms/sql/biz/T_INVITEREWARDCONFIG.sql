SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_INVITEREWARDCONFIG`
-- ----------------------------
DROP TABLE IF EXISTS `T_INVITE_REWARD_CONFIG`;
CREATE TABLE `T_INVITE_REWARD_CONFIG` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`DAYS` varchar(20) NOT NULL COMMENT '期限',
    	`RANGE_START` varchar(20) NOT NULL COMMENT '投资金额（起）',
    	`RANGE_END` varchar(20) NOT NULL COMMENT '投资金额（止）',
    	`PERC` varchar(20) NOT NULL COMMENT '配置value',
    	`IS_OPEN` varchar(20) NOT NULL COMMENT '是否开启',
    	`DELETE_STATE` varchar(20) NOT NULL COMMENT '逻辑删除位：0：删除； 1：正常',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;