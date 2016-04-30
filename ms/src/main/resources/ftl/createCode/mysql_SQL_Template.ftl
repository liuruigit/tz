
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `${tabletop}${objectNameUpper}`
-- ----------------------------
DROP TABLE IF EXISTS `${tabletop}${objectNameUpper}`;
CREATE TABLE `${tabletop}${objectNameUpper}` (
		`ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	<#list fieldList as var>
    	`${var[7]}` ${var[1]} NOT NULL COMMENT '${var[2]}',
	</#list>
  		PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
