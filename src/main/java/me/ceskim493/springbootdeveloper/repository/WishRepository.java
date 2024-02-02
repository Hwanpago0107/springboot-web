package me.ceskim493.springbootdeveloper.repository;

import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findWishByUser(User user);
}
