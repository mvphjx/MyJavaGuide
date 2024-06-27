package com.guide.common.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 以下是使用AI生成的代码
 * https://kimi.moonshot.cn/
 * 提示词为：
 * 使用hutool 读取一个txt文件 删除 包含 ‘————’的所有行保存成新文件
 */
public class TextModifyTest
{
    public static void main(String[] args)
    {
        // 原始文件路径
        String originalFilePath = "C:\\Users\\han\\Desktop\\temp\\temp\\original.txt";
        // 新文件路径
        String newFilePath = "C:\\Users\\han\\Desktop\\temp\\temp\\new.txt";

        // 要删除的行中包含的文本
        String textToRemove = "————";

        // 读取原始文件的所有行
        List<String> lines = new ArrayList<>();
        lines = FileUtil.readLines(new File(originalFilePath), CharsetUtil.CHARSET_UTF_8);

        // 创建一个新列表，用于存放不包含特定文本的行
        List<String> newLines = new ArrayList<>();

        // 遍历所有行，如果行中不包含特定文本，则添加到新列表中
        for (String line : lines)
        {
            if (!line.contains(textToRemove))
            {
                newLines.add(line);
            }
        }

        // 将新列表中的行写入新文件
        FileUtil.writeLines(newLines, new File(newFilePath), CharsetUtil.CHARSET_UTF_8);
    }
}
