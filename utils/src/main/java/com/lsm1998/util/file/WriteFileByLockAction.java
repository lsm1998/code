package com.lsm1998.util.file;

import java.io.FileOutputStream;

/**
 * @作者：刘时明
 * @时间：2019/6/4-22:04
 * @说明：文件数据写入事件接口
 */
@FunctionalInterface
public interface WriteFileByLockAction
{
    void lockAction(FileOutputStream output);
}
