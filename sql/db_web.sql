/*
Navicat MySQL Data Transfer

Source Server         : 本地连接
Source Server Version : 50171
Source Host           : localhost:3306
Source Database       : db_web

Target Server Type    : MYSQL
Target Server Version : 50171
File Encoding         : 65001

Date: 2016-03-07 22:43:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ACC_NAME` varchar(40) DEFAULT NULL COMMENT '账号',
  `PWD` varchar(48) DEFAULT NULL COMMENT '密码',
  `TRADE_PWD` varchar(48) DEFAULT NULL COMMENT '交易密码',
  `LINE_PWD` varchar(48) DEFAULT NULL COMMENT '手势密码',
  `TOKEN` varchar(150) DEFAULT NULL COMMENT 'token',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间 ',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `STATUS` tinyint(4) DEFAULT '0' COMMENT '状态：0正常1临时封号2永久封号',
  `DELETE_STATE` tinyint(4) DEFAULT '1' COMMENT '逻辑删除标志位：1正常0删除',
  `RECOMMEND_ID` int(11) DEFAULT '0' COMMENT '推荐人ID',
  `MOBILE` varchar(40) DEFAULT NULL COMMENT '手机号',
  `EMAIL` varchar(44) DEFAULT NULL COMMENT '邮箱',
  `CERT_NO` varchar(40) DEFAULT NULL COMMENT '身份证号',
  `REAL_NAME` varchar(40) DEFAULT NULL COMMENT '真实姓名',
  `PREPARE_STEP` tinyint(4) DEFAULT '0' COMMENT '投资准备0未开始1已实名2已设置交易密码3已绑卡',
  `LV` int(11) unsigned zerofill DEFAULT '00000000001' COMMENT '等级',
  `POINT` int(11) unsigned zerofill DEFAULT '00000000000' COMMENT '积分',
  `MONEY` bigint(20) unsigned zerofill DEFAULT '00000000000000000000' COMMENT '余额',
  `FROZEN_MONEY` bigint(20) unsigned zerofill DEFAULT '00000000000000000000' COMMENT '冻结金额',
  `INVEST_MONEY` bigint(20) DEFAULT '0' COMMENT '投资金额',
  `DIGEST` varchar(64) DEFAULT NULL COMMENT '摘要',
  `VERSION` int(11) DEFAULT '0' COMMENT '乐观锁版本号',
  `ACC_INCOME` bigint(20) unsigned zerofill DEFAULT '00000000000000000000' COMMENT '累计收益',
  `VIP` int(11) DEFAULT '0' COMMENT 'VIP级别',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_account_change
-- ----------------------------
DROP TABLE IF EXISTS `t_account_change`;
CREATE TABLE `t_account_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` bigint(20) DEFAULT NULL COMMENT '变更金额',
  `acc_money` bigint(20) DEFAULT NULL COMMENT '余额',
  `order_no` varchar(20) NOT NULL COMMENT '订单号',
  `create_time` datetime DEFAULT NULL,
  `type` varchar(4) DEFAULT NULL COMMENT '变更类型加、减（余额）',
  `user_id` int(11) DEFAULT NULL,
  `tran_name` varchar(20) DEFAULT NULL COMMENT '业务描述',
  `extra_msg1` varchar(50) DEFAULT NULL COMMENT '记录每笔交易的冗余信息',
  `extra_msg2` varchar(50) DEFAULT NULL COMMENT '记录每笔交易的冗余信息',
  `extra_msg3` varchar(50) DEFAULT NULL COMMENT '记录每笔交易的冗余信息',
  `extra_msg4` varchar(50) DEFAULT NULL COMMENT '记录每笔交易的冗余信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_account_login
-- ----------------------------
DROP TABLE IF EXISTS `t_account_login`;
CREATE TABLE `t_account_login` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `USER_ID` int(11) NOT NULL COMMENT 'USER_ID',
  `IMEI` varchar(50) DEFAULT NULL COMMENT '登录设备',
  `TIME` datetime DEFAULT NULL COMMENT '登录时间',
  `TOKEN` varchar(100) DEFAULT NULL COMMENT 'token',
  `TYPE` tinyint(4) DEFAULT NULL COMMENT '0密码1token2手势验证码',
  `IP` varchar(20) DEFAULT NULL,
  `CLIENT` tinyint(4) DEFAULT NULL COMMENT '客户端0web1app2微信',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_acc_coupon
-- ----------------------------
DROP TABLE IF EXISTS `t_acc_coupon`;
CREATE TABLE `t_acc_coupon` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ACC_ID` int(11) NOT NULL COMMENT '用户ID',
  `AMOUNT` bigint(20) NOT NULL COMMENT '金额',
  `STATUS` tinyint(4) DEFAULT '0' COMMENT '0未使用1使用2冻结',
  `COUPON_ID` bigint(20) NOT NULL COMMENT '最小投资使用条件',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建日期',
  `USED_TIME` datetime DEFAULT NULL COMMENT '使用日期',
  `DELETE_STATE` tinyint(4) NOT NULL DEFAULT '1',
  `VALID_DATE` datetime DEFAULT NULL COMMENT '有效日期s',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_bank_card
-- ----------------------------
DROP TABLE IF EXISTS `t_bank_card`;
CREATE TABLE `t_bank_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `bank_card_no` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '银行卡号',
  `mobile` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '绑定的手机号',
  `bank_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `bank_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '简码',
  `pro` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所在省',
  `city` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所在市',
  `branch` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支行名称',
  `no_agree` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '签约协议号',
  `create_time` datetime DEFAULT NULL,
  `is_default` tinyint(4) DEFAULT '0' COMMENT '0非默认1默认',
  `delete_state` tinyint(4) DEFAULT NULL,
  `prcptcd` varchar(50) COLLATE utf8_unicode_ci DEFAULT '',
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for t_bank_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_bank_rule`;
CREATE TABLE `t_bank_rule` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `BANK_NAME` varchar(20) NOT NULL COMMENT '银行名称',
  `BANK_SHORT_NAME` varchar(20) NOT NULL COMMENT '银行简称',
  `LIMIT` double NOT NULL COMMENT '单笔限额',
  `DAY_LIMIT` double NOT NULL COMMENT '单日限额',
  `TIME_LIMIT_BEGIN` time DEFAULT NULL COMMENT '起始时间',
  `TIME_LIMIT_END` time DEFAULT NULL COMMENT '结束时间',
  `CHANNEL_NAME` varchar(20) DEFAULT NULL COMMENT '渠道名称',
  `DELETE_STATE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '逻辑删除标志位：1正常0删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_channel_order
-- ----------------------------
DROP TABLE IF EXISTS `t_channel_order`;
CREATE TABLE `t_channel_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` bigint(20) DEFAULT NULL,
  `bank_id` int(11) NOT NULL COMMENT '银行卡ID',
  `order_no` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求订单号',
  `syn_result` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '同步应答码',
  `asy_result` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '异步应答码',
  `notify_time` datetime DEFAULT NULL COMMENT '通知时间',
  `tran_flow` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '外部订单号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '0发起充值1平台接收处理中2到账',
  `create_time` datetime DEFAULT NULL,
  `delete_state` tinyint(4) DEFAULT '1',
  `type` tinyint(2) unsigned DEFAULT NULL COMMENT '订单类型0充值1提现',
  `channel` tinyint(3) DEFAULT '0' COMMENT '0金运通1建行',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for t_config
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `KEY` varchar(20) NOT NULL COMMENT '配置KEY',
  `VALUE` varchar(100) NOT NULL COMMENT '配置value',
  `DESR` varchar(50) NOT NULL COMMENT '描述',
  `DELETE_STATE` tinyint(4) NOT NULL DEFAULT '1',
  `TYPE` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_content_type
-- ----------------------------
DROP TABLE IF EXISTS `t_content_type`;
CREATE TABLE `t_content_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `TYPE_KEY` varchar(20) NOT NULL COMMENT '网站内容KEY',
  `TYPE_DES` varchar(20) NOT NULL COMMENT '网站内容类型描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_coupon
-- ----------------------------
DROP TABLE IF EXISTS `t_coupon`;
CREATE TABLE `t_coupon` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `DESC` varchar(100) DEFAULT NULL,
  `NAME` varchar(30) NOT NULL COMMENT '投资券名称',
  `MIN` bigint(20) NOT NULL COMMENT '最小投资使用条件',
  `BEGIN_DATE` datetime NOT NULL COMMENT '有效日期',
  `END_DATE` datetime NOT NULL COMMENT '有效日期',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建日期',
  `DELETE_STATE` tinyint(4) NOT NULL DEFAULT '1',
  `TAG` varchar(255) DEFAULT NULL COMMENT '限制',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_feedback
-- ----------------------------
DROP TABLE IF EXISTS `t_feedback`;
CREATE TABLE `t_feedback` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ACC_ID` int(11) NOT NULL COMMENT '用户ID',
  `ATTACH` varchar(200) NOT NULL COMMENT '附件',
  `CONTENT` varchar(100) NOT NULL COMMENT '反馈信息',
  `STATUS` tinyint(4) DEFAULT '0' COMMENT '状态0未处理1已处理',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建日期',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新日期',
  `DELETE_STATE` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_invest_order
-- ----------------------------
DROP TABLE IF EXISTS `t_invest_order`;
CREATE TABLE `t_invest_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ACC_ID` int(11) NOT NULL COMMENT '用户ID',
  `ACC_NAME` varchar(40) NOT NULL COMMENT '用户名称',
  `PRO_ID` int(11) NOT NULL COMMENT '标的ID',
  `AMOUNT` bigint(20) unsigned zerofill NOT NULL COMMENT '投资金额',
  `NO` varchar(20) NOT NULL COMMENT '订单号',
  `CONTRACT` varchar(20) DEFAULT NULL COMMENT '合同号',
  `STATUS` tinyint(4) unsigned NOT NULL COMMENT '状态:0发起认购1认购成功',
  `DELETE_STATE` tinyint(4) unsigned NOT NULL COMMENT '删除状态',
  `SUCC_TIME` datetime DEFAULT NULL COMMENT '分润到账时间',
  `PAY_TYPE` varchar(20) DEFAULT NULL COMMENT '支付方式',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `APP_VERSION` varchar(20) NOT NULL COMMENT '客户端版本号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_jyt_billing
-- ----------------------------
DROP TABLE IF EXISTS `t_jyt_billing`;
CREATE TABLE `t_jyt_billing` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `BILLING_DATE` date NOT NULL COMMENT '对账日期',
  `USER_ID` varchar(30) NOT NULL COMMENT '商户号',
  `COUNT` double NOT NULL COMMENT '交易总笔数',
  `AMOUNT` double NOT NULL COMMENT '交易总金额',
  `SUCC_COUNT` double NOT NULL COMMENT '成功总笔数',
  `SUCC_AMOUNT` double NOT NULL COMMENT '成功总金额',
  `FAIL_COUNT` double NOT NULL COMMENT '失败总笔数',
  `FAIL_AMOUNT` double NOT NULL COMMENT '失败总金额',
  `TYPE` bigint(4) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `TITLE` varchar(20) NOT NULL COMMENT '标题',
  `CONTENT` varchar(50) NOT NULL COMMENT '消息内容',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建日期',
  `DELETE_STATE` tinyint(4) DEFAULT '1' COMMENT '逻辑删除标志位：1正常0删除',
  `ACC_ID` int(11) DEFAULT NULL COMMENT '用户ID',
  `SYSTEM_MSG` tinyint(2) DEFAULT NULL COMMENT '0系统1个人',
  `IS_READ` tinyint(2) DEFAULT '0' COMMENT '是否阅读0未读1已读',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_month_billing
-- ----------------------------
DROP TABLE IF EXISTS `t_month_billing`;
CREATE TABLE `t_month_billing` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ACC_ID` int(11) NOT NULL COMMENT '用户ID',
  `MONEY` bigint(20) NOT NULL COMMENT '余额',
  `PROPERTY` bigint(20) DEFAULT '0' COMMENT '总资产',
  `ACC_INCOME` bigint(20) NOT NULL COMMENT '收益',
  `CHARGE` bigint(20) NOT NULL COMMENT '充值',
  `CASH` bigint(20) NOT NULL COMMENT '提现',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建日期',
  `YEAR_MONTH` varchar(20) NOT NULL COMMENT '使用日期',
  `DELETE_STATE` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_notices
-- ----------------------------
DROP TABLE IF EXISTS `t_notices`;
CREATE TABLE `t_notices` (
  `NOTICES_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(200) DEFAULT NULL COMMENT '标题',
  `CONTENT` text COMMENT '富文本内容',
  `PICTURE_URL` varchar(300) DEFAULT NULL COMMENT '轮播图片URL',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建日期',
  `DELETE_STATE` tinyint(4) DEFAULT '1' COMMENT '逻辑删除标志位：1正常0删除',
  `CONTENT_TYPE` varchar(20) DEFAULT '' COMMENT '公告内容类型：NEWS 新闻资讯 ， NOTICES 网站公告',
  `INDEX` int(4) DEFAULT NULL COMMENT '第几条',
  `URL` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`NOTICES_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_project
-- ----------------------------
DROP TABLE IF EXISTS `t_project`;
CREATE TABLE `t_project` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `NO` varchar(30) NOT NULL COMMENT '标的编号',
  `GUARANTEE` tinyint(4) NOT NULL COMMENT '保障级别',
  `PAY_INTEREST_WAY` tinyint(4) DEFAULT NULL COMMENT '付息方式',
  `AMOUNT` double NOT NULL COMMENT '融资金额',
  `FINAL_AMOUNT` double DEFAULT NULL COMMENT '实际出售金额',
  `SELLED_AMOUNT` double DEFAULT NULL COMMENT '已融资金额',
  `MIN` double NOT NULL COMMENT '起投金额',
  `STEP` double NOT NULL COMMENT '累加金额',
  `LIMIT` double NOT NULL COMMENT '限投金额',
  `ANNUAL_RATE` double NOT NULL COMMENT '预期年化收益率',
  `SERVICE_RATE` double NOT NULL COMMENT '服务费率',
  `OPEN_DATE` datetime NOT NULL COMMENT '开放购买时间',
  `END_DATE` datetime NOT NULL COMMENT '结束购买时间',
  `SOLD_OUT_DATE` datetime DEFAULT NULL COMMENT '售罄日期',
  `STATUS` tinyint(4) NOT NULL COMMENT '状态：0新建1开放购买2已满标3已结算4已还款',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `RECOMMEND` tinyint(4) NOT NULL COMMENT '是否推荐',
  `EXTRA1` varchar(20) DEFAULT NULL COMMENT '预留信息1',
  `EXTRA2` varchar(20) DEFAULT NULL COMMENT '预留信息2',
  `EXTRA3` varchar(20) DEFAULT NULL COMMENT '预留信息3',
  `DAYS` smallint(6) DEFAULT NULL COMMENT '持续天数',
  `UPDATE_TIME` datetime DEFAULT NULL,
  `DELETE_STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识位：1：正常 ； 2：已删除，',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_project_attach_file
-- ----------------------------
DROP TABLE IF EXISTS `t_project_attach_file`;
CREATE TABLE `t_project_attach_file` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PRO_ID` int(11) NOT NULL COMMENT '标的ID',
  `NAME` varchar(10) NOT NULL COMMENT '附件名称',
  `DESC` varchar(20) NOT NULL COMMENT '附件描述',
  `TYPE` tinyint(4) DEFAULT '0' COMMENT '类型',
  `FILE_PATH` varchar(500) NOT NULL COMMENT '存储地址',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建日期',
  `URL` varchar(200) DEFAULT NULL,
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新日期',
  `DELETE_STATE` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_project_info
-- ----------------------------
DROP TABLE IF EXISTS `t_project_info`;
CREATE TABLE `t_project_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PRO_ID` int(11) DEFAULT NULL,
  `PRO_NO` varchar(30) NOT NULL COMMENT '标的编号',
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
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `DELETE_STATE` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_security_question
-- ----------------------------
DROP TABLE IF EXISTS `t_security_question`;
CREATE TABLE `t_security_question` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ACC_ID` int(11) NOT NULL COMMENT '用户ID',
  `QUESTION` varchar(20) NOT NULL COMMENT '问题',
  `ANSWER` varchar(20) NOT NULL COMMENT '答案',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `DELETE_STATE` tinyint(4) NOT NULL COMMENT '逻辑删除标志位',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_tran_details
-- ----------------------------
DROP TABLE IF EXISTS `t_tran_details`;
CREATE TABLE `t_tran_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `TRAN_TYPE` varchar(20) NOT NULL COMMENT '交易类型',
  `TRAN_DATE` varchar(30) NOT NULL COMMENT '商户交易时间',
  `TRAN_DATED` varchar(30) NOT NULL COMMENT '交易完成时间',
  `ORDER_ID` varchar(20) NOT NULL COMMENT '商户订单号',
  `NUM` varchar(100) NOT NULL COMMENT '批次号',
  `REP_ORDER_ID` varchar(100) NOT NULL COMMENT '代收付平台订单号',
  `TRAN_AMOUT` double NOT NULL COMMENT '交易金额',
  `CURRENCY` varchar(20) NOT NULL COMMENT '币种',
  `BANK_NO` varchar(20) NOT NULL COMMENT '客户交易银行账号',
  `BANK_NAME` varchar(20) NOT NULL COMMENT '客户交易银行账户名称',
  `USER_FIC_NO` varchar(20) NOT NULL COMMENT '商户交易虚拟账号',
  `RESULT_NO` varchar(20) NOT NULL COMMENT '交易结果码',
  `RESULT_DESC` varchar(20) NOT NULL COMMENT '交易结果描述',
  `POUND` double NOT NULL COMMENT '手续费',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_unbinding_apply
-- ----------------------------
DROP TABLE IF EXISTS `t_unbinding_apply`;
CREATE TABLE `t_unbinding_apply` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ACC_ID` int(11) NOT NULL COMMENT '用户ID',
  `ATTACH` varchar(200) NOT NULL COMMENT '附件',
  `STATUS` tinyint(4) DEFAULT '0' COMMENT '状态0申请中1解绑失败2解绑成功',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建日期',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新日期',
  `DELETE_STATE` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_hlb_change_record
-- ----------------------------
DROP TABLE IF EXISTS `t_hlb_change_record`;
CREATE TABLE `t_hlb_change_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `AMOUNT` bigint(20) NOT NULL COMMENT '葫芦币数量',
  `CHANGE_AMOUNT` bigint(20) NOT NULL COMMENT '葫芦币变更数量',
  `ACC_ID` bigint(20) NOT NULL COMMENT '用户ID',
  `DESC` bigint(20) NOT NULL COMMENT '描述',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建日期',
  `DELETE_STATE` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
