package com.guide.common.uml;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.PathUtil;
import com.google.common.reflect.ClassPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

@Slf4j
public class UmlProtectUtil
{

    @Test
    public void encode()
    {
        String classPath = UmlProtectUtil.class.getResource("").getPath();
        String prePath = classPath.substring(0, classPath.indexOf("/target/"));
        String umlPath = prePath + "\\src\\main\\resources\\uml\\protect";
        System.out.println(umlPath);
        if (FileUtil.isDirectory(umlPath))
        {
            File directory = new File(umlPath);
            for (File file : directory.listFiles())
            {
                encodeFile(file);
            }
        }
    }

    //加密
    public void encodeFile(File file)
    {
        String fileName = file.getName();
        if (fileName.endsWith(".puml"))
        {
            String content = FileUtil.readString(file, Charset.forName("utf8"));
            System.out.println(content);
        }
        else
        {
            log.warn("不需要加密");
        }
    }
}
