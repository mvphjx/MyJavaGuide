package com.guide.common.util.opi;

import com.guide.common.util.JsonUtil;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * bzo模型转换为web端模型，仅支持一对一转换
 * 简单场景：VO只对BO做扩展和内容修改，不删除BO的属性。VO可以继承BO，实现自动转换。
 * 1. 使用ABISJSonUtil.convert 构建对象
 * 2. 调用fromObject，增加扩展属性
 * 3. 如果不需要某些属性暴露给前端，可以在getXXX()增加 @JsonIgnore
 * 复杂场景：VO与BO差异较大，需要重写方法进行手动转换。
 * 1. 重写fromObject
 * 2. 重写toObject
 *
 * @param <VO>
 * @param <BO>
 */
public interface IVoConvert<VO, BO> extends Serializable
{
    /**
     * VO对象属性扩展，例如代码表文本
     *
     * @param bo BO对象，继承&自动转换时，可以不传
     */
    void fromObject(BO bo) throws RuntimeException;

    /**
     * 由web端模型生成bzo端模型。 缺省自动转换，必要时重写此方法
     */
    default BO toObject()
    {
        Type[] genericInterfaces = this.getClass().getGenericInterfaces();
        //获取第二个泛型类，即bzo类
        String className = ((ParameterizedTypeImpl) genericInterfaces[0]).getActualTypeArguments()[1].getTypeName();
        Class<?> aClass = null;
        try
        {
            aClass = Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return (BO) JsonUtil.convert(aClass, this);
    }

}
