package com.spacerovka.tryelastic.controller;

import com.spacerovka.tryelastic.dao.ArticleDao;
import com.spacerovka.tryelastic.model.Article;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/articles")
public class ArticleController {

    private ArticleDao dao;

    public ArticleController(ArticleDao dao) {
        this.dao = dao;
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable String id){
        return dao.getById(id);
    }

    @PostMapping
    public Article insertArticle(@RequestBody Article article) {
        return dao.insert(article);
    }

    @PutMapping("/{id}")
    public Article updateArticleById(@RequestBody Article article, @PathVariable String id) {
        return dao.updateById(id, article);
    }

    @DeleteMapping("/{id}")
    public void deleteArticleById(@PathVariable String id) {
        dao.deleteById(id);
    }
}
