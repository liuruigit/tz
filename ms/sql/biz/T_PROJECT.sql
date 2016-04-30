

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_PROJECT`
-- ----------------------------
DROP TABLE IF EXISTS `T_PROJECT`;
CREATE TABLE `T_PROJECT` (
	`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`DAYS` int(11) COMMENT '产品信息ID',
	`NO` varchar(30) NOT NULL COMMENT '标的编号',
	`GUARANTEE` tinyint(4) NOT NULL COMMENT '保障级别',
	`PAY_INTEREST_WAY` tinyint(4) COMMENT '付息方式',
	`AMOUNT` double NOT NULL COMMENT '融资金额',
	`FINAL_AMOUNT` double COMMENT '实际出售金额',
	`SELLED_AMOUNT` double COMMENT '已融资金额',
	`MIN` double NOT NULL COMMENT '起投金额',
	`STEP` double NOT NULL COMMENT '累加金额',
	`LIMIT` double NOT NULL COMMENT '限投金额',
	`ANNUAL_RATE` double NOT NULL COMMENT '预期年化收益率',
	`SERVICE_RATE` double NOT NULL COMMENT '服务费率',
	`OPEN_DATE` datetime NOT NULL COMMENT '开放购买时间',
	`END_DATE` datetime NOT NULL COMMENT '结束购买时间',
	`SOLD_OUT_DATE` datetime COMMENT '售罄时间',
	`STATUS` tinyint(4) NOT NULL COMMENT '状态：0新建1开放购买2已满标3已结算4已还款',
	`CREATE_TIME` varchar(20) NOT NULL COMMENT '创建时间',
	`RECOMMEND` tinyint(4) NOT NULL COMMENT '是否推荐',
	`EXTRA1` varchar(20) COMMENT '预留信息1',
	`EXTRA2` varchar(20) COMMENT '预留信息2',
	`EXTRA3` varchar(20) COMMENT '预留信息3',
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;