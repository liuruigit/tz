
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_SECURITYQUESTION`
-- ----------------------------
DROP TABLE IF EXISTS `T_SECURITYQUESTION`;
CREATE TABLE `T_SECURITYQUESTION` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`ACC_ID` tinyint(4) NOT NULL COMMENT '用户',
    	`QUESTION` varchar(20) NOT NULL COMMENT '问题',
    	`ANSWER` varchar(20) NOT NULL COMMENT '答案',
    	`UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
    	`CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    	`DELETE_STATE` tinyint(4) NOT NULL COMMENT '逻辑删除标志位',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
