package com.guide.common.opp;

/**
 * Java program which demonstrate that we can not override static method in Java.
 * Had Static method can be overridden, with Super class type and sub class object
 * static method from sub class would be called in our example, which is not the case.
 * 1)Java 程序，证明我们不能覆盖 Java 中的静态方法。
 * 2)应该通过类名调用静态方法
 * @author han
 * @date 2021年7月6日14:04:41
 */
public class CanWeOverrideStaticMethod
{

    public static void main(String args[])
    {

        Screen scrn = new ColorScreen();
        /**
         * IDE warning：不应该通过类实例访问静态成员。
         * 输出 parent，覆盖失败
         */
        scrn.show();
        ColorScreen colorScreen = new ColorScreen();
        /**
         * IDE warning：不应该通过类实例访问静态成员。
         * 输出 Child
         */
        colorScreen.show();

    }

}

class Screen
{
    /**
     * public static method which can not be overridden in Java
     */
    public static void show()
    {
        System.out.println("Static method from parent class");
    }
}

class ColorScreen extends Screen
{
    /**
     * static method of same name and method signature as existed in super
     * class, this is not method overriding instead this is called
     * method hiding in Java
     * 与 super * 类中存在相同名称和方法签名的静态方法，这不是方法覆盖,
     * 而是成为Java中的方法隐藏
     */
    public static void show()
    {
        System.out.println("Overridden static method in Child Class in Java");
    }
}
