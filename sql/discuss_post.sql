/*
Navicat MySQL Data Transfer

Source Server         : xxx
Source Server Version : 50736
Source Host           : localhost:3306
Source Database       : community

Target Server Type    : MYSQL
Target Server Version : 50736
File Encoding         : 65001

Date: 2023-01-08 13:18:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for discuss_post
-- ----------------------------
DROP TABLE IF EXISTS `discuss_post`;
CREATE TABLE `discuss_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4,
  `type` int(11) DEFAULT NULL COMMENT '0-普通; 1-置顶;',
  `status` int(11) DEFAULT NULL COMMENT '0-正常; 1-精华; 2-拉黑;',
  `create_time` timestamp NULL DEFAULT NULL,
  `comment_count` int(11) DEFAULT NULL,
  `score` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=303 DEFAULT CHARSET=utf8;
