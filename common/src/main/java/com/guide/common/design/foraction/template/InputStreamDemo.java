package com.guide.common.design.foraction.template;

import java.io.ByteArrayInputStream;

/**
 * Java IO 类库中，有很多类的设计用到了模板模式，
 * 比如 InputStream、OutputStream、Reader、Writer
 */
public class InputStreamDemo
{
    public void demo(byte buf[])
    {
        ByteArrayInputStream stream = new ByteArrayInputStream(buf);
        //read() 函数是一个模板方法，定义了读取数据的整个流程，并且暴露了一个可以由子类来定制的抽象方法。
        stream.read();
    }
}
