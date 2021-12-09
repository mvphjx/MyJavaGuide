package com.guide.common.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JsonUtil
{

    /**
     * jackson实现object转json
     *
     * @param obj
     * @param mapper 序列化参数
     * @return
     */
    public static String createJsDataByJackson(Object obj, ObjectMapper mapper)
    {
        String result = "";
        if (obj != null)
        {
            try
            {
                result = mapper.writeValueAsString(obj);
            }
            catch (JsonProcessingException e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * jackson实现object转json
     *
     * @param obj
     * @return String
     * <p>
     * 生成原始的json ；
     * 如果需要换行，需要自定义配置，比如对Timestamp指定格式。  请使用 TextInfoUtil.createJsonTextInfoUtil().convertToString(obj)
     */
    public static String createJsDataByJackson(Object obj)
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        mapper.setDateFormat(dateFormat);
        return createJsDataByJackson(obj, mapper);
    }

    /**
     * jackson实现json转 object
     *
     * @param json
     * @param valueType
     * @return
     */
    public static <T> T createObjectByJackson(String json, Class<T> valueType)
    {

        ObjectMapper mapper = new ObjectMapper();
        T t = null;
        try
        {
            /**
             * 时间格式兼容
             */
            SimpleModule module = new SimpleModule("Timestamp", Version.unknownVersion());
            module.addDeserializer(Timestamp.class, new WebTimestampDeserializer());
            mapper.registerModule(module);
            /**
             * 忽略json中的冗余字段
             */
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            t = (T) mapper.readValue(json, valueType);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 对象转换
     *
     * @param t   目标Class
     * @param obj 源对象
     * @param <T>
     * @return
     */
    public static <T> T convert(Class<T> t, Object obj)
    {
        String json = createJsDataByJackson(obj);
        return createObjectByJackson(json, t);
    }

}

class WebTimestampDeserializer extends JsonDeserializer<Timestamp>
{
    private static final Logger LOG = LoggerFactory.getLogger(WebTimestampDeserializer.class);

    @Override
    public Timestamp deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        String time = jp.getText();
        if (StrUtil.isEmpty(time))
        {
            return null;
        }
        else
        {
            Date parsedDate = null;

            try
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                parsedDate = dateFormat.parse(time);
            }
            catch (ParseException var12)
            {
                try
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                    parsedDate = dateFormat.parse(time);
                }
                catch (ParseException var11)
                {

                    try
                    {
                        if (time.contains("-"))
                        {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            parsedDate = dateFormat.parse(time);
                        }
                        else
                        {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                            parsedDate = dateFormat.parse(time);
                        }

                    }
                    catch (ParseException var10)
                    {
                        try
                        {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a",
                                    Locale.ENGLISH);
                            parsedDate = dateFormat.parse(time);
                        }
                        catch (ParseException var9)
                        {
                            LOG.info("deserialize time stamp failed" + var9.getMessage());
                            return null;
                        }
                    }
                }
            }
            return new Timestamp(parsedDate.getTime());
        }
    }
}
