package com.guide.common.uml;

import cn.hutool.core.io.FileUtil;
import com.guide.common.util.uml.UmlProtectUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

/**
 * 使用main实现
 * 使用@Test需要配置参数，否则无法Scanner输入
 * Help -> VM Options
 * -Deditable.java.test.console=true
 */
@Slf4j
public class UmlDecodeUtil
{

    public static void main(String[] args)
    {
        log.info("开始解密");
        log.info("密码提示:shenfenId");
        String umlPath = UmlProtectUtil.getPath();
        String key = UmlProtectUtil.inputKey();
        if (FileUtil.isDirectory(umlPath))
        {
            File directory = new File(umlPath);
            for (File file : directory.listFiles())
            {
                UmlProtectUtil.decodeFile(file, key);
            }
        }
    }
}
