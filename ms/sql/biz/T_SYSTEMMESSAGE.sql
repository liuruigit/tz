
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_SYSTEMMESSAGE`
-- ----------------------------
DROP TABLE IF EXISTS `T_SYSTEM_MESSAGE`;
CREATE TABLE `T_SYSTEM_MESSAGE` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`BODY` varchar(255) NOT NULL COMMENT '消息内容',
    	`CREATE_TIME` datetime NOT NULL COMMENT '创建日期',
    	`UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新日期',
    	`DELETE_STATE` tinyint(4) NOT NULL COMMENT '逻辑删除标志位：1正常0删除',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
