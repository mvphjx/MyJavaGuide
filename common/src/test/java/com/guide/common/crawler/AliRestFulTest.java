package com.guide.common.crawler;

import cn.hutool.json.JSONUtil;
import com.guide.common.model.Article;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjx
 * @version 1.0
 * @date 2021/8/22 17:55
 */
public class AliRestFulTest
{
    @Test
    public void save()
    {
        Article article = new Article();
        article.setTitle("Test");
        List list = new ArrayList<>();
        list.add(article);
        AliRestFul.saveToFile(JSONUtil.toJsonStr(list));
    }
    @Test
    public void getArticles()
    {
        List<Article> articles = AliRestFul.getArticles(1, 10);
        System.out.println(articles.size());
    }
}
