/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50616
 Source Host           : localhost:3306
 Source Schema         : admin_base

 Target Server Type    : MySQL
 Target Server Version : 50616
 File Encoding         : 65001

 Date: 15/07/2019 00:46:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_dept
-- ----------------------------
DROP TABLE IF EXISTS `admin_dept`;
CREATE TABLE `admin_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` bigint(20) NOT NULL COMMENT '上级部门ID',
  `dept_name` varchar(100) NOT NULL COMMENT '部门名称',
  `order_num` bigint(20) DEFAULT NULL COMMENT '排序',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门表';

-- ----------------------------
-- Records of admin_dept
-- ----------------------------
BEGIN;
INSERT INTO `admin_dept` VALUES (1, 0, '开发部', 1, '2019-06-14 20:56:41', NULL);
INSERT INTO `admin_dept` VALUES (2, 1, '开发一部', 1, '2019-06-14 20:58:46', NULL);
INSERT INTO `admin_dept` VALUES (3, 1, '开发二部', 2, '2019-06-14 20:58:56', NULL);
INSERT INTO `admin_dept` VALUES (4, 0, '采购部', 2, '2019-06-14 20:59:56', NULL);
INSERT INTO `admin_dept` VALUES (5, 0, '财务部', 3, '2019-06-14 21:00:08', NULL);
INSERT INTO `admin_dept` VALUES (6, 0, '销售部', 4, '2019-06-14 21:00:15', NULL);
INSERT INTO `admin_dept` VALUES (7, 0, '工程部', 5, '2019-06-14 21:00:42', NULL);
INSERT INTO `admin_dept` VALUES (8, 0, '行政部', 6, '2019-06-14 21:00:49', NULL);
INSERT INTO `admin_dept` VALUES (9, 0, '人力资源部', 8, '2019-06-14 21:01:14', '2019-06-14 21:01:34');
INSERT INTO `admin_dept` VALUES (10, 0, '系统部', 7, '2019-06-14 21:01:31', NULL);
COMMIT;

-- ----------------------------
-- Table structure for admin_eximport
-- ----------------------------
DROP TABLE IF EXISTS `admin_eximport`;
CREATE TABLE `admin_eximport` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `field1` varchar(20) NOT NULL COMMENT '字段1',
  `field2` int(11) NOT NULL COMMENT '字段2',
  `field3` varchar(100) NOT NULL COMMENT '字段3',
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='Excel导入导出测试';

-- ----------------------------
-- Records of admin_eximport
-- ----------------------------
BEGIN;
INSERT INTO `admin_eximport` VALUES (1, '字段1', 1, 'xxx@gmail.com', '2019-06-13 03:14:06');
INSERT INTO `admin_eximport` VALUES (2, '字段1', 1, 'xxx@gmail.com', '2019-07-12 15:18:18');
INSERT INTO `admin_eximport` VALUES (3, '1122', 333, 'wo@wangxian.me', '2019-07-12 15:18:18');
COMMIT;

