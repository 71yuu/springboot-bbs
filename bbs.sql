/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : bbs

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 20/12/2020 20:50:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin');

-- ----------------------------
-- Table structure for cat
-- ----------------------------
DROP TABLE IF EXISTS `cat`;
CREATE TABLE `cat`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `cat_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` int(11) NULL DEFAULT 0 COMMENT '删除标志（0,1）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cat
-- ----------------------------
INSERT INTO `cat` VALUES (1, '学习交流', NULL, 0);
INSERT INTO `cat` VALUES (2, '校内趣闻', NULL, 0);
INSERT INTO `cat` VALUES (3, '闲置转卖', NULL, 1);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `question_id` int(11) NULL DEFAULT NULL COMMENT '问题ID',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父评论ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` int(11) NULL DEFAULT 0 COMMENT '删除标记（0：正常，1：已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (42, 13, 0, 13, '123123', '2020-11-25 15:55:38', 1);
INSERT INTO `comment` VALUES (43, 13, 42, 13, '123123', '2020-11-25 15:55:41', 0);
INSERT INTO `comment` VALUES (44, 14, 0, 13, '12312', '2020-12-15 16:05:09', 0);
INSERT INTO `comment` VALUES (45, 14, 44, 13, '123123', '2020-12-15 16:06:15', 0);
INSERT INTO `comment` VALUES (46, 14, 44, 13, 'fadfsa', '2020-12-15 16:18:57', 0);
INSERT INTO `comment` VALUES (47, 14, 0, 13, '方法撒大多数', '2020-12-15 16:19:01', 0);
INSERT INTO `comment` VALUES (48, 24, 0, 13, '123123', '2020-12-16 18:18:14', 0);
INSERT INTO `comment` VALUES (49, 24, 0, 16, '123123', '2020-12-16 18:18:36', 0);
INSERT INTO `comment` VALUES (50, 24, 0, 16, 'fsdas ', '2020-12-16 18:18:53', 0);
INSERT INTO `comment` VALUES (51, 24, 0, 16, '312312', '2020-12-16 18:18:59', 0);
INSERT INTO `comment` VALUES (52, 24, 0, 16, 'fsadf', '2020-12-16 18:19:02', 0);
INSERT INTO `comment` VALUES (53, 24, 0, 16, '发士大夫', '2020-12-16 18:19:05', 0);
INSERT INTO `comment` VALUES (54, 24, 0, 16, 'fdsdasf', '2020-12-16 18:19:07', 0);
INSERT INTO `comment` VALUES (55, 24, 0, 16, 'fsadfads', '2020-12-16 18:19:10', 0);
INSERT INTO `comment` VALUES (56, 24, 0, 16, 'sdafasdf', '2020-12-16 18:19:12', 0);
INSERT INTO `comment` VALUES (57, 24, 0, 16, '13123', '2020-12-16 18:19:41', 0);
INSERT INTO `comment` VALUES (58, 24, 0, 16, 'fsdaf', '2020-12-16 18:19:43', 0);
INSERT INTO `comment` VALUES (59, 24, 0, 16, '132312', '2020-12-16 18:19:46', 0);
INSERT INTO `comment` VALUES (60, 24, 0, 16, '123123', '2020-12-16 18:19:48', 0);
INSERT INTO `comment` VALUES (61, 24, 0, 16, '12313', '2020-12-16 18:19:54', 0);
INSERT INTO `comment` VALUES (62, 24, 0, 16, '123123', '2020-12-16 18:21:38', 0);
INSERT INTO `comment` VALUES (63, 24, 0, 16, '21312', '2020-12-16 18:21:58', 0);
INSERT INTO `comment` VALUES (64, 24, 0, 16, '12312', '2020-12-17 11:29:14', 0);
INSERT INTO `comment` VALUES (65, 24, 0, 16, 'fasdf', '2020-12-17 11:29:22', 0);
INSERT INTO `comment` VALUES (66, 24, 65, 16, '123123', '2020-12-17 11:29:31', 0);

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '反馈ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '反馈内容',
  `status` int(11) NULL DEFAULT 0 COMMENT '是否处理（0：未处理，1：已处理）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `process_time` datetime(0) NULL DEFAULT NULL COMMENT '处理时间',
  `reply` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员回复',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (17, 13, '123123', 1, '2020-11-25 15:55:58', '2020-11-25 15:56:06', 'fsdafa方法去玩儿');

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '等级ID',
  `grade` int(10) NULL DEFAULT NULL COMMENT '等级',
  `score` int(10) NULL DEFAULT NULL COMMENT '所需积分',
  `icon_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '等级图标url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (1, 1, 0, 'http://yuu-bbs.oss-cn-beijing.aliyuncs.com/293839bb-ff62-4d6a-b0b5-3b345ff30f4f.png');
