# shiro-admin

## 说明

本项目基于 [wuyouzhuguli/FEBS-Shiro](https://github.com/wuyouzhuguli/FEBS-Shiro) 魔改而来，以适应更通用的企业应用，再次对 `FEBS` 参与开发的人员表示感谢。
感谢你们的辛苦劳动与付出。

**主要调整**

- 更改项目命名，使其更通用，方便企业使用
- 项目中的错误及不规范调整
- 文件字符编码问题
- 升级依赖的类库
- 其他...
- 鉴于改动的不兼容性，本项目不 pr 到原项目

> 后记:  
>> 本项目 fork 时 FEBS 没有说明其所使用 License，但 badge 图标标识为 MIT，后原作者改为Apache License 2.0，  
>> 尽管如此，予在此留下说明，列出本项目对于原作品的更改，亦是对作者的尊重。 

> 本地运行：http://127.0.0.1:8080/login，
> 管理员账号：admin, 密码：s6321

--------------

![https://img.shields.io/badge/license-MIT-blue.svg?longCache=true&style=flat-square](https://img.shields.io/badge/license-MIT-blue.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/download-1k%2Fm-green.svg?longCache=true&style=flat-square](https://img.shields.io/badge/download-1k%2Fm-green.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/springboot-2.1.3-yellow.svg?style=flat-square](https://img.shields.io/badge/springboot-2.1.3-yellow.svg?style=flat-square)
![https://img.shields.io/badge/shiro-1.4.0-orange.svg?longCache=true&style=flat-square](https://img.shields.io/badge/shiro-1.4.0-orange.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/layui-2.5.4-brightgreen.svg?longCache=true&style=flat-square](https://img.shields.io/badge/layui-2.5.4-brightgreen.svg?longCache=true&style=flat-square)

FEBS-Shiro是一款简单高效的后台权限管理系统，使用Spring Boot，Shiro和Layui构建。FEBS意指：**F**ast，**E**asy use，**B**eautiful和**S**afe。相信无论作为企业级应用，私活开发脚手架或者权限系统构建学习，FEBS-Shiro都会是一个不错的选择。

### 演示

演示环境账号密码：

账号 | 密码| 权限
---|---|---
scott | 1234qwer | 注册账户，拥有查看，新增权限（新增用户除外）和导出Excel权限


本地部署账号密码：

账号 | 密码| 权限
---|---|---
mrbird | 1234qwer |超级管理员，拥有所有增删改查权限
scott | 1234qwer | 注册账户，拥有查看，新增权限（新增用户除外）和导出Excel权限
micaela | 1234qwer |系统监测员，负责整个系统监控模块
Jana   | 1234qwer  |跑批人员，负责任务调度跑批模块

### 更多版本
当前分支为2.0版本，页面采用Layui全新构建，FEBS的其他版本：

名称 | 描述| 地址
---|---|---
FEBS-Shiro 2.x | Spring Boot 2.1.3 & Shiro1.4.0 权限管理系统（layui）。 | [https://github.com/wuyouzhuguli/FEBS-Shiro/tree/v2.0](https://github.com/wuyouzhuguli/FEBS-Shiro/tree/mysql)
FEBS-Shiro 1.x | Spring Boot 2.0.4 & Shiro1.4.0 权限管理系统（单页）。 | [https://github.com/wuyouzhuguli/FEBS-Shiro/tree/mysql](https://github.com/wuyouzhuguli/FEBS-Shiro/tree/mysql)
FEBS-Security | Spring Boot 2.0.4 & Spring Security 5.0.7 权限管理系统（单页）。 | [https://github.com/wuyouzhuguli/FEBS-Security](https://github.com/wuyouzhuguli/FEBS-Security)
FEBS-Vue | FEBS-Shiro前后端分离版本，前端架构采用Vue全家桶。 | [https://github.com/wuyouzhuguli/FEBS-Vue](https://github.com/wuyouzhuguli/FEBS-Vue)

### 系统模块
系统功能模块组成如下所示：
```
├─系统管理
│  ├─用户管理
│  ├─角色管理
│  ├─菜单管理
│  └─部门管理
├─系统监控
│  ├─在线用户
│  ├─系统日志
│  ├─登录日志
│  ├─Redis监控
│  ├─Redis终端
│  ├─请求追踪
│  ├─系统信息
│  │  ├─JVM信息
│  │  ├─TOMCAT信息
│  │  └─服务器信息
├─任务调度
│  ├─定时任务
│  └─调度日志
├─代码生成
│  ├─生成配置
│  ├─代码生成
└─其他模块
   ├─FEBS组件
   │  ├─表单组件
   │  ├─表单组合
   │  ├─FEBS工具
   │  ├─系统图标
   │  └─其他组件
   ├─APEX图表
   ├─高德地图
   └─导入导出
```
### 系统特点

1. 前后端请求参数校验

2. 支持Excel导入导出

3. 前端页面布局多样化，主题多样化

4. 支持多数据源，代码生成

5. 多Tab页面，适合企业应用

6. 用户权限动态刷新

7. 浏览器兼容性好，页面支持PC，Pad和移动端。

8. 代码简单，结构清晰

### 技术选型

#### 后端
- [Spring Boot 2.1.3](http://spring.io/projects/spring-boot/)
- [Mybatis-Plus](https://mp.baomidou.com/guide/)
- [MySQL 5.7.x](https://dev.mysql.com/downloads/mysql/5.7.html#downloads),[Hikari](https://brettwooldridge.github.io/HikariCP/),[Redis](https://redis.io/)
- [Shiro](http://shiro.apache.org/)

#### 前端
- [Layui 2.5.4](https://www.layui.com/)
- [Nepadmin](https://gitee.com/june000/nep-admin)
- [formSelects 4.x 多选框](https://hnzzmsf.github.io/example/example_v4.html)
- [eleTree 树组件](https://layuiextend.hsianglee.cn/eletree/)
- [formSelect.js树形下拉](https://wujiawei0926.gitee.io/treeselect/docs/doc.html)
- [Apexcharts图表](https://apexcharts.com/)

### 参与贡献
欢迎提交PR一起完善项目，以下为提PR并合并的小伙伴（排名不分先后）：

<a href="https://github.com/everhopingandwaiting">
    <img src="https://avatars3.githubusercontent.com/u/6021724?s=400&v=4" width="45px"></a>
<a href="https://github.com/mgzu">
    <img src="https://avatars1.githubusercontent.com/u/29629221?s=400&v=4" width="45px"></a>
<a href="https://github.com/yuuki80code">
    <img src="https://avatars0.githubusercontent.com/u/17798853?s=400&v=4" width="45px"></a>
<a href="https://github.com/cinsin">
        <img src="https://avatars1.githubusercontent.com/u/12856067?s=400&v=4" width="45px"></a>
<a href="https://github.com/Minnull">
    <img src="https://avatars2.githubusercontent.com/u/19608781?s=400&v=4" width="45px"></a>
<a href="https://github.com/Harrison0x80">
    <img src="https://avatars2.githubusercontent.com/u/8622915?s=400&v=4" width="45px"></a>


