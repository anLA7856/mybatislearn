create database df;

use df;

CREATE TABLE `user` (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `email` varchar(50)  DEFAULT NULL COMMENT 'email',
  `password` varchar(255)  DEFAULT NULL COMMENT 'password',
  `actived` int2 DEFAULT NULL COMMENT 'actived',
  `activate_code` varchar(255)  DEFAULT NULL COMMENT 'activateCode',
  `join_time` varchar(50)  DEFAULT NULL COMMENT 'joinTime',
  `username` varchar(50)  DEFAULT NULL COMMENT 'username',
  `head_url` varchar(50)  DEFAULT NULL COMMENT 'headUrl',
	`position` varchar(50)  DEFAULT NULL COMMENT 'position',
	`school` varchar(50)  DEFAULT NULL COMMENT 'school',
	`job` varchar(50)  DEFAULT NULL COMMENT 'job',
  `like_count` int DEFAULT NULL COMMENT 'likeCount',
  `post_count` int DEFAULT NULL COMMENT 'postCount',
	`scan_count` int DEFAULT NULL COMMENT 'scanCount',
	`follow_count` int DEFAULT NULL COMMENT 'followCount',
	`follower_count` int DEFAULT NULL COMMENT 'followerCount',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=143722 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

INSERT INTO `df`.`user`(`uid`, `email`, `password`, `actived`, `activate_code`, `join_time`, `username`, `head_url`, `position`, `school`, `job`, `like_count`, `post_count`, `scan_count`, `follow_count`, `follower_count`) VALUES (1, 'hello@gmail.com', '1231456', 1, 'lksdfjoaisndfasfdlkj', '2016-10-10', 'anLA7856', 'http:serser', 'shanghai', 'jialidun', 'coder', 2, 3, 4, 5, 6);
