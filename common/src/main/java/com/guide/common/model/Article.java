package com.guide.common.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 知乎专栏 文章
 *
 * @author hjx
 * @version 1.0
 * @date 2021/8/22 16:06
 */
@Data
public class Article
{
    private Long id;
    private String title;
    private String url;
    private String excerpt;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
