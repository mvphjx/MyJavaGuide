package com.guide.common.crawler;
/**
 * 阿里技术 专栏，获取所有文章
 * https://www.zhihu.com/column/c_179394357
 * @author hjx
 * @version 1.0
 * @date 2021/8/22 15:58
 */

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.guide.common.model.Article;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
public class AliRestFul
{
    //异步任务执行线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    //文章总数
    private static int totals = 0;

    public static void main(String[] args)
    {
        getAll();
    }

    /**
     * 查询所有文章，并且保存到本地
     */
    private static void getAll()
    {
        int offset = 0;
        int limit = 100;
        //获取totals
        loadTotals();
        List<CompletableFuture<List<Article>>> futures = new ArrayList<>();
        while (totals == 0 || totals > offset)
        {
            int finalOffset = offset;
            //对一个或多个 Future 合并操作
            CompletableFuture<List<Article>> future = CompletableFuture
                    .supplyAsync(() -> getArticles(finalOffset, limit), executorService).exceptionally(throwable -> {
                        // 异常 处理
                        throwable.printStackTrace();
                        return null;
                    });
            futures.add(future);
            offset = offset + limit;
        }
        //join操作，阻塞等待所有异步操作的结果.
        List<List<Article>> resultList = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        List<Article> result = new ArrayList<>();
        for (List<Article> articles : resultList)
        {
            result.addAll(articles);
        }
        List<Article> resultPlus = result.stream().distinct().sorted(Comparator.comparing(Article::getId).reversed())
                .collect(Collectors.toList());
        String jsonStr = JSONUtil.toJsonStr(resultPlus);
        saveToFile(jsonStr);
        executorService.shutdown();

    }

    /**
     * 将数据保存到  js文件中
     *
     * @param json
     */
    public static void saveToFile(String json)
    {
        String path = "js\\alidata.js";
        String content = "var alidata = " + json + ";";
        Assert.isTrue(FileUtil.exist(path));
        File file = FileUtil.writeString(content, path, "UTF-8");
        System.out.println(file.getPath());
        String filePath = file.getPath();
        if (filePath.endsWith("\\target\\classes\\js\\alidata.js"))
        {
            filePath = filePath.replace("\\target\\classes\\js\\alidata.js", "");
            filePath = filePath + "\\src\\main\\resources\\" + path;
            file = FileUtil.writeString(content, filePath, "UTF-8");
        }
        System.out.println(file.getPath());
    }

    /**
     * 加载文章总数
     */
    private static void loadTotals()
    {
        String thisUrl = getUrl(0, 0);
        String jsonStr = HttpUtil.get(thisUrl);
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        if (totals == 0)
        {
            totals = jsonObject.getByPath("paging.totals", Integer.TYPE);
            log.info("getArticles-totals：" + totals);
        }
    }

    /**
     * 分页查询文章
     *
     * @param offset
     * @param limit
     * @return
     */
    public static List<Article> getArticles(int offset, int limit)
    {
        String thisUrl = getUrl(offset, limit);
        String jsonStr = HttpUtil.get(thisUrl);
        List<Article> articles = covertArticles(jsonStr);
        return articles;
    }

    /**
     * 获取API URL
     *
     * @param offset
     * @param limit
     * @return
     */
    private static String getUrl(int offset, int limit)
    {

        String url = "https://www.zhihu.com/api/v4/columns/c_179394357/items?offset={{offset}}&limit={{limit}}";
        url = url.replace("{{offset}}", offset + "").replace("{{limit}}", limit + "");
        log.info(url);
        return url;

    }

    /**
     * 文章Json解析
     *
     * @param jsonStr
     * @return
     */
    private static List<Article> covertArticles(String jsonStr)
    {
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        List<Article> result = new ArrayList<>();
        JSONArray data = jsonObject.getJSONArray("data");
        Iterator<JSONObject> iterator = data.jsonIter().iterator();
        while (iterator.hasNext())
        {
            Article article = new Article();
            JSONObject articleJson = iterator.next();
            Long id = articleJson.getLong("id");
            Long created = articleJson.getLong("created");
            Long updated = articleJson.getLong("updated");
            String url = articleJson.getStr("url");
            String title = articleJson.getStr("title");
            article.setId(id);
            article.setTitle(title);
            article.setUrl(url);
            article.setCreateTime(LocalDateTimeUtil.of(created * 1000));
            article.setUpdateTime(LocalDateTimeUtil.of(updated * 1000));
            article.setExcerpt(articleJson.getStr("excerpt"));
            result.add(article);
        }
        log.info("getArticles：" + result.size());
        return result;
    }

}
