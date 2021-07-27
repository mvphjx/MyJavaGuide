package com.guide.common.io.other;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/7/25 0:24
 */
public class ConnectionAutoClose implements AutoCloseable
{
    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception
    {
        System.out.println("自动关闭连接");
    }

    public static void main(String[] args)
    {
        try(ConnectionAutoClose coon = new ConnectionAutoClose())
        {
            System.out.println("建立连接"+coon);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
