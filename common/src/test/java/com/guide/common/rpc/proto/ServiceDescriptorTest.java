package com.guide.common.rpc.proto;

import com.guide.common.model.Student;
import com.guide.common.rpc.util.ReflectionUtil;
import org.junit.Test;

import java.lang.reflect.Method;

public class ServiceDescriptorTest
{

    @Test
    public void testFrom() throws NoSuchMethodException
    {
        Method setName = Student.class.getDeclaredMethod("setName", String.class);
        ServiceDescriptor descriptor = ServiceDescriptor.from(Student.class, setName);
        System.out.println(descriptor);
    }
}
