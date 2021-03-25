/**
 * 作者：刘时明
 * 时间：2021/2/24
 */
package com.lsm1998.middle.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("t_subscribe")
@Data
public class Subscribe implements Serializable
{
    @TableId
    private Long id;

    private Long userId;

    private String content;
}
