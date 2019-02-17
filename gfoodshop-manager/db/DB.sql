SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

/*权限管理-START*/

-- ----------------------------
-- Table structure for gfs_perm
-- ----------------------------
DROP TABLE IF EXISTS `gfs_perm`;
CREATE TABLE `gfs_perm`  (
                           `id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
                           `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名',
                           `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gfs_role
-- ----------------------------
DROP TABLE IF EXISTS `gfs_role`;
CREATE TABLE `gfs_role`  (
                           `id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
                           `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
                           `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for gfs_role_perm
-- ----------------------------
DROP TABLE IF EXISTS `gfs_role_perm`;
CREATE TABLE `gfs_role_perm`  (
                                `id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
                                `role_id` int(20) UNSIGNED NULL DEFAULT NULL COMMENT '角色编号',
                                `perm_id` int(20) UNSIGNED NULL DEFAULT NULL COMMENT '权限编号',
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `roleid`(`role_id`) USING BTREE,
                                INDEX `permid`(`perm_id`) USING BTREE,
                                CONSTRAINT `roleid` FOREIGN KEY (`role_id`) REFERENCES `gfs_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT `permid` FOREIGN KEY (`perm_id`) REFERENCES `gfs_perm` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gfs_user
-- ----------------------------
DROP TABLE IF EXISTS `gfs_user`;
CREATE TABLE `gfs_user`  (
                           `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号',
                           `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
                           `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号|登录账号',
                           `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
                           `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '盐',
                           `creat_time` datetime(0) NOT NULL COMMENT '创建时间',
                           `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
                           `status` int(1) NOT NULL COMMENT '状态',
                           `role_id` int(20) UNSIGNED NOT NULL COMMENT '角色编号',
                           `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
                           `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
                           `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                           PRIMARY KEY (`id`) USING BTREE,
                           INDEX `role_id`(`role_id`) USING BTREE,
                           CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `gfs_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

/*权限管理-END*/