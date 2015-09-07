# MySQL-Front 5.1  (Build 3.0)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: 115.28.4.139    Database: weizhang
# ------------------------------------------------------
# Server version 5.5.34-0ubuntu0.12.04.1

DROP DATABASE IF EXISTS `weizhang`;
CREATE DATABASE `weizhang` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `weizhang`;

#
# Source for table t_query_history
#

DROP TABLE IF EXISTS `t_query_history`;
CREATE TABLE `t_query_history` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键id',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `carno` varchar(20) NOT NULL DEFAULT '' COMMENT '车牌号',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `engineno` varchar(50) DEFAULT NULL COMMENT '发动机号',
  `classno` varchar(50) DEFAULT NULL COMMENT '车架号',
  `carmodel` varchar(100) DEFAULT NULL COMMENT '车型',
  `cartype` varchar(20) DEFAULT NULL COMMENT '车牌类型',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史查询记录';


#
# Source for table t_subscribe
#

DROP TABLE IF EXISTS `t_subscribe`;
CREATE TABLE `t_subscribe` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键id',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `carno` varchar(20) NOT NULL DEFAULT '' COMMENT '车牌号',
  `city` varchar(20) DEFAULT NULL COMMENT '城市编码',
  `engineno` varchar(50) DEFAULT NULL COMMENT '发动机号',
  `classno` varchar(50) DEFAULT NULL COMMENT '车架号',
  `carmodel` varchar(100) DEFAULT NULL COMMENT '车型',
  `cartype` varchar(20) DEFAULT NULL COMMENT '车牌类型',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `lastsend_time` datetime DEFAULT NULL COMMENT '最后发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订阅';

#
# Dumping data for table t_subscribe
#
LOCK TABLES `t_subscribe` WRITE;
/*!40000 ALTER TABLE `t_subscribe` DISABLE KEYS */;

/*!40000 ALTER TABLE `t_subscribe` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
