DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
    `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `salt` varchar(100) NULL DEFAULT NULL COMMENT '加密盐值',
    `role_ids` varchar(100) NULL DEFAULT NULL COMMENT '角色列表',
    `locked` tinyint(1) DEFAULT '0' COMMENT '是否锁定',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_sys_user_username` (`username`)
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC COMMENT '用户表';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色主键id',
    `role_flag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色唯一标识',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
    `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
    `status` tinyint(1) DEFAULT '1' COMMENT '角色状态 1：正常 0：禁用',
    `permission_ids` varchar(255) NULL DEFAULT NULL COMMENT '资源编号列表',
    PRIMARY KEY (`id`),
    KEY `idx_sys_role_resource_ids` (`permission_ids`)
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC COMMENT '角色表';


DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名',
    `type` tinyint(4) NOT NULL COMMENT '资源类型（1：菜单 2：按钮或文本块）',
    `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父级编号',
    `parent_ids` varchar(100) NULL DEFAULT NULL COMMENT '父级编号列表',
    `permission` varchar(100) NULL DEFAULT NULL COMMENT '权限字符串',
    `icon` varchar(255) NULL DEFAULT NULL COMMENT '图标',
    `sort` int(0) NOT NULL DEFAULT '0' COMMENT '排序',
    `status` tinyint(1) DEFAULT '1' COMMENT '是否有效 1: 有效 0：无效',
    `config` json NULL DEFAULT NULL COMMENT '权限配置',
    PRIMARY KEY (`id`),
    KEY `idx_sys_permission_name` (`name`),
    KEY `idx_sys_permission_parent_id` (`parent_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC COMMENT '资源表';
