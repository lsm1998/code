package com.lsm1998.auto.database.enums;

import lombok.Getter;

/**
 * 错误信息枚举
 */
@Getter
public enum ErrorEnum {
    NOT_GET_TABLE("未获取到任何表."),
    NOT_GET_CONNECTION("无法获取数据库连接."),
    NOT_GET_COMMENT("无法获取表%s的备注信息."),
    NOT_GET_PRIMARY_KEYS("无法获取表%s内主键列表."),
    NOT_GET_COLUMN("无法获取表%s内的数据列列表"),
    NOT_ALLOW_DB_TYPE("不支持的数据库类型."),
    NOT_ALLOW_ENGINE("不支持的驱动模板类型."),
    NO_BUILDER_TABLE("请配置tables或者generatorByPattern参数后使用自动生成.");

    ErrorEnum(String message) {
        this.message = message;
    }

    private String message;
}