INSERT INTO `grade` VALUES (2, 2, 400, 'http://yuu-bbs.oss-cn-beijing.aliyuncs.com/881070d0-1a1a-4c55-bb47-f0e467f9b1ac.png');
INSERT INTO `grade` VALUES (3, 3, 800, 'http://yuu-bbs.oss-cn-beijing.aliyuncs.com/237e4cef-f883-41aa-a049-5905fb43f451.png');
INSERT INTO `grade` VALUES (4, 4, 1600, 'http://yuu-bbs.oss-cn-beijing.aliyuncs.com/701ce264-224b-4fce-bd75-66365d7d27c5.png');
INSERT INTO `grade` VALUES (5, 5, 4500, 'http://yuu-bbs.oss-cn-beijing.aliyuncs.com/6103c4e4-8892-4037-a9bb-bf5a4b9b1cba.png');
INSERT INTO `grade` VALUES (6, 6, 9000, 'http://yuu-bbs.oss-cn-beijing.aliyuncs.com/e2bc38ed-befc-4973-883e-d2f44ed7492d.png');
INSERT INTO `grade` VALUES (7, 7, 25000, 'http://yuu-bbs.oss-cn-beijing.aliyuncs.com/095a4873-f74c-4d81-b74e-6ccf3fd91b17.png');
INSERT INTO `grade` VALUES (8, 8, 50000, 'http://yuu-bbs.oss-cn-beijing.aliyuncs.com/db8726c3-22d9-40da-9937-70a187a7bf3a.png');

-- ----------------------------
-- Table structure for integral_rule
-- ----------------------------
DROP TABLE IF EXISTS `integral_rule`;
CREATE TABLE `integral_rule`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '积分规则ID',
  `rule_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则名称',
  `integral` int(10) NULL DEFAULT NULL COMMENT '积分',
  `daily_limit` int(10) NULL DEFAULT NULL COMMENT '每日积分增加限制条数',
  `rule_flag` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则查询标识',
  `explain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of integral_rule
-- ----------------------------
INSERT INTO `integral_rule` VALUES (1, '发布问题', 50, 10, 'FBWT', '发布一个问题，审核通过后，获得50积分，每日限制10次');
INSERT INTO `integral_rule` VALUES (2, '问题收到评论', 10, 10, 'WTSDPL', '文章收到评论，获得10积分，每日限制10次');
INSERT INTO `integral_rule` VALUES (3, '发表评论', 10, 10, 'FBPL', '发表文章评论，获取10积分，每日限制10次（自己给自己评论不获得积分）');
INSERT INTO `integral_rule` VALUES (4, '首次签到', 50, 1, 'SCQD', '首次签到获得50积分');
INSERT INTO `integral_rule` VALUES (5, '签到', 10, 1, 'QD', '普通签到获得10积分');
INSERT INTO `integral_rule` VALUES (6, '连续签到', 20, 1, 'LXQD', '连续7天以上签到，每日获得20积分');

-- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
  `like_type` int(11) NULL DEFAULT NULL COMMENT '点赞类型（0：问题，1：评论）',
  `like_id` int(11) NULL DEFAULT NULL COMMENT '点赞类型ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of like