-- ----------------------------
-- Table structure for admin_generator_config
-- ----------------------------
DROP TABLE IF EXISTS `admin_generator_config`;
CREATE TABLE `admin_generator_config` (
  `id` int(11) NOT NULL COMMENT '主键',
  `author` varchar(20) NOT NULL COMMENT '作者',
  `base_package` varchar(50) NOT NULL COMMENT '基础包名',
  `entity_package` varchar(20) NOT NULL COMMENT 'entity文件存放路径',
  `mapper_package` varchar(20) NOT NULL COMMENT 'mapper文件存放路径',
  `mapper_xml_package` varchar(20) NOT NULL COMMENT 'mapper xml文件存放路径',
  `service_package` varchar(20) NOT NULL COMMENT 'servcie文件存放路径',
  `service_impl_package` varchar(20) NOT NULL COMMENT 'serviceImpl文件存放路径',
  `controller_package` varchar(20) NOT NULL COMMENT 'controller文件存放路径',
  `is_trim` char(1) NOT NULL COMMENT '是否去除前缀 1是 0否',
  `trim_value` varchar(10) DEFAULT NULL COMMENT '前缀内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='代码生成配置表';

-- ----------------------------
-- Records of admin_generator_config
-- ----------------------------
BEGIN;
INSERT INTO `admin_generator_config` VALUES (1, 'AdminGenerator', 'com.company.project.gen', 'entity', 'mapper', 'mapper', 'service', 'service.impl', 'controller', '1', 't_');
COMMIT;

-- ----------------------------
-- Table structure for admin_job
-- ----------------------------
DROP TABLE IF EXISTS `admin_job`;
CREATE TABLE `admin_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(50) NOT NULL COMMENT 'spring bean名称',
  `method_name` varchar(50) NOT NULL COMMENT '方法名',
  `params` varchar(50) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(20) NOT NULL COMMENT 'cron表达式',
  `status` char(2) NOT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务表';

-- ----------------------------
-- Records of admin_job
-- ----------------------------
BEGIN;
INSERT INTO `admin_job` VALUES (1, 'testTask', 'test', 'hello', '0/1 * * * * ?', '1', '有参任务调度测试~~', '2018-02-24 16:26:14');
INSERT INTO `admin_job` VALUES (2, 'testTask', 'test1', NULL, '0/10 * * * * ?', '1', '无参任务调度测试', '2018-02-24 17:06:23');
INSERT INTO `admin_job` VALUES (3, 'testTask', 'test', 'hello world', '0/1 * * * * ?', '1', '有参任务调度测试,每隔一秒触发', '2018-02-26 09:28:26');
INSERT INTO `admin_job` VALUES (11, 'testTask', 'test2', NULL, '0/5 * * * * ?', '1', '测试异常', '2018-02-26 11:15:30');
COMMIT;

-- ----------------------------
-- Table structure for admin_job_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_job_log`;
CREATE TABLE `admin_job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(100) NOT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) NOT NULL COMMENT '方法名',
  `params` varchar(200) DEFAULT NULL COMMENT '参数',
  `status` char(2) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` text COMMENT '失败信息',
  `times` decimal(11,0) DEFAULT NULL COMMENT '耗时(单位：毫秒)',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2562 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='调度日志表';

-- ----------------------------
-- Records of admin_job_log
-- ----------------------------
BEGIN;
INSERT INTO `admin_job_log` VALUES (2561, 1, 'testTask', 'test', 'admin', '0', NULL, 1, '2019-06-11 10:46:55');
COMMIT;

-- ----------------------------
-- Table structure for admin_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_log`;
CREATE TABLE `admin_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `username` varchar(50) DEFAULT NULL COMMENT '操作用户',
  `operation` text COMMENT '操作内容',
  `time` decimal(11,0) DEFAULT NULL COMMENT '耗时',
  `method` text COMMENT '操作方法',
  `params` text COMMENT '方法参数',
  `ip` varchar(64) DEFAULT NULL COMMENT '操作者IP',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `location` varchar(50) DEFAULT NULL COMMENT '操作地点',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1023 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='操作日志表';

-- ----------------------------
-- Records of admin_log
-- ----------------------------
BEGIN;
INSERT INTO `admin_log` VALUES (999, 'admin', '修改角色', 60, 'com.company.project.system.controller.RoleController.updateRole()', ' role: \"Role(roleId=1, roleName=系统管理员, remark=系统管理员，拥有所有操作权限 ^_^, createdAt=null, modifyTime=Fri Jul 12 15:54:34 CST 2019, menuIds=1,3,11,12,13,160,161,4,14,15,16,162,5,17,18,19,163,6,20,21,22,164,2,8,23,10,24,170,136,171,172,113,114,127,128,129,130,131,101,102,103,104,105,106,107,108,173,109,110,174,137,138,165,139,166,115,132,133,135,134,126,159,116,117,119,120,121,122,123,118,125,167,168,169)\"', '192.168.1.22', '2019-07-12 15:54:35', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1000, 'admin', '修改菜单/按钮', 14, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=159, parentId=132, menuName=其他组件, url=/others/admin/others, perms=others:demo:others, icon=, type=0, orderNum=5, createdAt=null, modifyTime=Fri Jul 12 16:04:45 CST 2019)\"', '192.168.1.22', '2019-07-12 16:04:45', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1001, 'admin', '修改角色', 68, 'com.company.project.system.controller.RoleController.updateRole()', ' role: \"Role(roleId=1, roleName=系统管理员, remark=系统管理员，拥有所有操作权限 ^_^, createdAt=null, modifyTime=Fri Jul 12 16:07:29 CST 2019, menuIds=1,3,11,12,13,160,161,4,14,15,16,162,5,17,18,19,163,6,20,21,22,164,2,8,23,10,24,170,136,171,172,113,114,127,128,129,130,131,101,102,103,104,105,106,107,108,173,109,110,174,137,138,165,139,166,115,132,133,135,134,126,159,116,117,119,120,121,122,123,118,125,167,168,169)\"', '192.168.1.22', '2019-07-12 16:07:30', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1002, 'admin', '修改菜单/按钮', 9, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=159, parentId=132, menuName=其他组件, url=/others/demo/others, perms=others:demo:others, icon=, type=0, orderNum=5, createdAt=null, modifyTime=Fri Jul 12 16:12:06 CST 2019)\"', '192.168.1.22', '2019-07-12 16:12:07', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1003, 'admin', '修改菜单/按钮', 19, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=3, parentId=1, menuName=用户管理, url=/system/user, perms=user:view, icon=layui-icon-CI, type=0, orderNum=1, createdAt=null, modifyTime=Fri Jul 12 19:29:29 CST 2019)\"', '192.168.1.22', '2019-07-12 19:29:30', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1004, 'admin', '执行Redis keys命令', 17, 'com.company.project.monitor.controller.RedisController.keys()', ' arg: \"*\"', '192.168.1.22', '2019-07-12 19:39:11', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1005, 'admin', '修改用户', 115, 'com.company.project.system.controller.UserController.updateUser()', ' user: \"User(userId=1, username=null, password=null, deptId=1, email=admin@qq.com, mobile=17788888888, status=1, createdAt=null, modifyTime=Sat Jul 13 07:38:23 CST 2019, lastLoginTime=null, sex=0, avatar=null, theme=null, isTab=null, description=我是帅比作者。, deptName=null, createTimeFrom=null, createTimeTo=null, roleId=1, roleName=null)\"', '127.0.0.1', '2019-07-13 07:38:23', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1006, 'admin', '修改菜单/按钮', 20, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=3, parentId=1, menuName=用户管理, url=/system/user, perms=user:view, icon=layui-icon-home, type=0, orderNum=1, createdAt=null, modifyTime=Sun Jul 14 01:00:06 CST 2019)\"', '127.0.0.1', '2019-07-14 01:00:06', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1007, 'admin', '修改菜单/按钮', 9, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=4, parentId=1, menuName=角色管理, url=/system/role, perms=role:view, icon=layui-icon-earth, type=0, orderNum=2, createdAt=null, modifyTime=Sun Jul 14 01:00:30 CST 2019)\"', '127.0.0.1', '2019-07-14 01:00:30', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1008, 'admin', '修改菜单/按钮', 9, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=5, parentId=1, menuName=菜单管理, url=/system/menu, perms=menu:view, icon=layui-icon-appstore, type=0, orderNum=3, createdAt=null, modifyTime=Sun Jul 14 01:00:48 CST 2019)\"', '127.0.0.1', '2019-07-14 01:00:49', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1009, 'admin', '修改菜单/按钮', 10, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=6, parentId=1, menuName=部门管理, url=/system/dept, perms=dept:view, icon=layui-icon-idcard, type=0, orderNum=4, createdAt=null, modifyTime=Sun Jul 14 01:01:11 CST 2019)\"', '127.0.0.1', '2019-07-14 01:01:11', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1010, 'admin', '修改菜单/按钮', 8, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=10, parentId=2, menuName=系统日志, url=/monitor/log, perms=log:view, icon=layui-icon-filedone, type=0, orderNum=2, createdAt=null, modifyTime=Sun Jul 14 01:51:37 CST 2019)\"', '127.0.0.1', '2019-07-14 01:51:37', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1011, 'admin', '修改菜单/按钮', 7, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=8, parentId=2, menuName=在线用户, url=/monitor/online, perms=online:view, icon=layui-icon-team, type=0, orderNum=1, createdAt=null, modifyTime=Sun Jul 14 01:52:12 CST 2019)\"', '127.0.0.1', '2019-07-14 01:52:12', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1012, 'admin', '修改菜单/按钮', 5, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=136, parentId=2, menuName=登录日志, url=/monitor/loginlog, perms=loginlog:view, icon=layui-icon-container, type=0, orderNum=3, createdAt=null, modifyTime=Sun Jul 14 01:53:15 CST 2019)\"', '127.0.0.1', '2019-07-14 01:53:15', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1013, 'admin', '修改菜单/按钮', 8, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=113, parentId=2, menuName=Redis监控, url=/monitor/redis/info, perms=redis:view, icon=layui-icon-info-circle, type=0, orderNum=4, createdAt=null, modifyTime=Sun Jul 14 01:53:29 CST 2019)\"', '127.0.0.1', '2019-07-14 01:53:30', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1014, 'admin', '修改菜单/按钮', 7, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=114, parentId=2, menuName=Redis终端, url=/monitor/redis/terminal, perms=redis:terminal:view, icon=layui-icon-CI, type=0, orderNum=5, createdAt=null, modifyTime=Sun Jul 14 01:53:46 CST 2019)\"', '127.0.0.1', '2019-07-14 01:53:46', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1015, 'admin', '修改菜单/按钮', 8, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=127, parentId=2, menuName=请求追踪, url=/monitor/httptrace, perms=httptrace:view, icon=layui-icon-layout, type=0, orderNum=6, createdAt=null, modifyTime=Sun Jul 14 01:54:06 CST 2019)\"', '127.0.0.1', '2019-07-14 01:54:06', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1016, 'admin', '修改菜单/按钮', 7, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=128, parentId=2, menuName=系统信息, url=, perms=, icon=layui-icon-file-exception, type=0, orderNum=7, createdAt=null, modifyTime=Sun Jul 14 01:55:53 CST 2019)\"', '127.0.0.1', '2019-07-14 01:55:54', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1017, 'admin', '修改菜单/按钮', 8, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=129, parentId=128, menuName=JVM信息, url=/monitor/jvm, perms=jvm:view, icon=layui-icon-compass, type=0, orderNum=1, createdAt=null, modifyTime=Sun Jul 14 01:56:11 CST 2019)\"', '127.0.0.1', '2019-07-14 01:56:12', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1018, 'admin', '修改菜单/按钮', 24, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=130, parentId=128, menuName=Tomcat信息, url=/monitor/tomcat, perms=tomcat:view, icon=layui-icon-sever, type=0, orderNum=2, createdAt=null, modifyTime=Sun Jul 14 01:56:54 CST 2019)\"', '127.0.0.1', '2019-07-14 01:56:54', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1019, 'admin', '修改菜单/按钮', 8, 'com.company.project.system.controller.MenuController.updateMenu()', ' menu: \"Menu(menuId=131, parentId=128, menuName=服务器信息, url=/monitor/server, perms=server:view, icon=layui-icon-database, type=0, orderNum=3, createdAt=null, modifyTime=Sun Jul 14 01:57:50 CST 2019)\"', '127.0.0.1', '2019-07-14 01:57:50', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1020, 'admin', '新增用户', 27, 'com.company.project.system.controller.UserController.addUser()', ' user: \"User(userId=4, username=test, password=50531c15181030c9a07bb7fb8551ac26, deptId=1, email=wo@wangxian.me, mobile=18611619885, status=1, createdAt=Sun Jul 14 22:37:30 CST 2019, modifyTime=null, lastLoginTime=null, sex=0, avatar=default.jpg, theme=black, isTab=1, description=test, deptName=null, createTimeFrom=null, createTimeTo=null, roleId=80, roleName=null)\"', '127.0.0.1', '2019-07-14 22:37:30', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1021, 'admin', '删除用户', 28, 'com.company.project.system.controller.UserController.deleteUsers()', ' userIds: \"4\"', '127.0.0.1', '2019-07-14 22:39:06', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `admin_log` VALUES (1022, 'admin', '执行Redis keys命令', 14, 'com.company.project.monitor.controller.RedisController.keys()', ' arg: \"*\"', '127.0.0.1', '2019-07-15 00:45:51', '内网IP|0|0|内网IP|内网IP');
COMMIT;

-- ----------------------------
-- Table structure for admin_login_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_login_log`;
CREATE TABLE `admin_login_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `location` varchar(50) DEFAULT NULL COMMENT '登录地点',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `system` varchar(50) DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(50) DEFAULT NULL COMMENT '浏览器',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='登录日志表';

-- ----------------------------
-- Records of admin_login_log
-- ----------------------------
BEGIN;
INSERT INTO `admin_login_log` VALUES (75, 'admin', '2019-07-12 15:35:38', '内网IP|0|0|内网IP|内网IP', '192.168.1.22', 'Mac OS X 10_13_6', 'Chrome 75');
INSERT INTO `admin_login_log` VALUES (76, 'admin', '2019-07-12 15:41:11', '内网IP|0|0|内网IP|内网IP', '192.168.1.22', 'Mac OS X 10_13_6', 'Chrome 75');
INSERT INTO `admin_login_log` VALUES (77, 'admin', '2019-07-12 16:07:42', '内网IP|0|0|内网IP|内网IP', '192.168.1.22', 'Mac OS X 10_13_6', 'Chrome 75');
INSERT INTO `admin_login_log` VALUES (78, 'admin', '2019-07-12 16:13:01', '内网IP|0|0|内网IP|内网IP', '192.168.1.22', 'Mac OS X 10_13_6', 'Chrome 75');
INSERT INTO `admin_login_log` VALUES (79, 'admin', '2019-07-12 18:09:42', '内网IP|0|0|内网IP|内网IP', '192.168.1.22', 'Mac OS X 10_13_6', 'Chrome 75');
INSERT INTO `admin_login_log` VALUES (80, 'admin', '2019-07-12 19:46:52', '内网IP|0|0|内网IP|内网IP', '192.168.1.22', 'Mac OS X 10_13_6', 'Chrome 75');
INSERT INTO `admin_login_log` VALUES (81, 'admin', '2019-07-13 00:05:17', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Mac OS X 10_13_6', 'Chrome 75');
INSERT INTO `admin_login_log` VALUES (82, 'admin', '2019-07-13 07:26:23', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Mac OS X 10_13_6', 'Chrome 75');
INSERT INTO `admin_login_log` VALUES (83, 'admin', '2019-07-13 08:27:52', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Mac OS X 10_13_6', 'Chrome 75');
INSERT INTO `admin_login_log` VALUES (84, 'admin', '2019-07-14 21:25:40', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Mac OS X 10_13_6', 'Chrome 75');
INSERT INTO `admin_login_log` VALUES (85, 'test', '2019-07-14 22:37:56', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Mac OS X 10_13_6', 'Chrome 75');
INSERT INTO `admin_login_log` VALUES (86, 'admin', '2019-07-14 22:38:31', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Mac OS X 10_13_6', 'Chrome 75');
COMMIT;

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
  `parent_id` bigint(20) NOT NULL COMMENT '上级菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单/按钮名称',
  `url` varchar(50) DEFAULT NULL COMMENT '菜单URL',
  `perms` text COMMENT '权限标识',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `type` char(2) NOT NULL COMMENT '类型 0菜单 1按钮',
  `order_num` bigint(20) DEFAULT NULL COMMENT '排序',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单表';

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
BEGIN;
INSERT INTO `admin_menu` VALUES (1, 0, '系统管理', NULL, NULL, 'layui-icon-setting', '0', 1, '2017-12-27 16:39:07', NULL);
INSERT INTO `admin_menu` VALUES (2, 0, '系统监控', '', '', 'layui-icon-alert', '0', 2, '2017-12-27 16:45:51', '2019-06-13 11:20:40');
INSERT INTO `admin_menu` VALUES (3, 1, '用户管理', '/system/user', 'user:view', 'layui-icon-user', '0', 1, '2017-12-27 16:47:13', '2019-07-14 01:00:06');
INSERT INTO `admin_menu` VALUES (4, 1, '角色管理', '/system/role', 'role:view', 'layui-icon-solution', '0', 2, '2017-12-27 16:48:09', '2019-07-14 01:00:30');
INSERT INTO `admin_menu` VALUES (5, 1, '菜单管理', '/system/menu', 'menu:view', 'layui-icon-menu', '0', 3, '2017-12-27 16:48:57', '2019-07-14 01:00:49');
INSERT INTO `admin_menu` VALUES (6, 1, '部门管理', '/system/dept', 'dept:view', 'layui-icon-cluster', '0', 4, '2017-12-27 16:57:33', '2019-07-14 01:01:11');
INSERT INTO `admin_menu` VALUES (8, 2, '在线用户', '/monitor/online', 'online:view', 'layui-icon-team', '0', 1, '2017-12-27 16:59:33', '2019-07-14 01:52:12');
INSERT INTO `admin_menu` VALUES (10, 2, '系统日志', '/monitor/log', 'log:view', 'layui-icon-filedone', '0', 2, '2017-12-27 17:00:50', '2019-07-14 01:51:37');
INSERT INTO `admin_menu` VALUES (11, 3, '新增用户', NULL, 'user:add', NULL, '1', NULL, '2017-12-27 17:02:58', NULL);
INSERT INTO `admin_menu` VALUES (12, 3, '修改用户', NULL, 'user:update', NULL, '1', NULL, '2017-12-27 17:04:07', NULL);
INSERT INTO `admin_menu` VALUES (13, 3, '删除用户', NULL, 'user:delete', NULL, '1', NULL, '2017-12-27 17:04:58', NULL);
INSERT INTO `admin_menu` VALUES (14, 4, '新增角色', NULL, 'role:add', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO `admin_menu` VALUES (15, 4, '修改角色', NULL, 'role:update', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO `admin_menu` VALUES (16, 4, '删除角色', NULL, 'role:delete', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO `admin_menu` VALUES (17, 5, '新增菜单', NULL, 'menu:add', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO `admin_menu` VALUES (18, 5, '修改菜单', NULL, 'menu:update', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO `admin_menu` VALUES (19, 5, '删除菜单', NULL, 'menu:delete', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO `admin_menu` VALUES (20, 6, '新增部门', NULL, 'dept:add', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO `admin_menu` VALUES (21, 6, '修改部门', NULL, 'dept:update', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO `admin_menu` VALUES (22, 6, '删除部门', NULL, 'dept:delete', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO `admin_menu` VALUES (23, 8, '踢出用户', NULL, 'user:kickout', NULL, '1', NULL, '2017-12-27 17:11:13', NULL);
INSERT INTO `admin_menu` VALUES (24, 10, '删除日志', NULL, 'log:delete', NULL, '1', NULL, '2017-12-27 17:11:45', '2019-06-06 05:56:40');
INSERT INTO `admin_menu` VALUES (101, 0, '任务调度', NULL, NULL, 'layui-icon-time-circle', '0', 3, '2018-02-24 15:52:57', NULL);
INSERT INTO `admin_menu` VALUES (102, 101, '定时任务', '/job/job', 'job:view', 'layui-icon-reloadtime', '0', 1, '2018-02-24 15:53:53', '2018-04-25 09:05:12');
INSERT INTO `admin_menu` VALUES (103, 102, '新增任务', NULL, 'job:add', '', '1', NULL, '2018-02-24 15:55:10', NULL);
INSERT INTO `admin_menu` VALUES (104, 102, '修改任务', NULL, 'job:update', NULL, '1', NULL, '2018-02-24 15:55:53', NULL);
INSERT INTO `admin_menu` VALUES (105, 102, '删除任务', NULL, 'job:delete', NULL, '1', NULL, '2018-02-24 15:56:18', NULL);
INSERT INTO `admin_menu` VALUES (106, 102, '暂停任务', NULL, 'job:pause', NULL, '1', NULL, '2018-02-24 15:57:08', NULL);
INSERT INTO `admin_menu` VALUES (107, 102, '恢复任务', NULL, 'job:resume', NULL, '1', NULL, '2018-02-24 15:58:21', NULL);
INSERT INTO `admin_menu` VALUES (108, 102, '立即执行任务', NULL, 'job:run', NULL, '1', NULL, '2018-02-24 15:59:45', NULL);
INSERT INTO `admin_menu` VALUES (109, 101, '调度日志', '/job/log', 'job:log:view', 'layui-icon-file-text', '0', 2, '2018-02-24 16:00:45', '2019-06-09 02:48:19');
INSERT INTO `admin_menu` VALUES (110, 109, '删除日志', NULL, 'job:log:delete', NULL, '1', NULL, '2018-02-24 16:01:21', NULL);
INSERT INTO `admin_menu` VALUES (113, 2, 'Redis监控', '/monitor/redis/info', 'redis:view', 'layui-icon-info-circle', '0', 4, '2018-06-28 14:29:42', '2019-07-14 01:53:30');
INSERT INTO `admin_menu` VALUES (114, 2, 'Redis终端', '/monitor/redis/terminal', 'redis:terminal:view', 'layui-icon-right-square', '0', 5, '2018-06-28 15:35:21', '2019-07-14 01:53:46');
INSERT INTO `admin_menu` VALUES (115, 0, '其他模块', NULL, NULL, 'layui-icon-gift', '0', 5, '2019-05-27 10:18:07', NULL);
INSERT INTO `admin_menu` VALUES (116, 115, 'Apex图表', '', '', 'layui-icon-piechart', '0', 2, '2019-05-27 10:21:35', NULL);
INSERT INTO `admin_menu` VALUES (117, 116, '线性图表', '/others/apex/line', 'apex:line:view', 'layui-icon-linechart', '0', 1, '2019-05-27 10:24:49', NULL);
INSERT INTO `admin_menu` VALUES (118, 115, '高德地图', '/others/map', 'map:view', 'layui-icon-location', '0', 3, '2019-05-27 17:13:12', '2019-06-12 15:33:00');
INSERT INTO `admin_menu` VALUES (119, 116, '面积图表', '/others/apex/area', 'apex:area:view', 'layui-icon-areachart', '0', 2, '2019-05-27 18:49:22', NULL);
INSERT INTO `admin_menu` VALUES (120, 116, '柱形图表', '/others/apex/column', 'apex:column:view', 'layui-icon-barchart', '0', 3, '2019-05-27 18:51:33', NULL);
INSERT INTO `admin_menu` VALUES (121, 116, '雷达图表', '/others/apex/radar', 'apex:radar:view', 'layui-icon-pointmap', '0', 4, '2019-05-27 18:56:05', NULL);
INSERT INTO `admin_menu` VALUES (122, 116, '条形图表', '/others/apex/bar', 'apex:bar:view', 'layui-icon-scan', '0', 5, '2019-05-27 18:57:02', NULL);
INSERT INTO `admin_menu` VALUES (123, 116, '混合图表', '/others/apex/mix', 'apex:mix:view', 'layui-icon-sliders', '0', 6, '2019-05-27 18:58:04', '2019-06-06 02:55:23');
INSERT INTO `admin_menu` VALUES (125, 115, '导入导出', '/others/eximport', 'others:eximport:view', 'layui-icon-file-excel', '0', 4, '2019-05-27 19:01:57', '2019-06-13 01:20:14');
INSERT INTO `admin_menu` VALUES (126, 132, '系统图标', '/others/demo/icon', 'demo:icons:view', 'layui-icon-image', '0', 4, '2019-05-27 19:03:18', '2019-06-06 03:05:26');
INSERT INTO `admin_menu` VALUES (127, 2, '请求追踪', '/monitor/httptrace', 'httptrace:view', 'layui-icon-layout', '0', 6, '2019-05-27 19:06:38', '2019-07-14 01:54:06');
INSERT INTO `admin_menu` VALUES (128, 2, '系统信息', '', '', 'layui-icon-file-exception', '0', 7, '2019-05-27 19:08:23', '2019-07-14 01:55:54');
INSERT INTO `admin_menu` VALUES (129, 128, 'JVM信息', '/monitor/jvm', 'jvm:view', 'layui-icon-dashboard', '0', 1, '2019-05-27 19:08:50', '2019-07-14 01:56:12');
INSERT INTO `admin_menu` VALUES (130, 128, 'Tomcat信息', '/monitor/tomcat', 'tomcat:view', 'layui-icon-sever', '0', 2, '2019-05-27 19:09:26', '2019-07-14 01:56:54');
INSERT INTO `admin_menu` VALUES (131, 128, '服务器信息', '/monitor/server', 'server:view', 'layui-icon-database', '0', 3, '2019-05-27 19:10:07', '2019-07-14 01:57:50');
INSERT INTO `admin_menu` VALUES (132, 115, 'Layui组件', '', '', 'layui-icon-appstore', '0', 1, '2019-05-27 19:13:54', NULL);
INSERT INTO `admin_menu` VALUES (133, 132, '表单组件', '/others/demo/form', 'demo:form:view', 'layui-icon-table', '0', 1, '2019-05-27 19:14:45', NULL);
INSERT INTO `admin_menu` VALUES (134, 132, '常用工具', '/others/demo/tools', 'demo:tools:view', 'layui-icon-eye', '0', 3, '2019-05-29 10:11:22', '2019-06-12 13:21:27');
INSERT INTO `admin_menu` VALUES (135, 132, '表单组合', '/others/demo/form/group', 'demo:formgroup:view', 'layui-icon-build', '0', 2, '2019-05-29 10:16:19', NULL);
INSERT INTO `admin_menu` VALUES (136, 2, '登录日志', '/monitor/loginlog', 'loginlog:view', 'layui-icon-login', '0', 3, '2019-05-29 15:56:15', '2019-07-14 01:53:15');
INSERT INTO `admin_menu` VALUES (137, 0, '代码生成', '', NULL, 'layui-icon-verticalright', '0', 4, '2019-06-03 15:35:58', NULL);
INSERT INTO `admin_menu` VALUES (138, 137, '生成配置', '/generator/configure', 'generator:configure:view', 'layui-icon-wrench', '0', 1, '2019-06-03 15:38:36', NULL);
INSERT INTO `admin_menu` VALUES (139, 137, '代码生成', '/generator/generator', 'generator:view', 'layui-icon-verticalright', '0', 2, '2019-06-03 15:39:15', '2019-06-13 14:31:38');
INSERT INTO `admin_menu` VALUES (159, 132, '其他组件', '/others/demo/others', 'others:demo:others', 'layui-icon-block', '0', 5, '2019-06-12 07:51:08', '2019-07-12 16:12:07');
INSERT INTO `admin_menu` VALUES (160, 3, '密码重置', NULL, 'user:password:reset', NULL, '1', NULL, '2019-06-13 08:40:13', NULL);
INSERT INTO `admin_menu` VALUES (161, 3, '导出Excel', NULL, 'user:export', NULL, '1', NULL, '2019-06-13 08:40:34', NULL);
INSERT INTO `admin_menu` VALUES (162, 4, '导出Excel', NULL, 'role:export', NULL, '1', NULL, '2019-06-13 14:29:00', '2019-06-13 14:29:11');
INSERT INTO `admin_menu` VALUES (163, 5, '导出Excel', NULL, 'menu:export', NULL, '1', NULL, '2019-06-13 14:29:32', NULL);
INSERT INTO `admin_menu` VALUES (164, 6, '导出Excel', NULL, 'dept:export', NULL, '1', NULL, '2019-06-13 14:29:59', NULL);
INSERT INTO `admin_menu` VALUES (165, 138, '修改配置', NULL, 'generator:configure:update', NULL, '1', NULL, '2019-06-13 14:32:09', '2019-06-13 14:32:20');
INSERT INTO `admin_menu` VALUES (166, 139, '生成代码', NULL, 'generator:generate', NULL, '1', NULL, '2019-06-13 14:32:51', NULL);
INSERT INTO `admin_menu` VALUES (167, 125, '模板下载', NULL, 'eximport:template', NULL, '1', NULL, '2019-06-13 14:33:37', NULL);
INSERT INTO `admin_menu` VALUES (168, 125, '导出Excel', NULL, 'eximport:export', NULL, '1', NULL, '2019-06-13 14:33:57', NULL);
INSERT INTO `admin_menu` VALUES (169, 125, '导入Excel', NULL, 'eximport:import', NULL, '1', NULL, '2019-06-13 14:34:19', NULL);
INSERT INTO `admin_menu` VALUES (170, 10, '导出Excel', NULL, 'log:export', NULL, '1', NULL, '2019-06-13 14:34:55', NULL);
INSERT INTO `admin_menu` VALUES (171, 136, '删除日志', NULL, 'loginlog:delete', NULL, '1', NULL, '2019-06-13 14:35:27', '2019-06-13 14:36:08');
INSERT INTO `admin_menu` VALUES (172, 136, '导出Excel', NULL, 'loginlog:export', NULL, '1', NULL, '2019-06-13 14:36:26', NULL);
INSERT INTO `admin_menu` VALUES (173, 102, '导出Excel', NULL, 'job:export', NULL, '1', NULL, '2019-06-13 14:37:25', NULL);
INSERT INTO `admin_menu` VALUES (174, 109, '导出Excel', NULL, 'job:log:export', NULL, '1', NULL, '2019-06-13 14:37:46', '2019-06-13 14:38:02');
COMMIT;

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of admin_role
-- ----------------------------
BEGIN;
INSERT INTO `admin_role` VALUES (1, '系统管理员', '系统管理员，拥有所有操作权限 ^_^', '2019-06-14 16:23:11', '2019-07-12 16:07:30');
INSERT INTO `admin_role` VALUES (2, '注册账户', '注册账户，拥有查看，新增权限（新增用户除外）和导出Excel权限', '2019-06-14 16:00:15', '2019-06-14 20:47:47');
INSERT INTO `admin_role` VALUES (77, 'Redis监控员', '负责Redis模块', '2019-06-14 20:49:22', NULL);
INSERT INTO `admin_role` VALUES (78, '系统监控员', '负责整个系统监控模块', '2019-06-14 20:50:07', NULL);
INSERT INTO `admin_role` VALUES (79, '跑批人员', '负责任务调度跑批模块', '2019-06-14 20:51:02', NULL);
INSERT INTO `admin_role` VALUES (80, '开发人员', '拥有代码生成模块的权限', '2019-06-14 20:51:26', NULL);
COMMIT;

-- ----------------------------
-- Table structure for admin_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_menu`;
CREATE TABLE `admin_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单/按钮ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色菜单关联表';

-- ----------------------------
-- Records of admin_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `admin_role_menu` VALUES (2, 1);
INSERT INTO `admin_role_menu` VALUES (2, 3);
INSERT INTO `admin_role_menu` VALUES (2, 161);
INSERT INTO `admin_role_menu` VALUES (2, 4);
INSERT INTO `admin_role_menu` VALUES (2, 14);
INSERT INTO `admin_role_menu` VALUES (2, 162);
INSERT INTO `admin_role_menu` VALUES (2, 5);
INSERT INTO `admin_role_menu` VALUES (2, 17);
INSERT INTO `admin_role_menu` VALUES (2, 163);
INSERT INTO `admin_role_menu` VALUES (2, 6);
INSERT INTO `admin_role_menu` VALUES (2, 20);
INSERT INTO `admin_role_menu` VALUES (2, 164);
INSERT INTO `admin_role_menu` VALUES (2, 2);
INSERT INTO `admin_role_menu` VALUES (2, 8);
INSERT INTO `admin_role_menu` VALUES (2, 10);
INSERT INTO `admin_role_menu` VALUES (2, 170);
INSERT INTO `admin_role_menu` VALUES (2, 136);
INSERT INTO `admin_role_menu` VALUES (2, 172);
INSERT INTO `admin_role_menu` VALUES (2, 113);
INSERT INTO `admin_role_menu` VALUES (2, 114);
INSERT INTO `admin_role_menu` VALUES (2, 127);
INSERT INTO `admin_role_menu` VALUES (2, 128);
INSERT INTO `admin_role_menu` VALUES (2, 129);
INSERT INTO `admin_role_menu` VALUES (2, 130);
INSERT INTO `admin_role_menu` VALUES (2, 131);
INSERT INTO `admin_role_menu` VALUES (2, 101);
INSERT INTO `admin_role_menu` VALUES (2, 102);
INSERT INTO `admin_role_menu` VALUES (2, 173);
INSERT INTO `admin_role_menu` VALUES (2, 109);
INSERT INTO `admin_role_menu` VALUES (2, 174);
INSERT INTO `admin_role_menu` VALUES (2, 137);
INSERT INTO `admin_role_menu` VALUES (2, 138);
INSERT INTO `admin_role_menu` VALUES (2, 139);
INSERT INTO `admin_role_menu` VALUES (2, 115);
INSERT INTO `admin_role_menu` VALUES (2, 132);
INSERT INTO `admin_role_menu` VALUES (2, 133);
INSERT INTO `admin_role_menu` VALUES (2, 135);
INSERT INTO `admin_role_menu` VALUES (2, 134);
INSERT INTO `admin_role_menu` VALUES (2, 126);
INSERT INTO `admin_role_menu` VALUES (2, 159);
INSERT INTO `admin_role_menu` VALUES (2, 116);
INSERT INTO `admin_role_menu` VALUES (2, 117);
INSERT INTO `admin_role_menu` VALUES (2, 119);
INSERT INTO `admin_role_menu` VALUES (2, 120);
INSERT INTO `admin_role_menu` VALUES (2, 121);
INSERT INTO `admin_role_menu` VALUES (2, 122);
INSERT INTO `admin_role_menu` VALUES (2, 123);
INSERT INTO `admin_role_menu` VALUES (2, 118);
INSERT INTO `admin_role_menu` VALUES (2, 125);
INSERT INTO `admin_role_menu` VALUES (2, 167);
INSERT INTO `admin_role_menu` VALUES (2, 168);
INSERT INTO `admin_role_menu` VALUES (2, 169);
INSERT INTO `admin_role_menu` VALUES (77, 2);
INSERT INTO `admin_role_menu` VALUES (77, 113);
INSERT INTO `admin_role_menu` VALUES (77, 114);
INSERT INTO `admin_role_menu` VALUES (78, 2);
INSERT INTO `admin_role_menu` VALUES (78, 8);
INSERT INTO `admin_role_menu` VALUES (78, 23);
INSERT INTO `admin_role_menu` VALUES (78, 10);
INSERT INTO `admin_role_menu` VALUES (78, 24);
INSERT INTO `admin_role_menu` VALUES (78, 170);
INSERT INTO `admin_role_menu` VALUES (78, 136);
INSERT INTO `admin_role_menu` VALUES (78, 171);
INSERT INTO `admin_role_menu` VALUES (78, 172);
INSERT INTO `admin_role_menu` VALUES (78, 113);
INSERT INTO `admin_role_menu` VALUES (78, 114);
INSERT INTO `admin_role_menu` VALUES (78, 127);
INSERT INTO `admin_role_menu` VALUES (78, 128);
INSERT INTO `admin_role_menu` VALUES (78, 129);
INSERT INTO `admin_role_menu` VALUES (78, 130);
INSERT INTO `admin_role_menu` VALUES (78, 131);
INSERT INTO `admin_role_menu` VALUES (79, 101);
INSERT INTO `admin_role_menu` VALUES (79, 102);
INSERT INTO `admin_role_menu` VALUES (79, 103);
INSERT INTO `admin_role_menu` VALUES (79, 104);
INSERT INTO `admin_role_menu` VALUES (79, 105);
INSERT INTO `admin_role_menu` VALUES (79, 106);
INSERT INTO `admin_role_menu` VALUES (79, 107);
INSERT INTO `admin_role_menu` VALUES (79, 108);
INSERT INTO `admin_role_menu` VALUES (79, 173);
INSERT INTO `admin_role_menu` VALUES (79, 109);
INSERT INTO `admin_role_menu` VALUES (79, 110);
INSERT INTO `admin_role_menu` VALUES (79, 174);
INSERT INTO `admin_role_menu` VALUES (80, 137);
INSERT INTO `admin_role_menu` VALUES (80, 138);
INSERT INTO `admin_role_menu` VALUES (80, 165);
INSERT INTO `admin_role_menu` VALUES (80, 139);
INSERT INTO `admin_role_menu` VALUES (80, 166);
INSERT INTO `admin_role_menu` VALUES (1, 1);
INSERT INTO `admin_role_menu` VALUES (1, 3);
INSERT INTO `admin_role_menu` VALUES (1, 11);
INSERT INTO `admin_role_menu` VALUES (1, 12);
INSERT INTO `admin_role_menu` VALUES (1, 13);
INSERT INTO `admin_role_menu` VALUES (1, 160);
INSERT INTO `admin_role_menu` VALUES (1, 161);
INSERT INTO `admin_role_menu` VALUES (1, 4);
INSERT INTO `admin_role_menu` VALUES (1, 14);
INSERT INTO `admin_role_menu` VALUES (1, 15);
INSERT INTO `admin_role_menu` VALUES (1, 16);
INSERT INTO `admin_role_menu` VALUES (1, 162);
INSERT INTO `admin_role_menu` VALUES (1, 5);
INSERT INTO `admin_role_menu` VALUES (1, 17);
INSERT INTO `admin_role_menu` VALUES (1, 18);
INSERT INTO `admin_role_menu` VALUES (1, 19);
INSERT INTO `admin_role_menu` VALUES (1, 163);
INSERT INTO `admin_role_menu` VALUES (1, 6);
INSERT INTO `admin_role_menu` VALUES (1, 20);
INSERT INTO `admin_role_menu` VALUES (1, 21);
INSERT INTO `admin_role_menu` VALUES (1, 22);
INSERT INTO `admin_role_menu` VALUES (1, 164);
INSERT INTO `admin_role_menu` VALUES (1, 2);
INSERT INTO `admin_role_menu` VALUES (1, 8);
INSERT INTO `admin_role_menu` VALUES (1, 23);
INSERT INTO `admin_role_menu` VALUES (1, 10);
INSERT INTO `admin_role_menu` VALUES (1, 24);
INSERT INTO `admin_role_menu` VALUES (1, 170);
INSERT INTO `admin_role_menu` VALUES (1, 136);
INSERT INTO `admin_role_menu` VALUES (1, 171);
INSERT INTO `admin_role_menu` VALUES (1, 172);
INSERT INTO `admin_role_menu` VALUES (1, 113);
INSERT INTO `admin_role_menu` VALUES (1, 114);
INSERT INTO `admin_role_menu` VALUES (1, 127);
INSERT INTO `admin_role_menu` VALUES (1, 128);
INSERT INTO `admin_role_menu` VALUES (1, 129);
INSERT INTO `admin_role_menu` VALUES (1, 130);
INSERT INTO `admin_role_menu` VALUES (1, 131);
INSERT INTO `admin_role_menu` VALUES (1, 101);
INSERT INTO `admin_role_menu` VALUES (1, 102);
INSERT INTO `admin_role_menu` VALUES (1, 103);
INSERT INTO `admin_role_menu` VALUES (1, 104);
INSERT INTO `admin_role_menu` VALUES (1, 105);
INSERT INTO `admin_role_menu` VALUES (1, 106);
INSERT INTO `admin_role_menu` VALUES (1, 107);
INSERT INTO `admin_role_menu` VALUES (1, 108);
INSERT INTO `admin_role_menu` VALUES (1, 173);
INSERT INTO `admin_role_menu` VALUES (1, 109);
INSERT INTO `admin_role_menu` VALUES (1, 110);
INSERT INTO `admin_role_menu` VALUES (1, 174);
INSERT INTO `admin_role_menu` VALUES (1, 137);
INSERT INTO `admin_role_menu` VALUES (1, 138);
INSERT INTO `admin_role_menu` VALUES (1, 165);
INSERT INTO `admin_role_menu` VALUES (1, 139);
INSERT INTO `admin_role_menu` VALUES (1, 166);
INSERT INTO `admin_role_menu` VALUES (1, 115);
INSERT INTO `admin_role_menu` VALUES (1, 132);
INSERT INTO `admin_role_menu` VALUES (1, 133);
INSERT INTO `admin_role_menu` VALUES (1, 135);
INSERT INTO `admin_role_menu` VALUES (1, 134);
INSERT INTO `admin_role_menu` VALUES (1, 126);
INSERT INTO `admin_role_menu` VALUES (1, 159);
INSERT INTO `admin_role_menu` VALUES (1, 116);
INSERT INTO `admin_role_menu` VALUES (1, 117);
INSERT INTO `admin_role_menu` VALUES (1, 119);
INSERT INTO `admin_role_menu` VALUES (1, 120);
INSERT INTO `admin_role_menu` VALUES (1, 121);
INSERT INTO `admin_role_menu` VALUES (1, 122);
INSERT INTO `admin_role_menu` VALUES (1, 123);
INSERT INTO `admin_role_menu` VALUES (1, 118);
INSERT INTO `admin_role_menu` VALUES (1, 125);
INSERT INTO `admin_role_menu` VALUES (1, 167);
INSERT INTO `admin_role_menu` VALUES (1, 168);
INSERT INTO `admin_role_menu` VALUES (1, 169);
COMMIT;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `status` char(1) NOT NULL COMMENT '状态 0锁定 1有效',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近访问时间',
  `sex` char(1) DEFAULT NULL COMMENT '性别 0男 1女 2保密',
  `is_tab` char(1) DEFAULT NULL COMMENT '是否开启tab，0关闭 1开启',
  `theme` varchar(10) DEFAULT NULL COMMENT '主题',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of admin_user
-- ----------------------------
BEGIN;
INSERT INTO `admin_user` VALUES (1, 'admin', '5993868af946054ce518e4e9ab7ef939', 1, 'admin@qq.com', '17788888888', '1', '2019-06-14 20:39:22', '2019-07-13 07:38:23', '2019-07-14 22:38:32', '0', '1', 'white', 'ubnKSIfAJTxIgXOKlciN.png', '我是帅比作者。');
INSERT INTO `admin_user` VALUES (3, 'Reina', '1461afff857c02afbfb768aa3771503d', 4, 'Reina@hotmail.com', '17711111111', '0', '2019-06-14 21:07:38', '2019-06-14 21:09:06', '2019-06-14 21:08:26', '1', '1', 'black', '5997fedcc7bd4cffbd350b40d1b5b987.jpg', '由于公款私用，已被封禁。');
COMMIT;

-- ----------------------------
-- Table structure for admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- ----------------------------
-- Records of admin_user_role
-- ----------------------------
BEGIN;
INSERT INTO `admin_user_role` VALUES (3, 77);
INSERT INTO `admin_user_role` VALUES (1, 1);
COMMIT;

-- ----------------------------
-- Function structure for findDeptChildren
-- ----------------------------
DROP FUNCTION IF EXISTS `findDeptChildren`;
delimiter ;;
CREATE FUNCTION `admin_base`.`findDeptChildren`(rootId INT)
 RETURNS varchar(4000) CHARSET utf8
BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempChd VARCHAR(4000);
    SET sTemp = '$';
    SET sTempChd = CAST(rootId as CHAR);
    WHILE sTempChd is not null DO
        SET sTemp = CONCAT(sTemp,',',sTempChd);
    SELECT GROUP_CONCAT(id) INTO sTempChd FROM admin_dept
    WHERE FIND_IN_SET(parent_id,sTempChd)>0;
END WHILE;
RETURN sTemp;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for findMenuChildren
-- ----------------------------
DROP FUNCTION IF EXISTS `findMenuChildren`;
delimiter ;;
CREATE FUNCTION `admin_base`.`findMenuChildren`(rootId INT)
 RETURNS varchar(4000) CHARSET utf8
BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempChd VARCHAR(4000);
    SET sTemp = '$';
    SET sTempChd = CAST(rootId as CHAR);
    WHILE sTempChd is not null DO
        SET sTemp = CONCAT(sTemp,',',sTempChd);
    SELECT GROUP_CONCAT(id) INTO sTempChd FROM admin_menu
    WHERE FIND_IN_SET(parent_id,sTempChd)>0;
END WHILE;
RETURN sTemp;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
