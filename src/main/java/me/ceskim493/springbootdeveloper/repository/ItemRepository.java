package me.ceskim493.springbootdeveloper.repository;

import me.ceskim493.springbootdeveloper.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
