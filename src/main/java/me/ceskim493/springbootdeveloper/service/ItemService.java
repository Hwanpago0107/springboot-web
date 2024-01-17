package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.dto.AddItemRequest;
import me.ceskim493.springbootdeveloper.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public Item save(AddItemRequest request) {
        return itemRepository.save(request.toEntity());
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("unexpected Item"));
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
