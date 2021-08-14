package com.guide.common.rpc.codec;

import com.guide.common.model.Student;
import com.guide.common.rpc.util.ReflectionUtil;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.lang.reflect.Method;

/**
 * ReflectionUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>14, 2021</pre>
 */
public class ReflectionUtilTest
{

    @Before
    public void before() throws Exception
    {
    }

    @After
    public void after() throws Exception
    {
    }

    /**
     * Method: newInstans(Class<T> clazz)
     */
    @Test
    public void testNewInstans() throws Exception
    {
        Student student = ReflectionUtil.newInstans(Student.class);
        System.out.println(student);
    }

    /**
     * Method: getPublicMethods(Class clazz)
     */
    @Test
    public void testGetPublicMethods() throws Exception
    {
        Method[] publicMethods = ReflectionUtil.getPublicMethods(Student.class);
        for (Method publicMethod : publicMethods)
        {
            System.out.println(publicMethod);
        }
    }

    /**
     * Method: invoke(Object object, Method method, Object... args)
     */
    @Test
    public void testInvoke() throws Exception
    {
        Method[] publicMethods = ReflectionUtil.getPublicMethods(Student.class);
        Method method = null;
        for (Method publicMethod : publicMethods)
        {
            if (publicMethod.getName().equals("setName"))
            {
                method = publicMethod;
            }
        }
        Student student = new Student();
        ReflectionUtil.invoke(student, method, "小明");
        System.out.println(student);
    }

}
