package me.ceskim493.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.dto.AddItemRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private Item createDefaultItem() {
        return itemRepository.save(Item.builder()
                .name("test")
                .price(1000)
                .stockQuantity(100)
                .build());
    }
}