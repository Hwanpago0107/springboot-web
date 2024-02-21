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
        Wish wish = wishRepository.findWishByUser(user);
        if (wish == null) {
            wish = new Wish(user);
            wishRepository.save(wish);
        }

        // 위시리스트에 이미 해당 아이템이 있으면 옵션만 업데이트 해준다.
        WishItem wishItem = wishItemRepository.findWishItemByWishAndItem_Id(wish, request.getItem_id());

        if (wishItem != null) {
            wishItem.update(wish, item, request.getOption_text());
        } else {
            wishItem = new WishItem(wish, item, request.getOption_text());
        }

        return wishItemRepository.save(wishItem);
    }

    @Transactional
    public List<WishItem> findAll(User user) {
        return wishRepository.findWishByUser(user) == null ? new ArrayList<>() :
                wishItemRepository.findAllByWish(wishRepository.findWishByUser(user));
    }

    @Transactional
    public void delete(List<Long> checked, User user) {
        Wish wish = wishRepository.findWishByUser(user);
        for (Long check : checked) {
            wishItemRepository.deleteAllByItem_IdAndWish(check, wish);
        }
    }

    public void deleteAll(User user) {
        Wish wish = wishRepository.findWishByUser(user);
        wishItemRepository.deleteAllByWish(wish);
    }
}
