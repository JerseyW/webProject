/*
 Navicat MySQL Data Transfer

 Source Server         : mysql80
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : ssm_bbms_admin

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 22/02/2022 20:58:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user_sub
-- ----------------------------
DROP TABLE IF EXISTS `t_user_sub`;
CREATE TABLE `t_user_sub`  (
  `sId` int NOT NULL AUTO_INCREMENT,
  `uId` int NULL DEFAULT NULL,
  `bId` int NULL DEFAULT NULL,
  `subDateTime` datetime NULL DEFAULT NULL,
  `returnDateTime` datetime NULL DEFAULT NULL,
  `bookType` int NULL DEFAULT NULL,
  `returntype` int NULL DEFAULT NULL,
  `subNum` int NULL DEFAULT NULL,
  PRIMARY KEY (`sId`) USING BTREE,
  INDEX `bookId`(`bId`) USING BTREE,
  INDEX `userId`(`uId`) USING BTREE,
  CONSTRAINT `t_user_sub_ibfk_1` FOREIGN KEY (`bId`) REFERENCES `ssm_bbms_book`.`bookmanage` (`bId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_user_sub_ibfk_2` FOREIGN KEY (`uId`) REFERENCES `tbuser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_sub
-- ----------------------------
INSERT INTO `t_user_sub` VALUES (61, 21, 5, '2019-12-11 08:32:24', '2019-12-11 08:32:36', 0, 3, 1);
INSERT INTO `t_user_sub` VALUES (62, 21, 5, '2022-02-14 18:26:18', '2022-02-25 18:26:22', 1, 0, 1);
INSERT INTO `t_user_sub` VALUES (63, 23, 7, '2022-02-02 18:26:46', '2022-02-26 18:26:50', 1, 2, 2);
INSERT INTO `t_user_sub` VALUES (64, 24, 8, '2022-02-15 18:27:13', '2022-02-25 18:27:16', 1, 0, 1);
INSERT INTO `t_user_sub` VALUES (69, 25, 5, '2022-02-22 19:35:12', '2022-02-23 12:00:00', 1, 2, 1);
INSERT INTO `t_user_sub` VALUES (70, 25, 5, '2022-02-22 19:36:38', '2022-02-23 12:00:00', 1, 2, 1);
INSERT INTO `t_user_sub` VALUES (71, 23, 6, '2022-02-22 20:43:05', '2022-02-23 12:00:00', 1, 0, 1);
INSERT INTO `t_user_sub` VALUES (72, 25, 6, '2022-02-22 20:43:52', '2022-02-24 13:00:00', 1, 0, 1);

-- ----------------------------
-- Table structure for tbuser
-- ----------------------------
DROP TABLE IF EXISTS `tbuser`;
CREATE TABLE `tbuser`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `sex` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbuser
-- ----------------------------
INSERT INTO `tbuser` VALUES (21, 'leo1', 'leo', '20121212', 'leo', 23, 0);
INSERT INTO `tbuser` VALUES (22, 'jersey', 'jersey', '123456', '杰西', 28, 1);
INSERT INTO `tbuser` VALUES (23, 'jrsmith12', 'smith', '859698', '史密斯', 25, 0);
INSERT INTO `tbuser` VALUES (24, 'defe', 'yyyyy', 'yyyyyy', 'gfrgth', 85, 1);
INSERT INTO `tbuser` VALUES (25, 'ben', 'ben', '20121212', '本', 45, 1);

SET FOREIGN_KEY_CHECKS = 1;
