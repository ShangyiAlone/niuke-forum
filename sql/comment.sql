/*
Navicat MySQL Data Transfer

Source Server         : xxx
Source Server Version : 50736
Source Host           : localhost:3306
Source Database       : community

Target Server Type    : MYSQL
Target Server Version : 50736
File Encoding         : 65001

Date: 2023-01-08 13:19:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `entity_type` int(11) DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `target_id` int(11) DEFAULT NULL,
  `content` text,
  `status` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`),
  KEY `index_entity_id` (`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=235 DEFAULT CHARSET=utf8;
