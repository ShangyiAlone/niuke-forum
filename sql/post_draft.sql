/*
Navicat MySQL Data Transfer

Source Server         : xxx
Source Server Version : 50736
Source Host           : localhost:3306
Source Database       : community

Target Server Type    : MYSQL
Target Server Version : 50736
File Encoding         : 65001

Date: 2023-01-05 18:14:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for post_draft
-- ----------------------------
DROP TABLE IF EXISTS `post_draft`;
CREATE TABLE `post_draft` (
  `user_id` varchar(45) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
