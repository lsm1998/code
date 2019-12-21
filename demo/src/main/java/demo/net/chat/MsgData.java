/*
 * 作者：刘时明
 * 时间：2019/12/21-11:13
 * 作用：
 */
package demo.net.chat;

import lombok.Data;

import java.io.Serializable;

@Data
public class MsgData<E> implements Serializable
{
    private Integer code;
    private Integer type;
    private Integer target;
    private E data;
}
