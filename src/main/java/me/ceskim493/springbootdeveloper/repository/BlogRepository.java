package me.ceskim493.springbootdeveloper.repository;

import me.ceskim493.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
