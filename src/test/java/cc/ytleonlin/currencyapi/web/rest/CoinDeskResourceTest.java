package cc.ytleonlin.currencyapi.web.rest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CoinDeskResourceTest {

    private static final String PATH_ORIGINAL = CoinDeskResource.PATH + CoinDeskResource.PATH_ORIGINAL;
    private static final String PATH_TRANSFERRED = CoinDeskResource.PATH + CoinDeskResource.PATH_TRANSFERRED;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getOriginalData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_ORIGINAL)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getTransferredDataWithDefaultLanguage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_TRANSFERRED))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bpi.USD.codeDisplay").value("美元"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bpi.GBP.codeDisplay").value("英鎊"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bpi.EUR.codeDisplay").value("歐元"));
    }

    @Test
    void getTransferredDataWithOtherLanguage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_TRANSFERRED).param("language","en-US"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bpi.USD.codeDisplay").value("United States Dollar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bpi.USD.codeDisplayLanguage").value("en-US"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bpi.GBP.codeDisplay").value("British Pound Sterling"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bpi.EUR.codeDisplay").value("Euro"));
    }
}