-- ----------------------------
INSERT INTO `like` VALUES (51, 0, 13, 13, '2020-11-25 15:55:34');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `notice` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知内容',
  `notice_time` datetime(0) NULL DEFAULT NULL COMMENT '通知时间',
  `del_flag` int(10) NULL DEFAULT 0 COMMENT '删除标记（0：正常，1：已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '123防守打法', '2020-12-18 16:51:54', 0);
INSERT INTO `notice` VALUES (2, NULL, '2020-12-20 18:53:28', NULL);
INSERT INTO `notice` VALUES (3, 'V型正常v', '2020-12-20 18:54:38', 1);

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `notifer` int(11) NULL DEFAULT NULL COMMENT '发布者',
  `receiver` int(11) NULL DEFAULT NULL COMMENT '接受者',
  `type` int(11) NULL DEFAULT NULL COMMENT '通知类型（0：点赞问题，1：点赞评论，2：评论文章，3：回复评论）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '通知时间',
  `service_id` int(11) NULL DEFAULT NULL COMMENT '业务ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES (43, NULL, 13, 4, '2020-11-25 15:55:00', 13);
INSERT INTO `notification` VALUES (44, NULL, 13, 4, '2020-11-25 15:55:21', 13);
INSERT INTO `notification` VALUES (45, 13, 13, 0, '2020-11-25 15:55:35', 13);
INSERT INTO `notification` VALUES (46, 13, 13, 1, '2020-11-25 15:55:38', 13);
INSERT INTO `notification` VALUES (47, 13, 13, 3, '2020-11-25 15:55:41', 42);
INSERT INTO `notification` VALUES (48, NULL, 13, 5, '2020-11-25 15:56:06', 17);
INSERT INTO `notification` VALUES (49, NULL, 13, 6, '2020-11-25 16:11:33', 14);
INSERT INTO `notification` VALUES (50, NULL, 13, 4, '2020-11-25 16:13:02', 14);
INSERT INTO `notification` VALUES (51, 13, 13, 1, '2020-12-15 16:05:08', 14);
INSERT INTO `notification` VALUES (52, 13, 13, 3, '2020-12-15 16:06:14', 44);
INSERT INTO `notification` VALUES (53, 13, 13, 3, '2020-12-15 16:18:57', 44);
INSERT INTO `notification` VALUES (54, 13, 13, 1, '2020-12-15 16:19:01', 14);
INSERT INTO `notification` VALUES (82, 16, 13, 1, '2020-12-16 18:21:58', 24);
INSERT INTO `notification` VALUES (83, 16, 13, 1, '2020-12-17 11:29:14', 24);
INSERT INTO `notification` VALUES (84, 16, 13, 1, '2020-12-17 11:29:22', 24);
INSERT INTO `notification` VALUES (85, 16, 16, 3, '2020-12-17 11:29:31', 65);
INSERT INTO `notification` VALUES (86, NULL, 13, 4, '2020-12-18 17:33:34', 23);
INSERT INTO `notification` VALUES (87, NULL, 13, 4, '2020-12-18 17:34:21', 20);
INSERT INTO `notification` VALUES (88, NULL, 13, 4, '2020-12-18 17:36:38', 23);
INSERT INTO `notification` VALUES (89, NULL, 13, 4, '2020-12-18 17:36:41', 20);

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题 ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题标题',
  `text` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '问题文本',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '问题内容',
  `creator` int(11) NULL DEFAULT NULL COMMENT '发布者ID',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '浏览次数',
  `del_flag` int(11) NULL DEFAULT 0 COMMENT '标记删除（0：正常，1：已删除）',
  `state` tinyint(1) NULL DEFAULT NULL COMMENT '是否审核（0：未审核，1：审核通过，2：审核失败）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `cat_id` int(11) NULL DEFAULT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (13, '任正非-华为的精神领袖123123', '发斯蒂芬', '<p>12312312312</p><p><img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/32626d41-3be5-49ac-8d7e-e0e1d2209c8d.jpg\" style=\"max-width:100%;\"><br></p><p><img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/d6b106dc-a19c-42e1-8396-1a438d7755c9.jpeg\" style=\"max-width:100%;\"><br></p><p><br></p>', 13, 6, 1, 1, '2020-11-25 15:54:47', NULL);
