package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.domain.Wish;
import me.ceskim493.springbootdeveloper.domain.WishItem;
import me.ceskim493.springbootdeveloper.dto.CreateWishRequest;
import me.ceskim493.springbootdeveloper.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WishService {

    private final ItemRepository itemRepository;
    private final WishRepository wishRepository;
    private final WishItemRepository wishItemRepository;

    @Transactional
    public WishItem save(CreateWishRequest request, User user) {
        // 위시리스트에 추가할 상품이 존재하는지 본다.
        Item item = itemRepository.findById(request.getItem_id())
                .orElseThrow(() -> new IllegalArgumentException("no items"));

        // 사용자가 갖고 있는 위시리스트가 없으면 만들어준다.
        if (wishRepository.findWishByUser(user).isEmpty()) {
            Wish wish = new Wish(user);
            wishRepository.save(wish);
        }

        Wish wish = wishRepository.findWishByUser(user).get();

        // 위시리스트에 이미 해당 아이템이 있으면 그냥 넘어간다.
        List<WishItem> wishItems = wishItemRepository.findWishItemByItem_Id(request.getItem_id());

        WishItem wishItem = null;
        if (wishItems != null && wishItems.size() > 0) {
            return wishItems.get(0);
        } else {
            wishItem = new WishItem(wish, item);
            return wishItemRepository.save(wishItem);
        }
    }

    @Transactional
    public List<WishItem> findAll(User user) {
        if (wishRepository.findWishByUser(user).isEmpty()) {
            return new ArrayList<WishItem>();
        }
        Wish wish = wishRepository.findWishByUser(user).get();
        return wishItemRepository.findAllByWish(wish);
    }

    @Transactional
    public void delete(List<Long> checked) {
        wishItemRepository.deleteAllById(checked);
    }

    public void deleteAll() {
        wishItemRepository.deleteAll();
    }
}
