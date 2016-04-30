
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_MSACCOUNT`
-- ----------------------------
DROP TABLE IF EXISTS `T_MSACCOUNT`;
CREATE TABLE `T_MSACCOUNT` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`ID` tinyint(4) NOT NULL COMMENT 'id',
    	`ACC_NAME` varchar(20) NOT NULL COMMENT '用户名',
    	`PWD` varchar(20) NOT NULL COMMENT '密码',
    	`TRADE_PWD` varchar(20) NOT NULL COMMENT '交易密码',
    	`LINE_PWD` varchar(20) NOT NULL COMMENT '手势密码',
    	`TOKEN` varchar(20) NOT NULL COMMENT 'token',
    	`UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
    	`CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    	`STATUS` tinyint(4) NOT NULL COMMENT '账号状态',
    	`DELETE_STATUS` tinyint(4) NOT NULL COMMENT '逻辑删除标志位：1正常0删除',
    	`RECOMMEND_ID` tinyint(4) NOT NULL COMMENT '推荐人',
    	`MOBILE` varchar(20) NOT NULL COMMENT '电话',
    	`EMAIL` varchar(20) NOT NULL COMMENT '邮箱',
    	`CERT_NO` varchar(20) NOT NULL COMMENT '身份证号',
    	`REAL_NAME` varchar(20) NOT NULL COMMENT '真实姓名',
    	`PREPARE_STEP` tinyint(4) NOT NULL COMMENT '投资准备',
    	`LV` tinyint(4) NOT NULL COMMENT '等级',
    	`POINT` tinyint(4) NOT NULL COMMENT '积分',
    	`MONEY` tinyint(4) NOT NULL COMMENT '余额',
    	`FROZEN_MONEY` tinyint(4) NOT NULL COMMENT '冻结金额',
    	`INVEST_MONEY` tinyint(4) NOT NULL COMMENT '投资金额',
    	`DIGEST` varchar(20) NOT NULL COMMENT '摘要',
    	`VERSION` tinyint(4) NOT NULL COMMENT '版本号',
    	`ACC_INCOME` tinyint(4) NOT NULL COMMENT '累计收益',
    	`VIP` tinyint(4) NOT NULL COMMENT 'vip等级',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