INSERT INTO `question` VALUES (14, '从澳门回归20周年的发展看一国两制优势', '发的发大水', '<p>12312</p><p>发发送到</p>', 13, 63, 0, 1, '2020-11-25 16:11:24', NULL);
INSERT INTO `question` VALUES (18, '123123', '123123', '<p>123123</p><p><br></p>', 13, 1, 0, 1, '2020-12-16 15:43:22', 1);
INSERT INTO `question` VALUES (19, '测试图片', '发的发生的', '<p>1323<img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/22c6ef70-01f0-4dbd-b49b-a9e81e0f46ab.jpg\" style=\"max-width: 100%;\"></p><p>反对大师傅撒旦</p><p><img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/e4351fde-7669-40fa-a0d9-ef41d0a5778d.jpg\" style=\"max-width:100%;\"><br></p><p>3123</p><p><img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/ad8e294f-8f3a-443a-ac16-68008f80ed14.jpg\" style=\"max-width:100%;\"><br></p><p>方法是多少<img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/60f89c21-d184-4c1e-b19e-21e5f921e909.jpg\" style=\"max-width: 100%;\"></p><p><img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/05d87f46-fcc9-4298-9a0e-a078b5a9a2c4.png\" style=\"max-width:100%;\"><br></p><p>fasdfadsfdasf飞洒多发点是</p><p><br></p>', 13, 7, 0, 1, '2020-12-16 15:52:16', 1);
INSERT INTO `question` VALUES (20, 'fasdf', '123123大师傅卡戴珊开了房间法撒旦立刻方面卡的三鹿奶粉考虑那里麦克风乐山大佛卡了 尽快落地封杀了开发啊发士大夫撒旦', '<p>123123<img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/e429986a-b289-4c77-81ad-c8da82fd5e06.jpg\" style=\"max-width: 100%;\"></p><p>大师傅卡戴珊开了房间法撒旦立刻方面卡的三鹿奶粉考虑那里麦克风乐山大佛卡了 尽快落地封杀了开发</p><p>啊发士大夫撒旦</p><p><br></p>', 13, 7, 0, 1, '2020-12-16 16:49:46', 3);
INSERT INTO `question` VALUES (21, 'fff ', 'sdfsad发生的v撒旦发大师傅卡戴珊开了房间', '<p>sdfsad发生的v撒旦</p><p><img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/3e1d4b60-a5cb-44d7-b199-0cab21fead42.jpg\" style=\"max-width:100%;\"><br></p><p>发大师傅卡戴珊开了房间</p>', 13, 0, 0, 1, '2020-12-16 16:51:50', 1);
INSERT INTO `question` VALUES (22, '任正非-华为的精神领袖', 'JDK 8 由一个新增的特性就是引入了新的时间和日期 API，可以更方便的处理日期和时间。LocalDateLocalDate&nbsp;类用来表示一个具体的日期，但不包含具体时间，可以使用&nbsp;LocalDate&nbsp;的静态方法&nbsp;of()&nbsp;来创建一个实例。LocalDate&nbsp;也包含一些方法用来获取年份、月份、天、星期几等：LocalDate localDate = LocalDate.of(2020, 9, 15);     // 初始化一个日期：2020-09-15\r\nint year = localDate.getYear();                     // 年份：2020\r\nMonth month = localDate.getMonth();                 // 月份：SEPTEMBER\r\nint dayOfMonth = localDate.getDayOfMonth();         // 月份中的第几天：15\r\nDayOfWeek dayOfWeek = localDate.getDayOfWeek();     // 一周的第几天：TUESDAY\r\nint length = localDate.lengthOfMonth();             // 月份的天数：30\r\nboolean leapYear = localDate.isLeapYear();          // 是否为闰年：false\r\n1234567也可以调用静态方法&nbsp;now()&nbsp;来获取当前日期：LocalDate now = LocalDate.now();\r\n1LocalDateTimeLocalDateTime&nbsp;类是&nbsp;LocalDate&nbsp;和&nbsp;LocalTime&nbsp;的结合体，可以通过&nbsp;of()&nbsp;方法创建，也可以通过&nbsp;LocalDate&nbsp;的&nbsp;atTime()&nbsp;方法或&nbsp;LocalTime&nbsp;的&nbsp;atDate()&nbsp;方法将&nbsp;LocalDate&nbsp;或&nbsp;LocalTime&nbsp;合并成一个&nbsp;LocalDateTime：LocalDateTime localDateTime = LocalDateTime.of(2020, Month.JUNE, 15, 22, 25, 54); // 2020-06-15T22:25:54\r\n\r\nLocalDate localDate = LocalDate.now(); // 2020-09-15\r\nLocalTime localTime = LocalTime.now(); // 22:31:15.210\r\nLocalDateTime localDateTime = localDate.atTime(localTime); // 2020-09-15T22:31:15.210\r\nLocalDateTime localTimeDate = localTime.atDate(localDate); // 2020-09-15T22:31:15.210\r\n\r\nLocalDate localDateTime = localDateTime.toLocalDate(); // 2020-09-15\r\nLocalTime localDateTime = localDateTime.toLocalTime(); // 22:34:12.267\r\n123456789InstantInstant&nbsp;用来表示时间戳，与&nbsp;System.currentTimeMillis()&nbsp;类似，不过&nbsp;Instant&nbsp;可以精确到纳秒，System.currentTimeMillis()&nbsp;方法只精确到毫秒。可以通过&nbsp;now()&nbsp;方法创建，也可以通过&nbsp;ofEpochSecond&nbsp;方法创建。Instant&nbsp;有两个属性&nbsp;senconds&nbsp;和&nbsp;nanos&nbsp;可以分别获取秒和毫秒的时间戳。Instant instant = Instant.now(); // 2020-09-15T14:40:47.910Z\r\nInstant.ofEpochSecond(120, 100000); // 获取从 1970-01-01 00:00:00 后 120 秒的 10 万纳秒的时刻\r\n12DurationDuration&nbsp;的内部实现&nbsp;Instant&nbsp;类似，也包含&nbsp;seconds&nbsp;和&nbsp;nanos&nbsp;两部分，不同的是&nbsp;Duration&nbsp;表示的是一个时间段，所以&nbsp;Duration&nbsp;类中没有&nbsp;now()&nbsp;方法，可以通过&nbsp;Duration.between()&nbsp;方法创建&nbsp;Duration&nbsp;对象：', '<p>JDK 8 由一个新增的特性就是引入了新的时间和日期 API，可以更方便的处理日期和时间。</p><h2><a name=\"t0\"></a><a name=\"t0\"></a><a id=\"LocalDate_1\"></a>LocalDate</h2><p><code>LocalDate</code>&nbsp;类用来表示一个具体的日期，但不包含具体时间，可以使用&nbsp;<code>LocalDate</code>&nbsp;的静态方法&nbsp;<code>of()</code>&nbsp;来创建一个实例。<code>LocalDate</code>&nbsp;也包含一些方法用来获取年份、月份、天、星期几等：</p><pre><code onclick=\"mdcp.copyCode(event)\">LocalDate localDate = LocalDate.of(2020, 9, 15);     // 初始化一个日期：2020-09-15\r\nint year = localDate.getYear();                     // 年份：2020\r\nMonth month = localDate.getMonth();                 // 月份：SEPTEMBER\r\nint dayOfMonth = localDate.getDayOfMonth();         // 月份中的第几天：15\r\nDayOfWeek dayOfWeek = localDate.getDayOfWeek();     // 一周的第几天：TUESDAY\r\nint length = localDate.lengthOfMonth();             // 月份的天数：30\r\nboolean leapYear = localDate.isLeapYear();          // 是否为闰年：false\r\n</code><ul><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li>6</li><li>7</li></ul></pre><p>也可以调用静态方法&nbsp;<code>now()</code>&nbsp;来获取当前日期：</p><pre><code onclick=\"mdcp.copyCode(event)\">LocalDate now = LocalDate.now();\r\n</code><ul><li>1</li></ul></pre><h2><a name=\"t1\"></a><a name=\"t1\"></a><a id=\"LocalDateTime_17\"></a>LocalDateTime</h2><p><code>LocalDateTime</code>&nbsp;类是&nbsp;<code>LocalDate</code>&nbsp;和&nbsp;<code>LocalTime</code>&nbsp;的结合体，可以通过&nbsp;<code>of()</code>&nbsp;方法创建，也可以通过&nbsp;<code>LocalDate</code>&nbsp;的&nbsp;<code>atTime()</code>&nbsp;方法或&nbsp;<code>LocalTime</code>&nbsp;的&nbsp;<code>atDate()</code>&nbsp;方法将&nbsp;<code>LocalDate</code>&nbsp;或&nbsp;<code>LocalTime</code>&nbsp;合并成一个&nbsp;<code>LocalDateTime</code>：</p><pre><code onclick=\"mdcp.copyCode(event)\">LocalDateTime localDateTime = LocalDateTime.of(2020, Month.JUNE, 15, 22, 25, 54); // 2020-06-15T22:25:54\r\n\r\nLocalDate localDate = LocalDate.now(); // 2020-09-15\r\nLocalTime localTime = LocalTime.now(); // 22:31:15.210\r\nLocalDateTime localDateTime = localDate.atTime(localTime); // 2020-09-15T22:31:15.210\r\nLocalDateTime localTimeDate = localTime.atDate(localDate); // 2020-09-15T22:31:15.210\r\n\r\nLocalDate localDateTime = localDateTime.toLocalDate(); // 2020-09-15\r\nLocalTime localDateTime = localDateTime.toLocalTime(); // 22:34:12.267\r\n</code><ul><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li>6</li><li>7</li><li>8</li><li>9</li></ul></pre><h2><a name=\"t2\"></a><a name=\"t2\"></a><a id=\"Instant_31\"></a>Instant</h2><p><code>Instant</code>&nbsp;用来表示时间戳，与&nbsp;<code>System.currentTimeMillis()</code>&nbsp;类似，不过&nbsp;<code>Instant</code>&nbsp;可以精确到纳秒，<code>System.currentTimeMillis()</code>&nbsp;方法只精确到毫秒。可以通过&nbsp;<code>now()</code>&nbsp;方法创建，也可以通过&nbsp;<code>ofEpochSecond</code>&nbsp;方法创建。<code>Instant</code>&nbsp;有两个属性&nbsp;<code>senconds</code>&nbsp;和&nbsp;<code>nanos</code>&nbsp;可以分别获取秒和毫秒的时间戳。</p><pre><code onclick=\"mdcp.copyCode(event)\">Instant instant = Instant.now(); // 2020-09-15T14:40:47.910Z\r\nInstant.ofEpochSecond(120, 100000); // 获取从 1970-01-01 00:00:00 后 120 秒的 10 万纳秒的时刻\r\n</code><ul><li>1</li><li>2</li></ul></pre><h2><a name=\"t3\"></a><a name=\"t3\"></a><a id=\"Duration_38\"></a>Duration</h2><p><code>Duration</code>&nbsp;的内部实现&nbsp;<code>Instant</code>&nbsp;类似，也包含&nbsp;<code>seconds</code>&nbsp;和&nbsp;<code>nanos</code>&nbsp;两部分，不同的是&nbsp;<code>Duration</code>&nbsp;表示的是一个时间段，所以&nbsp;<code>Duration</code>&nbsp;类中没有&nbsp;<code>now()</code>&nbsp;方法，可以通过&nbsp;<code>Duration.between()</code>&nbsp;方法创建&nbsp;<code>Duration</code>&nbsp;对象：</p><p><img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/ab09197e-1073-4099-9dbd-ac0c63b51187.jpg\" style=\"max-width:100%;\"><br></p><p><img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/4e402968-a55b-4c9a-aac9-6013aa6fa6f4.jpg\" style=\"max-width:100%;\"><br></p><p><img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/6596fb4f-f6ef-453e-9e09-5e591b901d9a.jpg\" style=\"max-width:100%;\"><br></p><p><img src=\"http://yuu-bbs.oss-cn-beijing.aliyuncs.com/1d0943d3-79b0-4016-b588-f526b99eace7.jpg\" style=\"max-width:100%;\"><br></p>', 13, 7, 0, 1, '2020-12-16 17:07:57', 1);
INSERT INTO `question` VALUES (23, '任正非-华为的精神领袖', 'rwqerqwe', '<p>rwqerqwe</p><p><br></p>', 13, 5, 0, 1, '2020-12-16 17:34:03', 2);
INSERT INTO `question` VALUES (24, '123123', '1234', '<p>1234</p>', 13, 24, 0, 1, '2020-12-16 17:35:12', 2);

