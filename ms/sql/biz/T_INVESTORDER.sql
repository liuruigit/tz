
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_INVESTORDER`
-- ----------------------------
DROP TABLE IF EXISTS `T_INVEST_ORDER`;
CREATE TABLE `T_INVEST_ORDER` (
		  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`ACC_ID` int(11) NOT NULL COMMENT '用户ID',
    	`ACC_NAME` varchar(40) NOT NULL COMMENT '用户名称',
    	`PRO_ID` int(11) NOT NULL COMMENT '标的ID',
    	`AMOUNT` long NOT NULL COMMENT '投资金额',
    	`NO` varchar(20) NOT NULL COMMENT '订单号',
    	`CONTRACT` varchar(20) NOT NULL COMMENT '合同号',
    	`STATUS` int NOT NULL COMMENT '状态:0发起认购1认购成功',
    	`DELETE_STATE` varchar(20) NOT NULL COMMENT '删除状态',
    	`SUCC_TIME` varchar(20) NOT NULL COMMENT '认购成功时间',
    	`PAY_TYPE` varchar(20) NOT NULL COMMENT '支付方式',
    	`CREATE_TIME` varchar(20) NOT NULL COMMENT '创建时间',
    	`UPDATE_TIME` varchar(20) NOT NULL COMMENT '更新时间',
    	`APP_VERSION` varchar(20) NOT NULL COMMENT '客户端版本号',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
