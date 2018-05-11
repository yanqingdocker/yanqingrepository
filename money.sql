/*
Navicat MySQL Data Transfer

Source Server         : 47.104.233.0
Source Server Version : 50556
Source Host           : 47.104.233.0:3306
Source Database       : money

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-05-11 10:27:29
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
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority
-- ----------------------------

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
  `blance` double DEFAULT '0',
  `paypwd` varchar(255) DEFAULT NULL,
  `checkcode` varchar(255) DEFAULT NULL,
  `exception` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of count
-- ----------------------------
INSERT INTO `count` VALUES ('28', '688485602360', 'CNY', '2018-05-04 13:42:27', '10', '1', '0', '670b14728ad9902aecba32e22fa4f6bd', '1436177081', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('13', 'TITLE', 'AAAAAAAAAAAAAAAAAA', '2018-04-28 15:29:15', '胡彦清', '0', '0', '6');
INSERT INTO `message` VALUES ('14', 'TITLE', 'AAAAAAAAAAAAAAAAAA', '2018-04-28 15:29:15', 'dd', '1', '0', '4');
INSERT INTO `message` VALUES ('15', 'title', 'aaaaaaaaaaaaaaaaaaaaaaa', '2018-05-03 11:47:48', '胡彦清', '0', '0', '4');
INSERT INTO `message` VALUES ('16', 'title', 'aaaaaaaaaaaaaaaaaaaaaaa', '2018-05-03 11:47:48', '胡彦清', '1', '0', '7');

-- ----------------------------
-- Table structure for muser
-- ----------------------------
DROP TABLE IF EXISTS `muser`;
CREATE TABLE `muser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operalog
-- ----------------------------
INSERT INTO `operalog` VALUES ('4', '13246465', 'CNY', '10', '充值', '2018-05-09 16:59:19', 'yanqing', '1', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for roleauth
-- ----------------------------
DROP TABLE IF EXISTS `roleauth`;
CREATE TABLE `roleauth` (
  `id` int(11) NOT NULL,
  `roleid` int(11) DEFAULT NULL,
  `authorityid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('10', '胡彦清', '670b14728ad9902aecba32e22fa4f6bd', '18193412366', '863643344@qq.com', '62282519940820121X', '2018-5-4', '河南省信阳市罗山县a', '1', '2018-05-04 13:41:45', '2018-05-04 13:50:00');

-- ----------------------------
-- Table structure for userrole
-- ----------------------------
DROP TABLE IF EXISTS `userrole`;
CREATE TABLE `userrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `roleid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userrole
-- ----------------------------