-- ----------------------------
-- Table structure for sign
-- ----------------------------
DROP TABLE IF EXISTS `sign`;
CREATE TABLE `sign`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '签到ID',
  `sign_user` int(10) NULL DEFAULT NULL COMMENT '用户ID',
  `sign_time` datetime(0) NULL DEFAULT NULL COMMENT '签到时间',
  `sign_integral` int(10) NULL DEFAULT NULL COMMENT '签到奖励积分',
  `continuity_sign` int(10) NULL DEFAULT NULL COMMENT '连续签到天数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sign
-- ----------------------------
INSERT INTO `sign` VALUES (1, 13, '2020-12-15 11:15:18', 10, 6);
INSERT INTO `sign` VALUES (2, 13, '2020-12-04 11:56:06', 10, 1);
INSERT INTO `sign` VALUES (3, 13, '2020-12-11 13:50:02', 10, 2);
INSERT INTO `sign` VALUES (4, 13, '2020-12-13 13:55:09', 10, 2);
INSERT INTO `sign` VALUES (5, 13, '2020-12-14 13:56:33', 10, 2);
INSERT INTO `sign` VALUES (39, 13, '2020-12-16 15:16:41', 20, 7);
INSERT INTO `sign` VALUES (40, 13, '2020-12-18 16:58:05', 10, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户 ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名（登录账号）',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `head_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` int(11) NULL DEFAULT 0 COMMENT '删除标记',
  `integral` int(10) NULL DEFAULT NULL COMMENT '当前积分',
  `grade_id` int(10) NULL DEFAULT NULL COMMENT '当前等级ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (13, 'Yuu', '123', '放方法所所所所', 'http://yuu-bbs.oss-cn-beijing.aliyuncs.com/dcf5b757-f72b-4e70-b80d-983d344431c5.png', '2020-11-25 15:54:26', 0, 849, 3);
INSERT INTO `user` VALUES (14, '123', '123', NULL, 'http://yuu-bbs.oss-cn-beijing.aliyuncs.com/b7ac1ecc-9590-4a25-a6f6-f536176af95a.png', '2020-12-15 10:55:58', 0, 1, 1);
INSERT INTO `user` VALUES (15, '123123', '123123', NULL, '/img/anonymous.jpg', '2020-12-15 11:43:42', 0, 1, 1);
INSERT INTO `user` VALUES (16, 'test', '123', NULL, '/img/anonymous.jpg', '2020-12-15 11:44:45', 0, 31, 1);
INSERT INTO `user` VALUES (17, 'abc', '123', NULL, '/img/anonymous.jpg', '2020-12-15 11:45:49', 0, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
