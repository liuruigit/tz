
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_UNBINDINGAPPLY`
-- ----------------------------
DROP TABLE IF EXISTS `T_UNBINDINGAPPLY`;
CREATE TABLE `T_UNBINDINGAPPLY` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`ACC_ID` tinyint(4) NOT NULL COMMENT '用户ID',
    	`ATTACH` varchar(20) NOT NULL COMMENT '附件',
    	`STATUS` datetime NOT NULL COMMENT '状态',
    	`CREATE_TIME` varchar(20) NOT NULL COMMENT '创建日期',
    	`UPDATE_TIME` tinyint(4) NOT NULL COMMENT '更新日期',
    	`DELETE_STATE` tinyint(4) NOT NULL COMMENT '逻辑删除标志位',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
