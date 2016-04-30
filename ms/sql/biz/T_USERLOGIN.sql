
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_USERLOGIN`
-- ----------------------------
DROP TABLE IF EXISTS `T_USERLOGIN`;
CREATE TABLE `T_USERLOGIN` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`TRAN_TYPE` varchar(20) NOT NULL COMMENT '交易类型',
    	`TRAN_DATE` datetime NOT NULL COMMENT '商户交易时间',
    	`TRAN_DATED` datetime NOT NULL COMMENT '交易完成时间',
    	`ORDER_ID` varchar(20) NOT NULL COMMENT '商户订单号',
    	`NUM` varchar(20) NOT NULL COMMENT '批次号',
    	`REP_ORDER_ID` varchar(20) NOT NULL COMMENT '代收付平台订单号',
    	`TRAN_AMOUT` double NOT NULL COMMENT '交易金额',
    	`CURRENCY` varchar(20) NOT NULL COMMENT '币种',
    	`BANK_NO` varchar(20) NOT NULL COMMENT '客户交易银行账号',
    	`BANK_NAME` varchar(20) NOT NULL COMMENT '客户交易银行账户名称',
    	`USER_FIC_NO` varchar(20) NOT NULL COMMENT '商户交易虚拟账号',
    	`RESULT_NO` varchar(20) NOT NULL COMMENT '交易结果码',
    	`RESULT_DESC` varchar(20) NOT NULL COMMENT '交易结果描述',
    	`POUND` double NOT NULL COMMENT '手续费',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
