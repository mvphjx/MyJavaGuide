package com.guide.common.lambda;

/**
 * 基础语法
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/7 23:11
 */
public class BasicDemo
{

    /**
     * 变量声明&使用
     * Java会将msg的值作为参数传递给Lambda表达式，
     * 为Lambda表达式建立一个副本，它的代码访问的是这个副本，而不是外部声明的msg变量。
     * 因为作用域不同，无法直接访问外部变量
     */
    public void varDeclaration()
    {
        String msg = "Hello";
        //编译错误
        //msg = msg +"World";
        new Thread(() -> System.out.println(msg));
    }

    /**
     *函数式接口和Lambda表达式还可用作方法的返回值，传递代码回调用者，
     * 将这两种用法结合起来，可以构造复合的函数，使程序简洁易读。
     */
    public void returnVal()
    {

    }
}
