# Host: localhost  (Version: 5.5.5-10.4.32-MariaDB)
# Date: 2026-01-13 17:07:10
# Generator: MySQL-Front 5.3  (Build 4.234)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "mange_server"
#

DROP TABLE IF EXISTS `mange_server`;
CREATE TABLE `mange_server` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `server_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '服务器名字',
  `server_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '服务器ip',
  `start_Interat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '起始网段',
  `end_Interat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '终止网段',
  `start_post` int(11) NOT NULL DEFAULT 0 COMMENT '起始端口',
  `end_post` int(11) NOT NULL DEFAULT 0 COMMENT '终止端口',
  `election` varchar(255) DEFAULT NULL COMMENT '备注',
  `number` int(11) NOT NULL DEFAULT 0 COMMENT '内网数量',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `uk_server_name_ip` (`server_name`,`server_ip`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务器集群管理';

#
# Data for table "mange_server"
#

INSERT INTO `mange_server` VALUES (1,'test1','192.168.1.1','192.172.1','192.172.100',655,755,'测试1',0,1),(2,'test2','192.168.1.2','192.172.1','192.172.100',655,755,'测试2',0,1),(5,'test3','192.168.1.12','172.123.122','172.123.133',655,755,'test3',0,1),(6,'test_vpn','8.8.8.8','10.10.10','10.10.11',6666,7777,'vpn',0,1),(7,'test_one','3.3.3.3','123.11.2','123.11.2',6666,8888,'test',0,1),(8,'测试','4.4.4.4','123.111.1','123.111.111',6666,8888,'test',0,1);
