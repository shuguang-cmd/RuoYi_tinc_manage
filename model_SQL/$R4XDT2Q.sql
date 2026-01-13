# Host: localhost  (Version: 5.5.5-10.4.32-MariaDB)
# Date: 2026-01-13 17:06:42
# Generator: MySQL-Front 5.3  (Build 4.234)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "tinc_node_mange"
#

DROP TABLE IF EXISTS `tinc_node_mange`;
CREATE TABLE `tinc_node_mange` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ids',
  `use_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户',
  `table_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '设备ID',
  `server_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '接入服务器',
  `network_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '所属内网',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `node_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '节点名称',
  `network_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '内网ip',
  `explantion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `node_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '节点状态',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '配置状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tinc节点集群管理';

#
# Data for table "tinc_node_mange"
#

INSERT INTO `tinc_node_mange` VALUES (3,'','','test1','1','qwqw','qwqw','192.172.1.122','1212','2025-12-30 22:49:47','',''),(4,'','','test1','1','1234567','test1','192.172.1.221','221test','2025-12-31 11:12:02','',''),(5,'','','test1','1','12345','name_test','192.172.1.','1213','2025-12-31 12:07:02','',''),(6,'admin','','test1','11','121212','node_test','192.172.1.232','dfdsf','2025-12-31 12:11:46','',''),(7,'admin','','test1','1','121212','121212','192.172.1.121','dfsdfsdf','2025-12-31 12:57:51','0','0'),(8,'admin','','test1','shell','123456','shellTest','192.172.1.','测试','2026-01-07 19:32:01','0','0'),(9,'admin','','test2','segment','123456','sun666','192.172.100.3','你好','2026-01-10 22:40:24','0','0'),(10,'admin','','test3','sun','1234567','sun_node','172.123.131.6','dfds ','2026-01-12 20:52:23','0','0'),(11,'admin','','测试','TESTFRS','121212','FSFE','123.111.16.6','DDF','2026-01-13 16:09:29','0','0');
