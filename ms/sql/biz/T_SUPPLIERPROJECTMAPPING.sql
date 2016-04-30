
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_SUPPLIER_PROJECT_MAPPING`
-- ----------------------------
DROP TABLE IF EXISTS `T_SUPPLIER_PROJECT_MAPPING`;
CREATE TABLE `T_SUPPLIER_PROJECT_MAPPING` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`GS_CODE` varchar(50) NOT NULL COMMENT '统一社会信用代码',
    	`LOCATION` varchar(50) NOT NULL COMMENT '经营场所',
    	`NAME` varchar(20) NOT NULL COMMENT '甲方名称',
    	`TOTAL_AMOUNT` varchar(50) NOT NULL COMMENT '合伙人认缴出资',
    	`REAL_TOTAL_AMOUNT` varchar(20) NOT NULL COMMENT '合伙人实缴',
    	`SUPPLIER_REAL_AMOUNT` varchar(20) NOT NULL COMMENT '甲方已实缴',
    	`SUPPLIER_HOLD_PERC` varchar(20) NOT NULL COMMENT '甲方持有财产份额',
    	`PRO_ID` varchar(20) NOT NULL COMMENT '标的ID',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
