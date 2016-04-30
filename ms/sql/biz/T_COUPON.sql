
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_COUPON`
-- ----------------------------
DROP TABLE IF EXISTS `T_COUPON`;
CREATE TABLE `T_COUPON` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`DESC` varchar(20) NOT NULL COMMENT 'id',
    	`NAME` varchar(20) NOT NULL COMMENT '标题',
    	`MIN` varchar(20) NOT NULL COMMENT '金额下限',
    	`BEGIN_DATE` datetime NOT NULL COMMENT '开始日期',
    	`END_DATE` datetime NOT NULL COMMENT '截止日期',
    	`TAG` tinyint(4) NOT NULL COMMENT '适用范围',
    	`DELETE_STATE` tinyint(4) NOT NULL COMMENT '逻辑删除标志位：1正常0删除',
    	`CREATE_TIME` datetime NOT NULL COMMENT '逻辑删除标志位：1正常0删除',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
