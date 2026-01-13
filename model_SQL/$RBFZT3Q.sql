# Host: localhost  (Version: 5.5.5-10.4.32-MariaDB)
# Date: 2026-01-13 17:06:57
# Generator: MySQL-Front 5.3  (Build 4.234)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "tinc_network_mange"
#

DROP TABLE IF EXISTS `tinc_network_mange`;
CREATE TABLE `tinc_network_mange` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ids',
  `root_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户',
  `server_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '接入服务器',
  `network_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '内网名称',
  `create_time` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '端口',
  `segment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '网段',
  `nodes` int(11) NOT NULL DEFAULT 0 COMMENT '节点数量',
  `network_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '内网状态',
  `explanation` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tinc内网集群管理';

#
# Data for table "tinc_network_mange"
#

INSERT INTO `tinc_network_mange` VALUES (10,'sun','test1','ee','2025-12-20 18:05:07','657','192.172.2',0,'在线','eee'),(12,'admin','test2','wwww','2025-12-20 19:53:23','656','192.172.3',0,'在线','wwww'),(13,'sun','test2','ddd','2025-12-20 19:58:58','660','192.172.5',0,'在线','ded'),(14,'admin','test1','shell','2026-01-07 19:30:23','655','192.172.1',0,'在线','测试'),(15,'admin','test_vpn','prod_vpn','2026-01-07 22:51:30','6677','10.10.10',0,'正常运行中','vpn'),(16,'admin','test2','sun_wer','2026-01-09 17:09:15','666','192.172.40',0,'正常运行中','sun_wer'),(20,'admin','test2','segment','2026-01-10 20:43:30','707','192.172.100',0,'正常运行中','网段完整性测试'),(21,'admin','test3','sun','2026-01-12 20:50:55','701','172.123.131',0,'正常运行中','dfdf'),(22,'admin','测试','TESTFRS','2026-01-13 16:08:58','6668','123.111.16',0,'正常运行中','SESE');
