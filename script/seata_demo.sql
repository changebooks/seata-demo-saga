CREATE TABLE IF NOT EXISTS `seata_state_machine_def`
(
    `id`               VARCHAR(32)  NOT NULL COMMENT 'id',
    `name`             VARCHAR(128) NOT NULL COMMENT 'name',
    `tenant_id`        VARCHAR(32)  NOT NULL COMMENT 'tenant id',
    `app_name`         VARCHAR(32)  NOT NULL COMMENT 'application name',
    `type`             VARCHAR(20) COMMENT 'state language type',
    `comment_`         VARCHAR(255) COMMENT 'comment',
    `ver`              VARCHAR(16)  NOT NULL COMMENT 'version',
    `gmt_create`       DATETIME(3)  NOT NULL COMMENT 'create time',
    `status`           VARCHAR(2)   NOT NULL COMMENT 'status(AC:active|IN:inactive)',
    `content`          TEXT COMMENT 'content',
    `recover_strategy` VARCHAR(16) COMMENT 'transaction recover strategy(compensate|retry)',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `seata_state_machine_inst`
(
    `id`                  VARCHAR(128) NOT NULL COMMENT 'id',
    `machine_id`          VARCHAR(32)  NOT NULL COMMENT 'state machine definition id',
    `tenant_id`           VARCHAR(32)  NOT NULL COMMENT 'tenant id',
    `parent_id`           VARCHAR(128) COMMENT 'parent id',
    `gmt_started`         DATETIME(3)  NOT NULL COMMENT 'start time',
    `business_key`        VARCHAR(48) COMMENT 'business key',
    `start_params`        TEXT COMMENT 'start parameters',
    `gmt_end`             DATETIME(3) COMMENT 'end time',
    `excep`               BLOB COMMENT 'exception',
    `end_params`          TEXT COMMENT 'end parameters',
    `status`              VARCHAR(2) COMMENT 'status(SU succeed|FA failed|UN unknown|SK skipped|RU running)',
    `compensation_status` VARCHAR(2) COMMENT 'compensation status(SU succeed|FA failed|UN unknown|SK skipped|RU running)',
    `is_running`          TINYINT(1) COMMENT 'is running(0 no|1 yes)',
    `gmt_updated`         DATETIME(3)  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unikey_buz_tenant` (`business_key`, `tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `seata_state_inst`
(
    `id`                       VARCHAR(48)  NOT NULL COMMENT 'id',
    `machine_inst_id`          VARCHAR(128) NOT NULL COMMENT 'state machine instance id',
    `name`                     VARCHAR(128) NOT NULL COMMENT 'state name',
    `type`                     VARCHAR(20) COMMENT 'state type',
    `service_name`             VARCHAR(128) COMMENT 'service name',
    `service_method`           VARCHAR(128) COMMENT 'method name',
    `service_type`             VARCHAR(16) COMMENT 'service type',
    `business_key`             VARCHAR(48) COMMENT 'business key',
    `state_id_compensated_for` VARCHAR(50) COMMENT 'state compensated for',
    `state_id_retried_for`     VARCHAR(50) COMMENT 'state retried for',
    `gmt_started`              DATETIME(3)  NOT NULL COMMENT 'start time',
    `is_for_update`            TINYINT(1) COMMENT 'is service for update',
    `input_params`             TEXT COMMENT 'input parameters',
    `output_params`            TEXT COMMENT 'output parameters',
    `status`                   VARCHAR(2)   NOT NULL COMMENT 'status(SU succeed|FA failed|UN unknown|SK skipped|RU running)',
    `excep`                    BLOB COMMENT 'exception',
    `gmt_updated`              DATETIME(3) COMMENT 'update time',
    `gmt_end`                  DATETIME(3) COMMENT 'end time',
    PRIMARY KEY (`id`, `machine_inst_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS undo_log;
CREATE TABLE undo_log
(
    branch_id     bigint(20)   NOT NULL COMMENT 'branch transaction id',
    xid           varchar(128) NOT NULL COMMENT 'global transaction id',
    context       varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    rollback_info longblob     NOT NULL COMMENT 'rollback info',
    log_status    int(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    log_created   datetime(6)  NOT NULL COMMENT 'create datetime',
    log_modified  datetime(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE INDEX ux_undo_log (xid, branch_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = 'AT transaction mode undo table';

DROP TABLE IF EXISTS account;
CREATE TABLE account
(
    user_id        int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
    usable_balance int(11) unsigned NOT NULL DEFAULT '0' COMMENT '可用余额，单位：分',
    PRIMARY KEY (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '预存款';

INSERT INTO account
VALUES (1, 100);

DROP TABLE IF EXISTS account_flow;
CREATE TABLE account_flow
(
    id             int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水id',
    user_id        int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
    change_type    int(11) unsigned NOT NULL DEFAULT '0' COMMENT '改变方式，0-未知、1-增加、2-扣减',
    usable_balance int(11) unsigned NOT NULL DEFAULT '0' COMMENT '增减的可用余额，单位：分',
    order_id       int(11) unsigned NOT NULL DEFAULT '0' COMMENT '订单号',
    created_at     datetime         NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE INDEX uk_idempotent (user_id, change_type, order_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '预存款流水';

DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory
(
    product_id int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
    stock_num  int(11) unsigned NOT NULL DEFAULT '0' COMMENT '当前库存',
    PRIMARY KEY (product_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '库存';

INSERT INTO inventory
VALUES (1, 100);

DROP TABLE IF EXISTS inventory_flow;
CREATE TABLE inventory_flow
(
    id          int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水id',
    product_id  int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
    change_type int(11) unsigned NOT NULL DEFAULT '0' COMMENT '改变方式，0-未知、1-入库、2-出库',
    stock_num   int(11) unsigned NOT NULL DEFAULT '0' COMMENT '出入的库存',
    order_id    int(11) unsigned NOT NULL DEFAULT '0' COMMENT '订单号',
    created_at  datetime         NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE INDEX uk_idempotent (product_id, change_type, order_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '库存流水';

DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id          int(11) unsigned NOT NULL DEFAULT '0' COMMENT '订单号',
    user_id     int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
    product_id  int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
    product_num int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品数',
    pay_num     int(11) unsigned NOT NULL DEFAULT '0' COMMENT '支付金额，单位：分',
    is_deleted  int(11) unsigned NOT NULL DEFAULT '0' COMMENT '删除？1-是、0-否',
    created_at  datetime         NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted_at  datetime         NULL     DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '订单';
