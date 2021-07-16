SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `permission` varchar(255) NOT NULL,
    `permission_name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 6 DEFAULT CHARSET = utf8mb4;

INSERT INTO `permission` VALUES ('1', 'select', '查看');
INSERT INTO `permission` VALUES ('2', 'update', '更新');
INSERT INTO `permission` VALUES ('3', 'delete', '删除');
INSERT INTO `permission` VALUES ('4', 'save', '新增');

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `role_name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8mb4;

INSERT INTO `role` VALUES ('1', 'admin');
INSERT INTO `role` VALUES ('2', 'vip');
INSERT INTO `role` VALUES ('3', 'p');

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_user_username` (`username`)
) ENGINE = InnoDB AUTO_INCREMENT = 5 DEFAULT CHARSET = utf8mb4;

INSERT INTO `user` VALUES ('1', 'Jackson', '123456');
INSERT INTO `user` VALUES ('2', 'Rose', '123456');
INSERT INTO `user` VALUES ('3', 'Paul', '123456');

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
    `user_id` int(11) NOT NULL,
    `role_id` int(11) NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '2');
INSERT INTO `user_role` VALUES ('3', '3');

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
    `role_id` int(11) NOT NULL,
    `permission_id` int(11) NOT NULL,
    PRIMARY KEY (`permission_id`, `role_id`),
    CONSTRAINT `fk_role_permission_01` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
    CONSTRAINT `fk_role_permission_02` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

INSERT INTO `role_permission` VALUES ('1', '1');
INSERT INTO `role_permission` VALUES ('2', '1');
INSERT INTO `role_permission` VALUES ('3', '1');
INSERT INTO `role_permission` VALUES ('1', '2');
INSERT INTO `role_permission` VALUES ('2', '2');
INSERT INTO `role_permission` VALUES ('1', '3');
INSERT INTO `role_permission` VALUES ('1', '4');
INSERT INTO `role_permission` VALUES ('2', '4');

SET FOREIGN_KEY_CHECKS = 1;
