package com.guide.common.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


/**
 *  二进制文件流操作Demo
 * @author 北京海鑫高科指纹技术有限公司
 *         www.idfounder.com
 *         北京海鑫科金高科技股份有限公司
 *         www.hisign.com.cn
 *         创建日期：   2021/7/3 18:21
 */

public class BinaryFileDemo
{
    public static void main(String[] args)
    {
        System.out.println(1);
    }

    /**
     * 获取读文件输入流
     *
     * @param path
     * @return  装饰类，对输入输出流提供缓冲功能。对频繁小数据的读取进行优化
     * @throws FileNotFoundException
     */
    public static InputStream loadFile(String path) throws FileNotFoundException
    {
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        /**
         * 读文件时，即使目前只需要少量内容，但预知还会接着读取，就一次读取比较多的内容，
         * 放到读缓冲区，下次读取时，如果缓冲区有，就直接从缓冲区读，减少访问操作系统和硬盘。
         */
        return new BufferedInputStream(fileInputStream, 8192);
    }

    /**
     * 获取写文件的输出流
     * @param path
     * @param data
     * @return 装饰类，对输入输出流提供缓冲功能。对频繁小数据的读取进行优化
     * @throws Exception
     */
    public static OutputStream writeFile(String path, byte[] data) throws Exception
    {
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        /**
         * 写文件时，如果内容比较少，先写到写缓冲区，写缓冲区满了之后，再一次性调用操作系统写到硬盘。
         */
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        return bufferedOutputStream;
    }

}
