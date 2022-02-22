/*
 Navicat MySQL Data Transfer

 Source Server         : mysql80
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : ssm_bbms_book

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 22/02/2022 20:59:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bookmanage
-- ----------------------------
DROP TABLE IF EXISTS `bookmanage`;
CREATE TABLE `bookmanage`  (
  `bId` int NOT NULL AUTO_INCREMENT,
  `bookName` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `inventory` int NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`bId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bookmanage
-- ----------------------------
INSERT INTO `bookmanage` VALUES (5, 'mysql从入门到删库跑路', 'ben', 19, 69.00);
INSERT INTO `bookmanage` VALUES (6, 'java入门到精通', 'leo', 28, 120.00);
INSERT INTO `bookmanage` VALUES (7, '数据结构', 'ceo', 21, 52.00);
INSERT INTO `bookmanage` VALUES (8, 'vue3', '尤雨溪', 85, 120.00);
INSERT INTO `bookmanage` VALUES (9, 'mysql', '李尅', 0, 85.00);
INSERT INTO `bookmanage` VALUES (10, 'linux', 'deo', 56, 35.00);

SET FOREIGN_KEY_CHECKS = 1;
