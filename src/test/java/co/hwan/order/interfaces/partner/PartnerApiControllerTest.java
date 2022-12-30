package co.hwan.order.interfaces.partner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.hwan.order.application.partner.PartnerFacade;
import co.hwan.order.domain.partner.PartnerCommand;
import co.hwan.order.interfaces.partner.PartnerDto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(value = PartnerApiController.class)
public class PartnerApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartnerFacade partnerFacade;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysDo(print())
            .build();
    }

    @DisplayName("Regist Partner")
    @Test
    public void regisPartner() throws Exception {
        // given
        RegisterRequest registDto = RegisterRequest.of("Brand A", "11-123-456-", "branA@test.com",
            "010-4055-6727");

        PartnerCommand command = registDto.toCommand();

        // when
        mockMvc.perform(post("/api/partner").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registDto))).andExpect(status().isCreated());

        verify(partnerFacade, times(1)).registPartner(command);
    }
}
