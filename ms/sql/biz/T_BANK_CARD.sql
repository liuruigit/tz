
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_INVESTORDER`
-- ----------------------------
DROP TABLE IF EXISTS `T_INVEST_ORDER`;
CREATE TABLE `T_INVEST_ORDER` (
		  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`bankName` int(11) NOT NULL COMMENT '用户ID',
    	`name` varchar(40) NOT NULL COMMENT '用户名称',
    	`userId` int(11) NOT NULL COMMENT '标的ID',
    	`bankCardNo` long NOT NULL COMMENT '投资金额',
    	`pro` varchar(20) NOT NULL COMMENT '订单号',
    	`CITY` varchar(20) NOT NULL COMMENT '合同号',
    	`STATUS` int NOT NULL COMMENT '状态:0发起认购1认购成功',
    	`DELETE_STATE` varchar(20) NOT NULL COMMENT '删除状态',
    	`SUCC_TIME` varchar(20) NOT NULL COMMENT '认购成功时间',
    	`PAY_TYPE` varchar(20) NOT NULL COMMENT '支付方式',
    	`CREATE_TIME` varchar(20) NOT NULL COMMENT '创建时间',
    	`UPDATE_TIME` varchar(20) NOT NULL COMMENT '更新时间',
    	`APP_VERSION` varchar(20) NOT NULL COMMENT '客户端版本号',
  		PRIMARY KEY (`ID`)

				/** 支行*/
				String branch;
/** code*/
String bankCode;
/** 是否默认卡*/
int isDefault;
/** 协议号*/
String prcptcd;
/** 签约协议号*/
String noAgree;
/** 绑定的手机号*/
String mobile;
@ForbidUpdate
private Integer deleteState = 1;
private Date createTime = new Date();

) ENGINE=InnoDB DEFAULT CHARSET=utf8;
