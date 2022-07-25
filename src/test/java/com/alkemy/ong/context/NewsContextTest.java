package com.alkemy.ong.context;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.models.entity.CategoryEntity;
import com.alkemy.ong.models.entity.CommentEntity;
import com.alkemy.ong.models.entity.NewsEntity;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.UUID;

public abstract class NewsContextTest extends ContextTests {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    protected CategoryEntity createCategory() throws Exception{
        CategoryEntity category =
                CategoryEntity.builder()
                        .name("category test")
                        .description("news test")
                        .image("image")
                        .build();
        categoryRepository.save(category);
        return category;
    }
    protected NewsEntity createNews() throws Exception{
        CategoryEntity category = createCategory();
        NewsEntity news =
                NewsEntity.builder().
                        name("news test").
                        content("content test").
                        image("imageTest").
                        lastModification(new Timestamp(System.currentTimeMillis())).
                        categoryId(category.getId()).
                        build();
        newsRepository.save(news);
        return news;
    }

    protected NewsEntity createNewsParams() throws Exception{
        CategoryEntity category = createCategory();
        NewsEntity news =
                NewsEntity.builder().
                        name(String.format("%s_%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000)).
                        content(String.format("%s_%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000)).
                        image(String.format("%s_%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000)).
                        lastModification(new Timestamp(System.currentTimeMillis())).
                        categoryId(category.getId()).
                        build();
        newsRepository.save(news);
        return news;
    }

    protected NewsEntity createNewsParamsNoSave() throws Exception{
        CategoryEntity category = createCategory();
        NewsEntity news =
                NewsEntity.builder().
                        name(String.format("%s_%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000)).
                        content(String.format("%s_%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000)).
                        image(String.format("%s_%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000)).
                        lastModification(new Timestamp(System.currentTimeMillis())).
                        categoryId(category.getId()).
                        build();
        return news;
    }

    protected CommentEntity createComment(NewsEntity news) throws Exception{
        CommentEntity comment =
                CommentEntity.builder()
                        .body("body test")
                        .newsId(news.getId())
                        .build();
        commentRepository.save(comment);
        return comment;
    }
}
