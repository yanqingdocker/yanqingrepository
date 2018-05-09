/*
Navicat MySQL Data Transfer

Source Server         : DB
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : money

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-05-02 12:20:32
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bankcard
-- ----------------------------
INSERT INTO `bankcard` VALUES ('2', '6217002020025396102', '中国建设银行', '62282519940820121X', '胡彦清', '18193412366', '中国南昌', '4', '2018-04-28 14:31:43');
INSERT INTO `bankcard` VALUES ('3', '6225768612776185', '招商银行', '62282519940820121X', '胡彦清', '18193412366', '中国西安', '4', '2018-04-28 14:33:39');

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
  `blance` double(20,0) DEFAULT '0',
  `paypwd` varchar(255) DEFAULT NULL,
  `checkcode` varchar(255) DEFAULT NULL,
  `exception` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of count
-- ----------------------------
INSERT INTO `count` VALUES ('5', '688485602361', 'CNY', '2018-04-20 13:12:52', '6', '1', '50', '123456', null, '0');
INSERT INTO `count` VALUES ('7', '688485602363', '美元', '2018-04-25 11:42:37', '5', '1', null, null, null, '0');
INSERT INTO `count` VALUES ('8', '688485602364', '英镑', '2018-04-26 17:48:17', '5', '1', '10', '123456', null, '0');
INSERT INTO `count` VALUES ('18', '688485602365', 'CNY', '2018-05-02 10:59:31', '4', '1', '10', '123456', '246817277', '0');
INSERT INTO `count` VALUES ('20', '688485602366', 'USD', '2018-05-02 12:04:47', '4', '1', '0', '000000', null, '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('13', 'TITLE', 'AAAAAAAAAAAAAAAAAA', '2018-04-28 15:29:15', '胡彦清', '0', '0', '6');
INSERT INTO `message` VALUES ('14', 'TITLE', 'AAAAAAAAAAAAAAAAAA', '2018-04-28 15:29:15', 'dd', '1', '0', '4');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('4', '胡彦清', 'e10adc3949ba59abbe56e057f20f883e', '18193412366', '863643344@qq.com', '62282519940820121X', '2018-04-13', '吉林省吉林市昌邑区阿斯蒂芬', '1', '2018-04-25 11:21:06', '2018-04-25 11:41:58');
INSERT INTO `user` VALUES ('6', 'dd', 'ff', '18193412355', 'df', 'fds', 'dsf', 'sdf', '1', null, null);
