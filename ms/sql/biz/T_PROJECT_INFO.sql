-- ----------------------------
-- Table structure for `T_PROJECTINFO`
-- ----------------------------
DROP TABLE IF EXISTS `T_PROJECT_INFO`;
CREATE TABLE `T_PROJECT_INFO` (
	`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`NO` varchar(20) NOT NULL COMMENT '标的编号',
	`NAME` varchar(20) NOT NULL COMMENT '资产名称',
	`PROPERTY_TYPE` tinyint(4) NOT NULL COMMENT '资产类型0住宅1商铺2写字楼',
	`LOCATION` varchar(20) NOT NULL COMMENT '地域',
	`AREA` double NOT NULL COMMENT '产权面积',
	`SELL_PRICE` varchar(20) NOT NULL COMMENT '出售价格',
	`MARKET_PRICE` varchar(20) NOT NULL COMMENT '市场价',
	`PROPERTY_CERT` tinyint(4) NOT NULL COMMENT '产权证：0已办理1可办理而未办理2已办理网签备案3可办理而未到办理时间4短期内不可办理',
	`LAND_CERT` tinyint(4) NOT NULL COMMENT '土地证:0已办理1可办理而未办理2短期内不可办理',
	`PROPERTY_OWNER` tinyint(4) NOT NULL COMMENT '资产权属：0单独自然人所有1法人单独所有2两个以上主体共有',
	`PROPERTY_RIGHT` varchar(50) NOT NULL COMMENT '物权情况',
	`EXTRA_INFO` varchar(500) NOT NULL COMMENT '附加信息',
	`CONTRACT` varchar(20) NOT NULL COMMENT '买卖合同图片路径',
	`ADDED_CONTRACT` varchar(20) NOT NULL COMMENT '补充协议图片路径',
	`PROPERTY_LIST` varchar(20) NOT NULL COMMENT '房源清单',
	`RECORD` varchar(20) NOT NULL COMMENT '备案摘要',
	`CREATE_TIME` varchar(20) NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


