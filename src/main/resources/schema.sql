-- zeus.t_data_change_record definition

CREATE TABLE `t_data_change_record`
(
    `id`                  varchar(36)  NOT NULL COMMENT '主键',
    `source_id`           varchar(36)  NOT NULL COMMENT '表所在的数据源id',
    `db_name`             varchar(30)           DEFAULT NULL COMMENT '表所在的库',
    `tbl_name`            varchar(100)          DEFAULT NULL COMMENT '表名',
    `col_name`            varchar(100)          DEFAULT NULL COMMENT '字段名(对整表的操作该字段可为空)',
    `change_type`         int(1)       NOT NULL COMMENT '变更类型1:新增；2:删除；3:修改；4:整表删除',
    `change_time`         datetime     NOT NULL DEFAULT current_timestamp() COMMENT '变更时间',
    `change_content`      varchar(500) NOT NULL COMMENT '变更内容',
    `sql_content`         text                  DEFAULT NULL COMMENT '数据库执行语句',
    `target_table_status` int(1)       NOT NULL DEFAULT 0 COMMENT '目标表状态0:初始状态；1:成功；2:失败；3：不执行',
    `json_status`         int(1)       NOT NULL DEFAULT 0 COMMENT '构建Json状态0:初始状态；1:成功；2:失败；3：不执行',
    PRIMARY KEY (`id`),
    KEY `idx_change_time` (`change_time`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='元数据变更记录表';


-- zeus.t_json_info definition

CREATE TABLE `t_json_info`
(
    `id`          varchar(36)  NOT NULL COMMENT '子任务id',
    `name`        varchar(500) NOT NULL COMMENT 'JSON文件名',
    `uid`         varchar(36)  NOT NULL COMMENT '创建人id',
    `title`       varchar(100)          DEFAULT NULL COMMENT 'JSON文件描述',
    `content`     text         NOT NULL COMMENT 'JSON',
    `ctime`       datetime     NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `mtime`       datetime              DEFAULT NULL COMMENT '更新时间',
    `task_id`     varchar(36)  NOT NULL COMMENT '主任务表id',
    `sub_task_id` varchar(36)           DEFAULT '' COMMENT '子任务id',
    PRIMARY KEY (`id`),
    KEY `idx_name` (`name`) USING BTREE,
    KEY `idx_uid` (`uid`) USING BTREE,
    KEY `idx_ctime` (`ctime`) USING BTREE,
    KEY `idx_task_id` (`task_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='JSON信息表';


-- zeus.t_new_table_info definition

CREATE TABLE `t_new_table_info`
(
    `source_id`     varchar(36)  NOT NULL COMMENT '表所在的数据源',
    `db_name`       varchar(100) NOT NULL COMMENT '表所在的库',
    `tbl_name`      varchar(100) NOT NULL COMMENT '表名',
    `col_name`      varchar(100) NOT NULL COMMENT '字段名',
    `col_type`      varchar(20)  NOT NULL COMMENT '字段类型',
    `col_length`    int(2)       NOT NULL COMMENT '字段长度',
    `col_precision` int(2)       NOT NULL DEFAULT 0 COMMENT '精度(如果是浮点类型，则该字段表示小数位数，其他类型则为0)',
    `col_pos`       int(2)       NOT NULL DEFAULT 0 COMMENT '字段位置编号(字段在该表中的位置编号，默认从0开始，可从源头读取)',
    `is_null`       int(1)       NOT NULL COMMENT '是否允许为空0:否；1:是',
    `col_notes`     varchar(200)          DEFAULT NULL COMMENT '字段注释',
    `sql_type_code` varchar(6)   NOT NULL COMMENT '字段类型编号',
    PRIMARY KEY (`source_id`, `db_name`, `col_name`, `tbl_name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='数据库表信息';


-- zeus.t_source_config definition

CREATE TABLE `t_source_config`
(
    `id`                        varchar(36) NOT NULL COMMENT '数据源ID',
    `name`                      varchar(36) NOT NULL COMMENT '数据源',
    `user`                      varchar(10)          DEFAULT NULL COMMENT '数据源账号',
    `pass`                      varchar(100)         DEFAULT NULL COMMENT '数据源密码',
    `dsn`                       varchar(255)         DEFAULT NULL COMMENT 'Jdbc连接串',
    `dtype`                     int(1)      NOT NULL COMMENT '数据库类型0.识别不到类型;1.RDBMS;2.Oracle;3.MySQL;4.SQLServer;5.PostgreSQL;6.ClickHouse7.Hive/HDFS;8.DBF;9.Hbase2.0withPhoenix;10.Hbase1.0withPhoenix',
    `dstatus`                   int(1)      NOT NULL DEFAULT 1 COMMENT '数据源状态0：删除；1:启用（默认值）；2:停用',
    `path`                      varchar(100)         DEFAULT NULL COMMENT '要读取的文件路径',
    `defaultFS`                 varchar(100)         DEFAULT NULL COMMENT 'Hadoop hdfs 文件系统namenode节点地址',
    `have_kerberos`             int(1)               DEFAULT NULL COMMENT '是否有Kerberos认证0:否；1:是',
    `kerberos_keytab_file_path` varchar(100)         DEFAULT NULL COMMENT 'Kerberos认证 keytab文件路径',
    `kerberos_principal`        text                 DEFAULT NULL COMMENT 'Kerberos认证Principal名',
    `hadoop_config`             varchar(500)         DEFAULT NULL COMMENT 'Hadoop 相关的高级参数',
    `hive_user_name`            varchar(20)          DEFAULT NULL COMMENT 'hive用户名',
    `hive_pass`                 varchar(20)          DEFAULT NULL COMMENT 'hive密码',
    `hive_connect_str`          varchar(255)         DEFAULT NULL COMMENT 'hive 连接串',
    `is_enable_ha`              int(1)               DEFAULT NULL COMMENT '是否启用HA0:否；1:是',
    `name_services`             varchar(100)         DEFAULT NULL COMMENT '集群服务名',
    `name_nodes`                varchar(500)         DEFAULT NULL COMMENT 'namenode名称，用逗号“,”分隔',
    `name_node_rpc`             varchar(1000)        DEFAULT NULL COMMENT '节点主机名，用逗号“,”分隔',
    `ctime`                     datetime    NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `mtime`                     datetime             DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_name` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='数据源配置表';


-- zeus.t_sub_task_info definition

CREATE TABLE `t_sub_task_info`
(
    `id`                    varchar(36)  NOT NULL COMMENT 'JSON关联表ID',
    `source_id`             varchar(36)  NOT NULL COMMENT '源数据源ID',
    `source_db`             varchar(100) NOT NULL COMMENT '源数据库名',
    `source_tbl`            varchar(100) NOT NULL COMMENT '源数据库表名',
    `target_id`             varchar(36)  NOT NULL COMMENT '目标数据源ID',
    `target_db`             varchar(100) NOT NULL COMMENT '目标数据库名',
    `target_tbl`            varchar(100) NOT NULL COMMENT '目标数据库表名',
    `is_add_target_tbl`     int(1)       NOT NULL DEFAULT 0 COMMENT '是否新增目标表1:是；0:否',
    `add_target_tbl_status` int(1)       NOT NULL DEFAULT 0 COMMENT '新增目标表状态0:初始状态；1:成功；2:失败',
    `add_target_tbl_reason` text                  DEFAULT NULL COMMENT '新增目标表失败原因',
    `add_field_status`      int(1)       NOT NULL DEFAULT 0 COMMENT '字段信息入库状态0:初始状态；1:成功；2:失败',
    `add_field_reason`      text                  DEFAULT NULL COMMENT '字段信息入库失败原因',
    `add_json_status`       int(1)       NOT NULL DEFAULT 0 COMMENT '创建json状态0:初始状态；1:成功；2:失败',
    `add_json_reason`       text                  DEFAULT NULL COMMENT '创建json状态失败原因',
    `target_mapping_status` int(1)       NOT NULL DEFAULT 1 COMMENT '目标数据源类型映射状态1:成功；2:失败',
    `target_mapping_reason` text                  DEFAULT NULL COMMENT '目标数据源类型映射失败原因',
    `task_id`               varchar(36)  NOT NULL COMMENT '任务主表ID',
    `collect_type`          int(1)       NOT NULL DEFAULT 1 COMMENT '采集类型1:采集;2:数据服务',
    `ctime`                 datetime     NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `mtime`                 datetime              DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_task_id` (`task_id`) USING BTREE,
    KEY `s_index` (`source_db`, `source_tbl`, `target_db`, `target_tbl`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='任务子表';


-- zeus.t_system_config definition

CREATE TABLE `t_system_config`
(
    `id`          varchar(36)  NOT NULL COMMENT '主键',
    `code`        varchar(100) NOT NULL COMMENT '配置code',
    `name`        varchar(50)  NOT NULL COMMENT '配置名',
    `is_open`     int(1)       NOT NULL DEFAULT 1 COMMENT '是否开启1:开启；0:关闭',
    `cycle`       int(1)                DEFAULT 1 COMMENT '重复周期0:无；1:每天',
    `content`     varchar(100) NOT NULL COMMENT '配置内容',
    `config_time` varchar(50)           DEFAULT NULL COMMENT '配置的时间',
    `ctime`       datetime     NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `mtime`       datetime              DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='系统配置表';


-- zeus.t_table_info definition

CREATE TABLE `t_table_info`
(
    `source_id`         varchar(36)  NOT NULL COMMENT '表所在的数据源',
    `db_name`           varchar(100) NOT NULL COMMENT '表所在的库',
    `tbl_name`          varchar(100) NOT NULL COMMENT '表名',
    `col_name`          varchar(100) NOT NULL COMMENT '字段名',
    `col_type`          varchar(20)  NOT NULL COMMENT '字段类型',
    `col_length`        int(2)       NOT NULL COMMENT '字段长度',
    `col_precision`     int(2)       NOT NULL DEFAULT 0 COMMENT '精度(如果是浮点类型，则该字段表示小数位数，其他类型则为0)',
    `col_pos`           int(2)       NOT NULL DEFAULT 0 COMMENT '字段位置编号(字段在该表中的位置编号，默认从0开始，可从源头读取)',
    `is_null`           int(1)       NOT NULL COMMENT '是否允许为空0:否；1:是',
    `col_notes`         varchar(200)          DEFAULT NULL COMMENT '字段注释',
    `col_del_status`    int(1)                DEFAULT 1 COMMENT '字段删除状态默认为1，删除设置为2',
    `table_main_id`     varchar(36)           DEFAULT '0' COMMENT '数据库表信息主表id',
    `create_table_type` varchar(20)           DEFAULT NULL COMMENT '建表类型映射',
    `sub_task_id`       varchar(36)  NOT NULL DEFAULT '' COMMENT '子任务id',
    PRIMARY KEY (`source_id`, `db_name`, `col_name`, `tbl_name`, `sub_task_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='数据库表信息';


-- zeus.t_table_main_info definition

CREATE TABLE `t_table_main_info`
(
    `id`          varchar(36)  NOT NULL COMMENT '主键',
    `source_id`   varchar(36)  NOT NULL COMMENT '表所在的数据源',
    `db_name`     varchar(30)  NOT NULL COMMENT '表所在的库',
    `tbl_name`    varchar(100) NOT NULL COMMENT '表名',
    `ctime`       datetime     NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `mtime`       datetime              DEFAULT NULL COMMENT '更新时间',
    `tbl_type`    int(1)       NOT NULL COMMENT '1:源表；2:目标表',
    `sub_task_id` varchar(36)           DEFAULT NULL COMMENT '子任务编号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='数据库表信息主表';


-- zeus.t_task_info definition

CREATE TABLE `t_task_info`
(
    `id`          varchar(36)          DEFAULT NULL,
    `task_status` int(255)    NOT NULL DEFAULT 0 COMMENT '任务总状态状态0:初始状态；1:字段信息入库完成；2:任务全部完成',
    `uid`         varchar(36) NOT NULL COMMENT '用户id',
    `ctime`       datetime    NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `mtime`       datetime             DEFAULT NULL COMMENT '更新时间',
    KEY `idx_uid` (`uid`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='任务主表';


-- zeus.t_type_info definition

CREATE TABLE `t_type_info`
(
    `dtype`             int(1)      NOT NULL COMMENT '数据库类型',
    `col_type`          varchar(20) NOT NULL COMMENT '字段类型',
    `sql_type`          varchar(30) NOT NULL COMMENT '源数据类型',
    `sql_type_code`     varchar(6)  DEFAULT NULL COMMENT '源数据类型编码',
    `create_table_type` varchar(20) DEFAULT NULL,
    PRIMARY KEY (`dtype`, `sql_type`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='数据库类型信息';


-- zeus.t_user definition

CREATE TABLE `t_user`
(
    `id`       varchar(36)  NOT NULL COMMENT '用户ID',
    `username` varchar(20)  NOT NULL COMMENT '登录名',
    `pass`     varchar(100) NOT NULL COMMENT '登录密码',
    `utype`    int(11)      NOT NULL DEFAULT 1 COMMENT '用户角色1：管理员（注册时默认角色）',
    `ctime`    datetime     NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `mtime`    datetime              DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='用户表';