package com.guide.common.tool;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.nio.charset.Charset;

public class Base64Image
{

    public static void main(String[] args)
    {
        String path = "C:\\PU\\mras\\hsbi-vue\\src\\assets\\chart";
        File file = new File(path);
        if (file.isDirectory())
        {
            for (File listFile : file.listFiles())
            {
                covertBase64Image(listFile);
            }
        }
    }




    private static void covertBase64Image(File listFile)
    {
        String content = FileUtil.readString(listFile, Charset.defaultCharset());
        if (content.startsWith("module.exports"))
        {
            int start = content.indexOf(";base64,") + ";base64,".length();
            int end = content.lastIndexOf("\"");
            content = content.substring(start, end);
            byte[] data = Base64Decoder.decode(content);
            FileUtil.writeBytes(data, listFile);
        }
    }

}
