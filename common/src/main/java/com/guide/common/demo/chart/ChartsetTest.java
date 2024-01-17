package com.guide.common.demo.chart;

import cn.hutool.core.io.IoUtil;
import lombok.extern.log4j.Log4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 测试一下中文编码自适应
 */
@Log4j
public class ChartsetTest
{
    public static void main(String[] args) throws IOException
    {
        ByteArrayInputStream utf8Stream = IoUtil.toStream("name=我是UTF8", StandardCharsets.UTF_8);
        ByteArrayInputStream gbkStream = IoUtil.toStream("name=我是GBK", Charset.forName("GBK"));
        properties.load(utf8Stream);
        log.info(getAppProperty("name"));
        properties.load(gbkStream);
        log.info(getAppProperty("name"));

    }

    static Properties properties = new Properties();
    ;

    public static String getAppProperty(String key)
    {
        String value = properties.getProperty(key);
        if (value == null)
        {
            return null;
        }
        try
        {
            String _value = new String(value.getBytes("ISO8859-1"), "UTF-8");
            if (_value.contains("�"))
            {
                _value = new String(value.getBytes("ISO8859-1"), "GBK");
            }
            value = _value;
        }
        catch (UnsupportedEncodingException e)
        {
            log.error("属性值编码转换错误！", e);
            e.printStackTrace();
        }
        return value;
    }
}
