
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_JYTBILLING`
-- ----------------------------
DROP TABLE IF EXISTS `T_JYTBILLING`;
CREATE TABLE `T_JYT_BILLING` (
		  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`BILLING_DATE` varchar(20) NOT NULL COMMENT '对账日期',
    	`USER_ID` double NOT NULL COMMENT '商户号',
    	`COUNT` double NOT NULL COMMENT '交易总笔数',
    	`AMOUNT` double NOT NULL COMMENT '交易总金额',
    	`SUCC_COUNT` double NOT NULL COMMENT '成功总笔数',
    	`SUCC_AMOUNT` double NOT NULL COMMENT '成功总金额',
    	`FAIL_COUNT` double NOT NULL COMMENT '失败总笔数',
    	`FAIL_AMOUNT` double NOT NULL COMMENT '失败总金额',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
