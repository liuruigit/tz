
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `T_CONTENTTYPE`
-- ----------------------------
DROP TABLE IF EXISTS `T_CONTENT_TYPE`;
CREATE TABLE `T_CONTENT_TYPE` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    	`TYPE_KEY` varchar(20) NOT NULL COMMENT '网站内容KEY',
    	`TYPE_DES` varchar(20) NOT NULL COMMENT '网站内容类型描述',
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
