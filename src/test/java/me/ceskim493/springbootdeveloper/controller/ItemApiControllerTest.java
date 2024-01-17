package me.ceskim493.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.dto.AddItemRequest;
import me.ceskim493.springbootdeveloper.dto.UpdateItemRequest;
import me.ceskim493.springbootdeveloper.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class ItemApiControllerTest {
    
    @Autowired
    protected MockMvc mockMvc;
    
    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    ItemRepository itemRepository;

    @BeforeEach // 테스트 실행 전 실행하는 메소드
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        itemRepository.deleteAll();
    }

    @DisplayName("addItem: 아이템 추가에 성공한다.")
    @Test
    public void addItem() throws Exception {

        // given
        final String url = "/api/items";
        final String name = "name";
        final int price = 100;
        final int stockQuantity = 10;
        final AddItemRequest userRequest = new AddItemRequest(name, price, stockQuantity);

        // 객체 JSON 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Item> items = itemRepository.findAll();

        assertThat(items.size()).isEqualTo(1); // 크기가 1인지 검증
        assertThat(items.get(0).getName()).isEqualTo(name);
        assertThat(items.get(0).getPrice()).isEqualTo(price);
        assertThat(items.get(0).getStockQuantity()).isEqualTo(stockQuantity);
    }

    @DisplayName("findAllItems: 상품 목록 조회에 성공한다.")
    @Test
    public void findAllItems() throws Exception {
        // given : 블로그글을 저장하고
        final String url = "/api/items";
        Item savedItem = createDefaultItem();

        // when : 목록 조회 API를 호출하고
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then : 응답코드가 200이고, 반환받은 값 중에 0번째 요소의 content와 title이 저장된 값과 같은지 확인한다.
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(savedItem.getName()))
                .andExpect(jsonPath("$[0].price").value(savedItem.getPrice()))
                .andExpect(jsonPath("$[0].stockQuantity").value(savedItem.getStockQuantity()));
    }

    @DisplayName("updateItem: 상품 내용 수정에 성공한다.")
    @Test
    public void updateItem() throws Exception {
        //given : 상품 항목 항나를 저장
        final String url = "/api/items/{id}";
        Item savedItem = createDefaultItem();

        final String newName = "new name";
        final int newPrice = 2000;
        final int newStock = 10000;

        UpdateItemRequest request = new UpdateItemRequest(newName, newPrice, newStock);

        // when : update api로 수정 요청을 보내고, 이때 요청 타입은 JSON이고 given절에서 만들어둔 객체를 요청 본문으로 함계 보낸다.
        ResultActions result = mockMvc.perform(put(url, savedItem.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then : 응답코드가 200이고, 상품 id로 조회한 후에 값이 수정되었는지 확인한다.
        result.andExpect(status().isOk());

        Item item = itemRepository.findById(savedItem.getId()).get();

        assertThat(item.getName()).isEqualTo(newName);
        assertThat(item.getPrice()).isEqualTo(newPrice);
        assertThat(item.getStockQuantity()).isEqualTo(newStock);
    }

    private Item createDefaultItem() {
        return itemRepository.save(Item.builder()
                .name("test")
                .price(1000)
                .stockQuantity(100)
                .build());
    }
}