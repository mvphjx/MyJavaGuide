package com.guide.common.util.uml;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.guide.common.util.Aes128Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * 需要配置参数，否则无法Scanner输入
 * Help -> VM Options
 * -Deditable.java.test.console=true
 */
@Slf4j
public class UmlProtectUtil
{
    //秘钥
    static String INPUT_KEY = null;



    public static String inputKey()
    {
        if (!StrUtil.isEmpty(INPUT_KEY))
        {
            return INPUT_KEY;
        }
        Scanner rd = new Scanner(System.in);
        System.out.print("请输入秘钥：");
        String key = rd.nextLine();
        System.out.println(key);
        INPUT_KEY = key;
        return key;
    }

    public static String getPath()
    {
        String classPath = UmlProtectUtil.class.getResource("").getPath();
        String prePath = classPath.substring(0, classPath.indexOf("/target/"));
        String umlPath = prePath + "\\src\\main\\resources\\uml\\protect";
        return umlPath;
    }

    //解密
    public static void decodeFile(File file, String key)
    {
        String fileName = file.getName();
        if (fileName.endsWith(".puml.encrypt"))
        {
            String content = FileUtil.readString(file, Charset.forName("utf8"));
            String encrypt = Aes128Util.decrypt(content, key);
            String filePath = file.getPath().replace(".encrypt", "");
            FileUtil.writeString(encrypt, filePath, Charset.forName("utf8"));
        }
        else
        {
            log.warn("不需要解密：" + fileName);
        }

    }

    //加密
    public static void encodeFile(File file, String key)
    {
        String fileName = file.getName();
        if (fileName.endsWith(".puml"))
        {
            String content = FileUtil.readString(file, Charset.forName("utf8"));
            String encrypt = Aes128Util.encrypt(content, key);
            String filePath = file.getPath() + ".encrypt";
            FileUtil.writeString(encrypt, filePath, Charset.forName("utf8"));
        }
        else
        {
            log.warn("不需要加密：" + fileName);
        }
    }

    public static void main(String[] args)
    {
        log.info("开始解密");
        log.info("密码提示:shenfenId");
        String umlPath = getPath();
        String key = inputKey();
        if (FileUtil.isDirectory(umlPath))
        {
            File directory = new File(umlPath);
            for (File file : directory.listFiles())
            {
                decodeFile(file, key);
            }
        }
    }
}
