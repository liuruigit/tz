
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_ACCOUNT`
-- ----------------------------
DROP TABLE IF EXISTS `T_ACCOUNT`;
CREATE TABLE `T_ACCOUNT` (
		  `USER_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`ACC_NAME` varchar(40) NOT NULL COMMENT '账号',
    	`PWD` varchar(15) NOT NULL COMMENT '密码',
    	`TRADE_PWD` varchar(15) NOT NULL COMMENT '交易密码',
    	`LINE_PWD` varchar(15) COMMENT '手势密码',
    	`TOKEN` varchar(50) COMMENT 'token',
    	`CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    	`STATUS` tinyint(4) DEFAULT '0' COMMENT '状态：0正常1临时封号2永久封号',
    	`DELETE_STATE` tinyint(4) DEFAULT '1' COMMENT '逻辑删除标志位：1正常0删除',
    	`RECOMMAND_ID` int(11) COMMENT '推荐人ID',
    	`MOBILE` varchar(15) NOT NULL COMMENT '手机号',
    	`EMAIL` varchar(50) COMMENT '邮箱',
    	`CERT_NO` varchar(20) COMMENT '身份证号',
    	`REAL_NAME` varchar(20) COMMENT '真实姓名',
    	`IS_REAL_NAME` tinyint(4) COMMENT '是否实名',
    	`LV` INTEGER COMMENT '等级',
    	`POINT` INTEGER COMMENT '积分',
    	`MONEY` double COMMENT '余额',
    	`FREEZE_MONEY` double COMMENT '冻结金额',
  		PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `T_ACCOUNT_LOGIN`;
CREATE TABLE `T_ACCOUNT_LOGIN` (
      `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
		  `USER_ID` int(11) NOT NULL COMMENT 'USER_ID',
      `IMEI` varchar(50) COMMENT '登录设备',
      `LOGIN_TIME` datetime COMMENT '登录时间',
      `TOKEN` varchar(50) COMMENT 'token',
      `TYPE` tinyint(4) COMMENT '0密码1token2手势验证码',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;