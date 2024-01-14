package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.Article;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.dto.ArticleListViewResponse;
import me.ceskim493.springbootdeveloper.dto.ArticleViewResponse;
import me.ceskim493.springbootdeveloper.service.BlogService;
import me.ceskim493.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    
    private final BlogService blogService;
    private final UserService userService;

    @GetMapping("/articles")
    public String getArticles(Model model, @LoginUser SessionUser user) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();

        model.addAttribute("articles", articles); // 블로그 글 리스트 setting
        model.addAttribute("userName", userService.getSessionUserName(user)); // session에 저장된 유저이름 setting
        
        return "articleList"; // articleList.html라는 뷰 조회
    } 
    
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        model.addAttribute("userName", userService.getSessionUserName(user)); // session에 저장된 유저이름 setting
        
        return "article"; // article.html라는 뷰 조회
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model, @LoginUser SessionUser user) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        model.addAttribute("userName", userService.getSessionUserName(user)); // session에 저장된 유저이름 setting

        return "newArticle";
    }
}
