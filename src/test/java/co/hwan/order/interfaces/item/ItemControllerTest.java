package co.hwan.order.interfaces.item;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.hwan.order.domain.item.ItemService;
import co.hwan.order.interfaces.item.ItemDto.RegisterItemOptionGroupRequest;
import co.hwan.order.interfaces.item.ItemDto.RegisterItemOptionRequest;
import co.hwan.order.interfaces.item.ItemDto.RegisterItemRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(value = ItemApiController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @BeforeEach
    void setUp (WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .alwaysDo(print())
            .build();
    }

    @DisplayName("Register Item")
    @Test
    void  registerItem() throws Exception {
        // given dto
        // RegisterItemOptionRequest
        RegisterItemOptionRequest optionRequest = new ItemDto.RegisterItemOptionRequest();
        optionRequest.setOrdering(1);
        optionRequest.setItemOptionName("SMALL");
        optionRequest.setItemOptionPrice(1000L);
        // RegisterItemOptionGroupRequest
        ArrayList<RegisterItemOptionRequest> optionRequests = new ArrayList<>();
        RegisterItemOptionGroupRequest registerItemOptionGroupRequest = new ItemDto.RegisterItemOptionGroupRequest();
        registerItemOptionGroupRequest.setOrdering(1);
        registerItemOptionGroupRequest.setItemOptionGroupName("SIZE");
        registerItemOptionGroupRequest.setItemOptionList(optionRequests);

        // RegisterItemRequest
        ArrayList<RegisterItemOptionGroupRequest> optionGroupRequests = new ArrayList<>();
        optionGroupRequests.add(registerItemOptionGroupRequest);

        RegisterItemRequest registerItemRequest = new RegisterItemRequest();
        registerItemRequest.setItemName("T-Shirt");
        registerItemRequest.setPartnerToken("ptn_test_134_token");
        registerItemRequest.setItemOptionGroupList(optionGroupRequests);
        registerItemRequest.setItemPrice(30000L);


        mockMvc.perform(
            post("/api/v1/items").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerItemRequest))
        ).andExpect(status().isOk());


        // todo
        // item application layer 들어내기
//        verify(itemService, times(1)).registerItem();


    }

}
