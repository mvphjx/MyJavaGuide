package com.guide.common.uml;

import cn.hutool.core.io.FileUtil;
import com.guide.common.util.uml.UmlProtectUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

@Slf4j
public class UmlEncodeUtil
{

    public static void main(String[] args)
    {
        log.info("开始加密");
        log.info("密码提示:shenfenId");
        String umlPath = UmlProtectUtil.getPath();
        String key = UmlProtectUtil.inputKey();
        if (FileUtil.isDirectory(umlPath))
        {
            File directory = new File(umlPath);
            for (File file : directory.listFiles())
            {
                UmlProtectUtil.encodeFile(file, key);
            }
        }
    }
}
