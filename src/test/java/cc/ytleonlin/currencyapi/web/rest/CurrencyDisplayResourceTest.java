package cc.ytleonlin.currencyapi.web.rest;

import cc.ytleonlin.currencyapi.domain.CurrencyDisplay;
import cc.ytleonlin.currencyapi.service.CurrencyDisplayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
class CurrencyDisplayResourceTest {

    private static final String PATH_WITH_CODE = CurrencyDisplayResource.PATH + CurrencyDisplayResource.PATH_WITH_CODE;
    private static final String PATH_WITH_ID = CurrencyDisplayResource.PATH + CurrencyDisplayResource.PATH_WITH_ID;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CurrencyDisplayService currencyDisplayService;

    @Test
    @Transactional
    void create() throws Exception {
        CurrencyDisplay display = createDisplay();
        mockMvc.perform(MockMvcRequestBuilders.post(CurrencyDisplayResource.PATH).contentType(MediaType.APPLICATION_JSON).content(convertObjectToBytes(display)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    void getByCode() throws Exception {
        String code = "EUR";

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_WITH_CODE, code))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(code));
    }

    @Test
    void getById() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_WITH_ID, id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    @Transactional
    void partialUpdateById() throws Exception {
        //initialize a record to update
        CurrencyDisplay original = currencyDisplayService.save(createDisplay());
        long countBeforeUpdate = currencyDisplayService.countAll();

        //Create data to update
        String newDisplay = "British Pound (Updated)";
        CurrencyDisplay toUpdate = new CurrencyDisplay();
        toUpdate.setDisplay(newDisplay);

        mockMvc.perform(MockMvcRequestBuilders.patch(PATH_WITH_ID, original.getId()).contentType(MediaType.APPLICATION_JSON).content(convertObjectToBytes(toUpdate)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(original.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.display").value(newDisplay))
                .andExpect(MockMvcResultMatchers.jsonPath("$.language").value(original.getLanguage()));

        Assertions.assertThat(currencyDisplayService.countAll()).isEqualTo(countBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteById() throws Exception {
        //initialize a record to delete
        CurrencyDisplay saved = currencyDisplayService.save(createDisplay());
        long countBeforeDelete = currencyDisplayService.countAll();

        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_WITH_ID, saved.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertThat(currencyDisplayService.countAll()).isEqualTo(countBeforeDelete - 1);
    }

    private CurrencyDisplay createDisplay() {
        CurrencyDisplay display = new CurrencyDisplay();
        display.setCode("GBP");
        display.setDisplay("British Pound Sterling");
        display.setLanguage("en");
        return display;
    }

    private byte[] convertObjectToBytes(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(obj);
    }
}