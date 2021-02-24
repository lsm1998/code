/**
 * 作者：刘时明
 * 时间：2021/2/24
 */
package com.lsm1998.mp.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@TableName("t_user")
@Data
public class User implements Serializable
{
    @TableId
    private Long id;

    private String nickname;

    private List<Subscribe> subscribeList;
}
