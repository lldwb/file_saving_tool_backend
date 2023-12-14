-- 用户表
CREATE TABLE user
(
    user_id         INT         NOT NULL AUTO_INCREMENT,
    user_name       VARCHAR(15) NOT NULL,
    user_password   VARCHAR(15) NOT NULL,
    user_email      VARCHAR(30) NOT NULL,
    user_permission INT         NOT NULL DEFAULT 0,
    user_state      INT         NOT NULL,
    create_time     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id)
);

-- 客户端表
CREATE TABLE client
(
    client_id    INT         NOT NULL AUTO_INCREMENT,
    client_uuid  CHAR(32)    NOT NULL,
    client_state INT         NOT NULL,
    user_id      VARCHAR(30) NOT NULL,
    create_time  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (client_id)
);

-- 登录风控表
CREATE TABLE login_risk_control
(
    login_risk_control_id      INT         NOT NULL AUTO_INCREMENT,
    login_risk_control_type    INT         NOT NULL,
    login_risk_control_details VARCHAR(30) NOT NULL,
    login_risk_control_result  INT         NOT NULL,
    user_id                    INT         NOT NULL,
    create_time                DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time                DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (login_risk_control_id)
);

-- 分享日志表
CREATE TABLE share_log
(
    share_log_id        INT         NOT NULL AUTO_INCREMENT,
    share_log_authority VARCHAR(30) NOT NULL,
    file_info_id        INT         NOT NULL,
    directory_info_id   INT         NOT NULL,
    user_id             INT         NOT NULL,
    create_time         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (share_log_id)
);

-- 操作日志表
CREATE TABLE operation_log
(
    operation_log_id   INT         NOT NULL AUTO_INCREMENT,
    operation_log_name VARCHAR(30) NOT NULL,
    operation_log_path CHAR(32)    NOT NULL,
    operation_log_type INT         NOT NULL,
    operation_log_size BIGINT      NOT NULL,
    file_info_id       INT         NOT NULL,
    directory_info_id  INT         NOT NULL,
    user_id            INT         NOT NULL,
    create_time        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (operation_log_id)
);

-- 路径映射表
CREATE TABLE path_mapping
(
    path_mapping_id         INT         NOT NULL AUTO_INCREMENT,
    path_mapping_local_path VARCHAR(30) NOT NULL,
    directory_info_id       INT         NOT NULL,
    path_mapping_type       INT         NOT NULL,
    client_id               INT         NOT NULL,
    user_id                 INT         NOT NULL,
    create_time             DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time             DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (path_mapping_id)
);

-- 文件信息表
CREATE TABLE file_info
(
    file_info_id      INT         NOT NULL AUTO_INCREMENT,
    file_info_name    VARCHAR(30) NOT NULL,
    file_info_path    CHAR(32)    NOT NULL,
    file_info_state   INT         NOT NULL DEFAULT 1,
    file_info_size    BIGINT      NOT NULL,
    directory_info_id INT         NOT NULL,
    user_id           INT         NOT NULL,
    create_time       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (file_info_id)
);

-- 文件夹信息表
CREATE TABLE directory_info
(
    directory_info_id        INT         NOT NULL AUTO_INCREMENT,
    directory_info_name      VARCHAR(30) NOT NULL,
    directory_info_father_id INT,
    directory_info_state     INT         NOT NULL DEFAULT 1,
    user_id                  INT         NOT NULL,
    create_time              DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time              DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (directory_info_id)
);
