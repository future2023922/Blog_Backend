/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 80023
Source Host           : localhost:3306
Source Database       : contestForBlog

Target Server Type    : MYSQL
Target Server Version : 80023
File Encoding         : 65001

Date: 2023-10-28 16:19:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` char(19) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `author_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章作者',
  `category_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章分类',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_times` datetime DEFAULT NULL,
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除',
  `version` int DEFAULT '0' COMMENT '版本',
  `copyright` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `category_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类id',
  `category_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类标题',
  `author_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` char(19) NOT NULL,
  `blog_id` char(19) NOT NULL COMMENT '评论所属的文章',
  `user_id` char(19) NOT NULL COMMENT '评论者的id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论的内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `parent_comment_Id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论的父id',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` char(19) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` char(19) NOT NULL COMMENT '用户的唯一id',
  `username` varchar(60) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '用户密码',
  `role_id` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色id',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `sign` varchar(200) DEFAULT NULL COMMENT '用户的个性签名',
  `avatar` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户头像链接',
  `is_deleted` tinyint DEFAULT '0',
  `version` int DEFAULT '0',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
