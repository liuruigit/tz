
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_GOODSINFO`
-- ----------------------------
DROP TABLE IF EXISTS `T_GOODSINFO`;
CREATE TABLE `T_GOODSINFO` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`NAME` varchar(30) NOT NULL COMMENT '商品名称',
    	`INTRODUCE` varchar(30) NOT NULL COMMENT '商品介绍',
    	`DESC` varchar(30) NOT NULL COMMENT '商品描述',
    	`PRICE` double NOT NULL COMMENT '商品原价',
    	`DISCOUNT_PRICE` double NOT NULL COMMENT '折扣价',
    	`SALES_NUM` int(11) NOT NULL COMMENT '销售量',
    	`IMAGE_URL` varchar(30) NOT NULL COMMENT '介绍图地址',
    	`BANNEL_URL` varchar(30) NOT NULL COMMENT 'bannel地址',
    	`TYPE` tinyint(4) NOT NULL COMMENT '商品类型',
    	`STATUS` varchar(30) NOT NULL COMMENT '商品状态',
    	`UPDATE_TIME` varchar(30) NOT NULL COMMENT '更新时间',
    	`REMARK` varchar(30) NOT NULL COMMENT '管理员备注',
    	`CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
    	`DELETE_STATE` tinyint(4) NOT NULL COMMENT '逻辑删除标志位：1正常0删除',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
