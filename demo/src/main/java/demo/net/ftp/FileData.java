/*
 * 作者：刘时明
 * 时间：2019/12/21-16:41
 * 作用：
 */
package demo.net.ftp;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileData implements Serializable
{
    private String src;
    private String dist;
    // 序号
    private Integer seq;
    // 每个包的大小
    private Integer size;
    private Integer maxSize;
    private byte[] data;
}
