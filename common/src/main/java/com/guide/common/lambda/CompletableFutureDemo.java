package com.guide.common.lambda;

import cn.hutool.http.HttpUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Java8 异步编程—CompletableFuture
 * <p>
 * 业务场景：测试多个网站网速，等待结果返回进行融合处理。
 */
public class CompletableFutureDemo
{
    //异步任务执行线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Test
    public void urlTest()
    {
        List<String> urls = new ArrayList<>();
        urls.add("https://www.yuque.com/");
        urls.add("https://www.baidu.com");
        urls.add("https://spring.io");
        urls.add("https://www.google.cn");
        urls.add("https://github.com");
        urls.add("http://www.google.com.hk");

        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (String url : urls)
        {
            //对一个或多个 Future 合并操作
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> getSpeed(url), executorService)
                    .exceptionally(throwable -> {
                        // 异常 处理
                        return throwable.toString();
                    });
            futures.add(future);
        }
        //join操作，阻塞等待所有异步操作的结果.
        List<String> result = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        result.forEach(System.out::println);
        executorService.shutdown();

    }

    private String getSpeed(String url)
    {
        long l = System.currentTimeMillis();
        System.out.println("Start " + url);
        String result = url;
        try
        {
            int timeOut = 5000;
            String html = HttpUtil.get(url, timeOut);
            result = result + " 访问正常 " + (System.currentTimeMillis() - l) + "ms";
        }
        catch (Exception e)
        {
            result = result + " 访问异常 " + (System.currentTimeMillis() - l) + "ms";
            result = result + "(" + e.getMessage() + ")";
            throw new RuntimeException(result);
        }

        System.out.println("Finish " + url);
        return result;

    }
}
