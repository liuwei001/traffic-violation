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
  `city` varchar(20) DEFAULT NULL COMMENT '城市编码',
  `engineno` varchar(50) DEFAULT NULL COMMENT '发动机号',
  `classno` varchar(50) DEFAULT NULL COMMENT '车架号',
  `carmodel` varchar(100) DEFAULT NULL COMMENT '车型',
  `cartype` varchar(20) DEFAULT NULL COMMENT '车牌类型',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史查询记录';


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