
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_HLBCHANGERECORD`
-- ----------------------------
DROP TABLE IF EXISTS `T_HLBCHANGERECORD`;
CREATE TABLE `T_HLBCHANGERECORD` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`ACC_ID` varchar(20) NOT NULL COMMENT '用户ID',
    	`AMOUNT` double NOT NULL COMMENT '葫芦币数量',
    	`CHANGE_AMOUNT` double NOT NULL COMMENT '葫芦币变更数量',
    	`DESC` varchar(20) NOT NULL COMMENT '描述',
    	`CREATE_TIME` datetime NOT NULL COMMENT '交易时间',
    	`DELETE_STATE` tinyint(4) NOT NULL COMMENT '逻辑删除',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
