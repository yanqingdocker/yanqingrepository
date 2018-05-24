/*
Navicat MySQL Data Transfer

Source Server         : money123.mysql.rds.aliyuncs.com
Source Server Version : 50718
Source Host           : money123.mysql.rds.aliyuncs.com:3306
Source Database       : money

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-05-24 12:29:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for appliy
-- ----------------------------
DROP TABLE IF EXISTS `appliy`;
CREATE TABLE `appliy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `realname` varchar(255) DEFAULT NULL,
  `appliycount` varchar(255) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of appliy
-- ----------------------------
INSERT INTO `appliy` VALUES ('1', '胡彦清', '18193412366', '10', '2018-05-10');

-- ----------------------------
-- Table structure for authoirty
-- ----------------------------
DROP TABLE IF EXISTS `authoirty`;
CREATE TABLE `authoirty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authoirtyname` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authoirty
-- ----------------------------
INSERT INTO `authoirty` VALUES ('9', '删除成员', '/muser/delete');
INSERT INTO `authoirty` VALUES ('10', '查看所有成员', ' /muser/queryAll');
INSERT INTO `authoirty` VALUES ('11', '成员登录', '/muser/login');
INSERT INTO `authoirty` VALUES ('12', '成员注销', '/muser/logout');
INSERT INTO `authoirty` VALUES ('13', '查看所有会员', '/user/queryAl');
INSERT INTO `authoirty` VALUES ('14', '修改成员密码', '/muser/resetPwd');
INSERT INTO `authoirty` VALUES ('15', '给成员修改角色', '/userrole/batchUpdate');
INSERT INTO `authoirty` VALUES ('16', '添加角色', '/role/addRole');
INSERT INTO `authoirty` VALUES ('17', '修改角色', '/role/updateRole');
INSERT INTO `authoirty` VALUES ('18', '给角色修改权限', '/roleauth/ batchUpdate');
INSERT INTO `authoirty` VALUES ('19', '删除角色', ' /role/deleteRole');
INSERT INTO `authoirty` VALUES ('20', '查询所有角色', '/role/queryAll');
INSERT INTO `authoirty` VALUES ('21', '根据ID查询角色', '/role/queryAll');
INSERT INTO `authoirty` VALUES ('22', '查看所有账户', '/count/queryAllCount');
INSERT INTO `authoirty` VALUES ('23', '修改账户状态', '/count/startOrstopCount');
INSERT INTO `authoirty` VALUES ('24', '查看所有支付宝账户列表', '/rate/queryAll');
INSERT INTO `authoirty` VALUES ('25', '查看所有银行卡列表', '/bank/queryAll');
INSERT INTO `authoirty` VALUES ('26', '现金存入', '/count/inMoney');
INSERT INTO `authoirty` VALUES ('27', '现金转出', '/count/outMoney');
INSERT INTO `authoirty` VALUES ('28', '现金兑换', '/cashpool/exchange');
INSERT INTO `authoirty` VALUES ('29', '查询平台资金', '/count/queryblancebyType');
INSERT INTO `authoirty` VALUES ('30', '查询所有货币类型', '/count/queryMoneyType');
INSERT INTO `authoirty` VALUES ('31', '查询不同类型的操作记录统计条目总和', '/opera/queryoperacount');
INSERT INTO `authoirty` VALUES ('32', '汇率查询', '/rate/queryAll');
INSERT INTO `authoirty` VALUES ('33', '查询不同类型的操作记录金额总和', '/opera/queryoperatype');
INSERT INTO `authoirty` VALUES ('34', '查询所有账户的操作记录', '/opera/queryAll');
INSERT INTO `authoirty` VALUES ('35', '查询权限', '/authoirty/queryAll');
INSERT INTO `authoirty` VALUES ('36', '查询现金库', '/cashpool/queryAll');
INSERT INTO `authoirty` VALUES ('37', '根据类型查询单个汇率', '/rate/getSingleRate');
INSERT INTO `authoirty` VALUES ('38', '查询当天所有账户的操作记录', '/opera/queryByDay');
INSERT INTO `authoirty` VALUES ('39', '查询本周所有账户的操作记录', '/opera/queryByWeek');
INSERT INTO `authoirty` VALUES ('40', '查询本月所有账户的操作记录', '/opera/queryByMonth');
INSERT INTO `authoirty` VALUES ('41', '查询本季所有账户的操作记录', '/opera/queryByquarter');
INSERT INTO `authoirty` VALUES ('42', '查询本年度所有账户的操作记录', '/opera/queryByYear');
INSERT INTO `authoirty` VALUES ('43', '添加权限', '/authoirty/add');
INSERT INTO `authoirty` VALUES ('44', '修改权限', '/authoirty/add');
INSERT INTO `authoirty` VALUES ('45', '删除权限', '/authoirty/delete');
INSERT INTO `authoirty` VALUES ('46', '查看待处理任务', '/task/queryUndo');
INSERT INTO `authoirty` VALUES ('47', '查看已处理任务', '/task/queryDone');
INSERT INTO `authoirty` VALUES ('48', '确认处理任务', '/task/dotask');
INSERT INTO `authoirty` VALUES ('49', '添加成员', '/muser/add');

-- ----------------------------
-- Table structure for bankcard
-- ----------------------------
DROP TABLE IF EXISTS `bankcard`;
CREATE TABLE `bankcard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bankcard` varchar(255) DEFAULT NULL,
  `banktype` varchar(255) DEFAULT NULL,
  `idcard` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `cardaddress` varchar(255) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bankcard
-- ----------------------------
INSERT INTO `bankcard` VALUES ('2', '6217002020025396102', '中国建设银行', '62282519940820121X', '胡彦清', '18193412366', '中国南昌', '4', '2018-04-28 14:31:43');
INSERT INTO `bankcard` VALUES ('3', '6225768612776185', '招商银行', '62282519940820121X', '胡彦清', '18193412366', '中国西安', '4', '2018-04-28 14:33:39');
INSERT INTO `bankcard` VALUES ('4', '6217002020025396102', '中国建设银行', '62282519940820121X', '胡彦清', '18193412366', '中国南昌', '10', '2018-05-11 12:03:39');

-- ----------------------------
-- Table structure for cashpool
-- ----------------------------
DROP TABLE IF EXISTS `cashpool`;
CREATE TABLE `cashpool` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `countid` varchar(255) DEFAULT NULL,
  `counttype` varchar(255) DEFAULT NULL,
  `blance` double(255,0) DEFAULT NULL,
  `lasttime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cashpool
-- ----------------------------
INSERT INTO `cashpool` VALUES ('1', '人民币资金库', 'CNY', '22', '2018-05-17 16:42:54');
INSERT INTO `cashpool` VALUES ('2', '美元资金库', 'USD', '226', '2018-05-17 16:42:54');

-- ----------------------------
-- Table structure for count
-- ----------------------------
DROP TABLE IF EXISTS `count`;
CREATE TABLE `count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cardid` varchar(255) DEFAULT NULL,
  `counttype` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT '1',
  `blance` double DEFAULT '0',
  `paypwd` varchar(255) DEFAULT NULL,
  `checkcode` varchar(255) DEFAULT NULL,
  `exception` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of count
-- ----------------------------
INSERT INTO `count` VALUES ('30', '688485602360', 'CNY', '2018-05-11 14:57:36', '10', '1', '431', 'e10adc3949ba59abbe56e057f20f883e', '-760803427', '0');
INSERT INTO `count` VALUES ('31', '688485602361', 'USD', '2018-05-11 14:57:46', '10', '1', '198.504', 'e10adc3949ba59abbe56e057f20f883e', '1030041292', '0');
INSERT INTO `count` VALUES ('32', '688485602362', 'CNY', '2018-05-11 16:55:12', '11', '1', '10367.048700000001', '670b14728ad9902aecba32e22fa4f6bd', '1547912150', '0');
INSERT INTO `count` VALUES ('33', '688485602363', 'USD', '2018-05-11 16:55:19', '11', '1', '80.7272', '670b14728ad9902aecba32e22fa4f6bd', '148602333', '0');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `messagetype` int(1) DEFAULT NULL,
  `visiable` int(1) DEFAULT '0',
  `userid` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('13', 'TITLE', 'AAAAAAAAAAAAAAAAAA', '2018-04-28 15:29:15', '胡彦清', '0', '0', '6');
INSERT INTO `message` VALUES ('14', 'TITLE', 'AAAAAAAAAAAAAAAAAA', '2018-04-28 15:29:15', 'dd', '1', '0', '4');
INSERT INTO `message` VALUES ('15', 'title', 'aaaaaaaaaaaaaaaaaaaaaaa', '2018-05-03 11:47:48', '胡彦清', '0', '0', '4');
INSERT INTO `message` VALUES ('16', 'title', 'aaaaaaaaaaaaaaaaaaaaaaa', '2018-05-03 11:47:48', '胡彦清', '1', '0', '7');
INSERT INTO `message` VALUES ('17', 'title', '转账到6248451213246 100万', '2018-05-11 17:40:04', '胡彦清', '0', '1', '11');
INSERT INTO `message` VALUES ('18', 'title', '转账到6248451213246 100万', '2018-05-11 17:40:04', '张丽晶', '1', '0', '10');
INSERT INTO `message` VALUES ('19', '哈哈哈哈哈啊哈哈哈或或或或或或', '啊哈哈哈啥哈哈哈哈哈哈哈哈哈哈fgfgfgfgfg', '2018-05-11 17:47:01', '张丽晶', '0', '0', '10');
INSERT INTO `message` VALUES ('20', '哈哈哈哈哈啊哈哈哈或或或或或或', '啊哈哈哈啥哈哈哈哈哈哈哈哈哈哈fgfgfgfgfg', '2018-05-11 17:47:01', '胡彦清', '1', '0', '11');
INSERT INTO `message` VALUES ('21', 'cccc', 'gggggggggggg', '2018-05-18 15:49:52', '胡彦清', '0', '1', '11');
INSERT INTO `message` VALUES ('22', 'cccc', 'gggggggggggg', '2018-05-18 15:49:52', '张丽晶', '1', '0', '10');
INSERT INTO `message` VALUES ('23', 'cc', 'cccc', '2018-05-22 16:45:02', '张丽晶', '0', '0', '10');
INSERT INTO `message` VALUES ('24', 'cc', 'cccc', '2018-05-22 16:45:02', '胡彦清', '1', '0', '11');

-- ----------------------------
-- Table structure for muser
-- ----------------------------
DROP TABLE IF EXISTS `muser`;
CREATE TABLE `muser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of muser
-- ----------------------------
INSERT INTO `muser` VALUES ('1', 'admin', '123456');

-- ----------------------------
-- Table structure for operalog
-- ----------------------------
DROP TABLE IF EXISTS `operalog`;
CREATE TABLE `operalog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `countid` varchar(15) DEFAULT NULL,
  `counttype` varchar(255) DEFAULT NULL,
  `num` double DEFAULT NULL,
  `operatype` varchar(255) DEFAULT NULL,
  `operatime` varchar(255) DEFAULT NULL,
  `operauser` varchar(255) DEFAULT NULL,
  `oi` int(11) DEFAULT NULL,
  `operaip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operalog
-- ----------------------------
INSERT INTO `operalog` VALUES ('1', '688485602363', 'USD', '10', '现金存入', '2018-05-27 16:31:14', '操作员-null', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('2', '688485602361', 'USD', '1.571', '现金存入', '2018-05-17 16:31:27', '操作员-null', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('3', '人民币资金库', 'CNY', '-1', '现金兑换', '2018-05-17 16:31:37', '操作员-null', '0', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('4', '美元资金库', 'USD', '6.3663', '现金兑换', '2018-05-17 16:31:37', '操作员-null', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('5', '688485602360', 'CNY', '100', '现金存入', '2018-05-17 16:41:42', '操作员-null', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('6', '688485602361', 'USD', '94.2', '现金存入', '2018-05-17 16:42:14', '操作员-null', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('7', '人民币资金库', 'CNY', '-2', '现金兑换', '2018-05-17 16:42:54', '操作员-null', '0', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('8', '美元资金库', 'USD', '12.7334', '现金兑换', '2018-05-17 16:42:54', '操作员-null', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('9', '688485602360', 'CNY', '-30', '转账', '2018-05-17 16:46:10', '会员-胡彦清', '0', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('10', '688485602362', 'CNY', '30', '转账', '2018-05-17 16:46:10', '会员-胡彦清', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('11', '688485602360', 'CNY', '-1', '兑换', '2018-05-17 16:46:53', '会员-胡彦清', '0', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('12', '688485602361', 'USD', '0.157', '兑换', '2018-05-17 16:46:53', '会员-胡彦清', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('13', '688485602362', 'CNY', '-10', '兑换', '2018-05-17 18:22:32', '会员-张丽晶', '0', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('14', '688485602363', 'USD', '1.57', '兑换', '2018-05-17 18:22:32', '会员-张丽晶', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('15', '688485602360', 'CNY', '-900', '现金转出', '2018-05-17 20:45:56', '操作员-null', '0', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('16', '688485602362', 'CNY', '6.3739', '现金存入', '2018-05-18 16:47:03', '操作员-null', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('17', '688485602362', 'CNY', '76.4868', '现金存入', '2018-05-18 16:48:53', '操作员-null', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('18', '688485602360', 'CNY', '1', '现金存入', '2018-05-18 16:50:03', '操作员-null', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('19', '688485602361', 'USD', '1', '现金存入', '2018-05-18 16:50:31', '操作员-null', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('20', '688485602360', 'CNY', '20', '现金存入', '2018-05-18 19:08:55', '操作员-null', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('21', '688485602360', 'CNY', '100', '现金存入', '2018-05-22 10:55:23', '操作员-admin', '1', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('22', '688485602362', 'CNY', '-10', '转账', '2018-05-24 11:00:41', '会员-张丽晶', '0', '0:0:0:0:0:0:0:1');
INSERT INTO `operalog` VALUES ('23', '688485602362', 'CNY', '10', '转账', '2018-05-24 11:00:42', '会员-张丽晶', '1', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('2', '会计', '2018-05-18');
INSERT INTO `role` VALUES ('3', '超级管理员', '2018-05-18');
INSERT INTO `role` VALUES ('4', '出纳', '2018-05-18');

-- ----------------------------
-- Table structure for roleauth
-- ----------------------------
DROP TABLE IF EXISTS `roleauth`;
CREATE TABLE `roleauth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL,
  `authid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roleauth
-- ----------------------------

-- ----------------------------
-- Table structure for servicemoney
-- ----------------------------
DROP TABLE IF EXISTS `servicemoney`;
CREATE TABLE `servicemoney` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bankcard` varchar(255) DEFAULT NULL,
  `tradetype` varchar(255) DEFAULT NULL,
  `banktype` varchar(255) DEFAULT NULL,
  `moneynum` double DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `servicetype` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of servicemoney
-- ----------------------------

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskname` varchar(255) DEFAULT NULL,
  `taskcontent` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `operauser` varchar(255) DEFAULT NULL,
  `douser` varchar(255) DEFAULT NULL,
  `endtime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('3', '转账申请', '转账100', '2018-05-18 13:21:23', '待处理', 'admin', '', '2018-05-23 11:40:50');
INSERT INTO `task` VALUES ('4', '转账申请', '转账100', '2018-05-18 13:21:26', '已处理', 'admin', 'admin', '2015-5-01-09');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `idcard` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `isauthentication` int(1) DEFAULT '0',
  `createtime` varchar(255) DEFAULT NULL,
  `lasttime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('10', '胡彦清', '670b14728ad9902aecba32e22fa4f6bd', '18193412366', '863643344@qq.com', '62282519940820121X', '2018-5-4', '河南省信阳市罗山县a', '1', '2018-05-04 13:41:45', '2018-05-04 13:50:00');
INSERT INTO `user` VALUES ('11', '张丽晶', '670b14728ad9902aecba32e22fa4f6bd', '18305976106', '941257921@com', '352228199202262026', '2018-5-11', '福建省福州市鼓楼区chahui', '1', '2018-05-11 16:53:59', '2018-05-11 16:54:52');

-- ----------------------------
-- Table structure for userrole
-- ----------------------------
DROP TABLE IF EXISTS `userrole`;
CREATE TABLE `userrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `roleid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userrole
-- ----------------------------
INSERT INTO `userrole` VALUES ('52', '1', '3');
