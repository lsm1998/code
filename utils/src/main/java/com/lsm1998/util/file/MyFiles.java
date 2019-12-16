package com.lsm1998.util.file;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @作者：刘时明
 * @时间：2019/6/2-8:43
 * @说明：文件操作工具类
 */
public class MyFiles
{
    // 每次读取的最大字节数
    private static final int MAX_READ_SIZE = 10 * 1024;
    // static目录名称
    private static final String STATIC_DIRECTORY = "static/";

    /**
     * 重载方法，说明见调用方法
     */
    public static byte[] getBytes(String path)
    {
        return getBytes(new File(path));
    }

    /**
     * 读取文件内容
     *
     * @param file 文件
     * @return
     */
    public static byte[] getBytes(File file)
    {
        if (checkRead(file))
        {
            try (FileInputStream fis = new FileInputStream(file);
                 ByteChannel channel = fis.getChannel();
                 ByteArrayOutputStream bao = new ByteArrayOutputStream())
            {
                int len;
                ByteBuffer buffer = ByteBuffer.allocate(MAX_READ_SIZE);
                while ((len = channel.read(buffer)) != -1)
                {
                    bao.write(buffer.array(), 0, len);
                    buffer.clear();
                }
                return bao.toByteArray();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取输入流内容
     *
     * @param is
     * @return
     */
    public static byte[] getBytes(InputStream is)
    {
        try (ByteArrayOutputStream bao = new ByteArrayOutputStream())
        {
            int len;
            byte[] bytes = new byte[MAX_READ_SIZE];
            while ((len = is.read(bytes)) != -1)
            {
                bao.write(bytes, 0, len);
            }
            return bao.toByteArray();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重载方法，说明见调用方法
     * @return
     */
    public static boolean copy(String source, String target)
    {
        return copy(new File(source), new File(target));
    }

    /**
     * 拷贝文件
     *
     * @param source 源文件
     * @param target 目标文件
     * @return
     */
    public static boolean copy(File source, File target)
    {
        if (checkRead(source))
        {
            if (target.exists())
            {
                System.err.println("目标文件存在，拷贝失败");
            } else
            {
                if (createFileByNotExists(target))
                {
                    try (FileInputStream fis = new FileInputStream(source);
                         FileOutputStream fos = new FileOutputStream(target);
                         ByteChannel inChannel = fis.getChannel();
                         ByteChannel outChannel = fos.getChannel())
                    {
                        ByteBuffer buffer = ByteBuffer.allocate(MAX_READ_SIZE);

                        while ((inChannel.read(buffer)) != -1)
                        {
                            buffer.flip();
                            outChannel.write(buffer);
                            buffer.clear();
                        }
                        return true;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }


    /**
     * 检查文件是否可读
     *
     * @param file
     * @return
     */
    public static boolean checkRead(File file)
    {
        return file.exists() && file.canRead();
    }

    /**
     * 重载方法，说明见调用方法
     */
    public static boolean checkRead(String filePath)
    {
        return checkRead(new File(filePath));
    }

    /**
     * 检查文件是否可写
     *
     * @param file
     * @return
     */
    public static boolean checkWrite(File file)
    {
        return file.exists() && file.canWrite();
    }

    /**
     * 重载方法，说明见调用方法
     */
    public static boolean checkWrite(String filePath)
    {
        return checkWrite(new File(filePath));
    }

    /**
     * 使用文件锁的情况下写入数据
     *
     * @param file   文件
     * @param action 写入数据事件
     * @param append 是否追加
     */
    public static void writeFileByLock(File file, WriteFileByLockAction action, boolean append)
    {
        try (FileOutputStream fos = new FileOutputStream(file, append);
             FileChannel channel = fos.getChannel())
        {
            FileLock lock = channel.tryLock();
            action.lockAction(fos);
            lock.release();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 重载方法，说明见调用方法
     */
    public static void writeFileByLock(File file, WriteFileByLockAction action)
    {
        writeFileByLock(file, action, false);
    }

    /**
     * 不覆盖的情况下创建一个新文件
     *
     * @param file
     * @return
     */
    private static boolean createFileByNotExists(File file)
    {
        try
        {
            if (!file.exists())
            {
                file.createNewFile();
                return true;
            }
        } catch (IOException e)
        {
            System.err.println("创建文件失败，" + file.getPath() + "," + e.getMessage());
        }
        return false;
    }

    /**
     * 统计一个目录内特定后缀的文件数量
     *
     * @return
     */
    public static int countDirectoryBySuffix(File file, String suffix)
    {
        if (file.exists() && file.isDirectory())
        {

        }
        return 0;
    }

    /**
     * 重载方法，说明见调用方法
     */
    public static int countDirectoryBySuffix(String filePath, String suffix)
    {
        return countDirectoryBySuffix(new File(filePath), suffix);
    }

    /**
     * 获取Resources目录下的文件
     *
     * @param fileName
     * @return
     */
    public static File getFileByResources(String fileName)
    {
        URL url = MyFiles.class.getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        return file;
    }

    /**
     * 获取Resources目录下static目录内的文件
     *
     * @param fileName
     * @return
     */
    public static File getFileByStatic(String fileName)
    {
        return getFileByResources(STATIC_DIRECTORY + fileName);
    }

    /**
     * 重载方法，说明见调用方法
     */
    public static void writeStringToFile(String content, String fileName)
    {
        writeStringToFile(content, fileName, false);
    }

    /**
     * 重载方法，说明见调用方法
     */
    public static void writeStringToFile(String content, String fileName, boolean append)
    {
        writeStringToFile(content, new File(fileName),append);
    }

    /**
     * 字符串写入文件
     * @param content 内容
     * @param file 文件
     * @param append 是否追加
     */
    public static void writeStringToFile(String content, File file,boolean append)
    {
        if (file.exists())
        {
            file.delete();
        }
        try(FileWriter fw=new FileWriter(file,append))
        {
            fw.write(content);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
