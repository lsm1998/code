create table t_user(
    id bigint(20) not null,
    username varchar(20) not null,
    password varchar(64) not null,
    face_image varchar(155) not null,
    face_image_big varchar(155) not null,
    nickname varchar(20) not null,
    qrcode varchar(155) not null,
    cid varchar(64),
	`create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
	PRIMARY KEY (`id`) USING BTREE
)

create table t_msg_request(
    id bigint(20) not null,
    send_id bigint(20) not null,
    accept_id bigint(20) not null,
    `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
	PRIMARY KEY (`id`) USING BTREE
)

create table t_friends(
    id bigint(20) not null,
    user_id bigint(20) not null,
    friend_id bigint(20) not null,
    `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
	PRIMARY KEY (`id`) USING BTREE
)

create table t_msg(
    id bigint(20) not null,
    send_id bigint(20) not null,
    accept_id bigint(20) not null,
    msg varchar(255) not null,
    sign_flag int(1) not null,
    `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
	PRIMARY KEY (`id`) USING BTREE
)