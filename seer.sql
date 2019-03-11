create database if Not exists `seer`;
use `seer`;
create table if Not exists `seer_user`				#用户表
(
	`user_id` int primary key auto_increment, 	# 用户id
	`user_name` varchar(20),							#用户名
	`user_pwd` char(32),									#用户密码
	`user_email` varchar(40),							#用户邮箱
	`user_birthday` date DEFAULT('1991-1-1'),		#用户生日
	`user_portrait_url` varchar(100),				#用户头像路径
	`user_background_img` varchar(100)				#用户个人主页背景图路径
) AUTO_INCREMENT = 1000;

create table if Not exists `seer_industry`		#行业列表
(
	`industry_id` int primary key auto_increment,	#行业自增长id
	`industry_name` varchar(20)							#行业名称
);

##角色权限
		##用户角色表 
create table if Not exists `seer_role`
(
	id int primary key auto_increment, #自动增长id
	name varchar(16) #角色名称
);
		##用户角色关联表 
create table if Not exists `seer_roles_users`
(
	id int primary key auto_increment, #自动增长id
	user_id int, 							#用户id
	role_id int							#角色id
);

#权限表
create table if Not exists `seer_role_power`
(
	id int primary key auto_increment, #自动增长id
	name varchar(32) #权限名
);

#角色权限关联表
create table if Not exists `seer_role_powers`
(
	id int primary key auto_increment, #自增长id
	role_id int,								#角色id
	power_id int 								#权限id
);

create table if Not exists `seer_interest`		#问题类型表
(
	`interest_id` int primary key auto_increment,	#问题类型自增长ID
	`interset_name` varchar(30),							#问题类型名称
	`interset_img` varchar(100),							#问题类型描述图片
	`interset_describe` text								#问题类型描述
);

create table if Not exists `seer_interest_follow`	#用户关注问题类型列表
(
	`follow_id` int primary key auto_increment,		#关注默认增长ID
	`follow_interest_id` int,								#关注的类型id
	`follow_user_id` int										#关注问题类型的用户id
);

create table if Not exists `seer_issue`			#用户提问表
(
	`issue_id` int primary key auto_increment,	#问题自增长ID
	`issue_interest_id` int,							#问题所属分类ID
	`issue_title` varchar(200),						#问题标题
	`issue_user_id` int,									#提问者的id
	`issue_content` text,								#问题补充
	`issue_anonymous` tinyint DEFAULT 0,			#是否匿名，默认匿名为否
	`issue_essence` tinyint DEFAULT 0,				#是否设置为精华，默认为否
	`issue_ban` tinyint DEFAULT 0,					#是否禁止回答，默认为否
	`issue_create_time` datetime DEFAULT NOW(),	#提出问题的时间
	`issue_reply` int,									#提问的回复数量
	`issue_views` int										#提问的浏览数
);

create table if Not exists `seer_reply`			#回答列表
(
	`reply_id` int primary key auto_increment,	#回答自增长id
	`reply_issue_id` int,								#回答所属问题的id
	`reply_reply_id` int DEFAULT NULL,				#回答的父ID
	`reply_tips` varchar(200) DEFAULT NULL,		#官方辟谣
	`reply_content` text,								#回答的内容
	`reply_user_id` int,									#回答者的ID
	`reply_time` datetime DEFAULT NOW(),			#回答的时间
	`reply_praise` int DEFAULT 0,						#回答点赞数量
	`reply_invalid` tinyint DEFAULT 0				#回答是否被折叠，默认是否
);

create table if Not exists `seer_reply_praise`	#点赞列表
(
	`praise_id` int primary key auto_increment,	#自增长id
	`praise_reply_id` int,								#点赞的回答id
	`praise_user_id` int,								#点赞的用户id
	`praise_time` datetime DEFAULT NOW()			#点赞时间
);

create table if Not exists `seer_ral`			#首页推荐
(
	`ral_id` int primary key auto_increment,	#推荐自增长ID
	`ral_reply_id` int,								#被推荐的回答ID
	`rel_date` datetime DEFAULT NOW()			#推荐到首页的时间
);

create table if Not exists `seer_msg_type`	#消息类型
(
	`msg_type_id` int primary key auto_increment,	#消息类型自增长id
	`msg_type_name` varchar(20)							#消息类型的名称
);

create table if Not exists `seer_msg` #消息列表
(
	`msg_id` int primary key auto_increment,	#消息自增长id
	`msg_user_from_id` int,							#发送消息者id
	`msg_user_accept_id` int,						#消息接受者id
	`msg_create_time` datetime,					#消息发送时间
	`msg_type_id` int,								#消息类型
	`msg_content` varchar(200)						#消息内容
);

create table if Not exists `seer_imgPath`	#图片储存表
(
	`img_id` int primary key auto_increment,	#图片自增长id
	`img_path` varchar(200),						#图片路径
	`img_user_id` int									#储存图片用户
	
);

#角色信息
insert into seer_role(name) values('user'),('admin');