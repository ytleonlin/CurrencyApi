package cc.ytleonlin.currencyapi.service;

import cc.ytleonlin.currencyapi.configuration.ApplicationConfig;
import cc.ytleonlin.currencyapi.domain.CoinDeskInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class CoinDeskService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private CurrencyDisplayService currencyDisplayService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").withZone(ZoneId.systemDefault());

    public String getOriginal() {
        return restTemplate.getForObject(applicationConfig.getCoinDesk().getApiUrl(), String.class);
    }

    public CoinDeskInfo getTransferredCoinDeskInfo(String language) {
        CoinDeskInfo info = getCoinDeskInfo();
        info.getTime().setUpdatedDisplay(formatter.format(info.getTime().getUpdatedISO()));
        info.getBpi().forEach((code, currency) ->
                currencyDisplayService.findByCodeAndLanguage(currency.getCode(), language).ifPresent(display -> {
                    currency.setCodeDisplay(display.getDisplay());
                    currency.setCodeDisplayLanguage(display.getLanguage());
                })
        );

        return info;
    }

    private CoinDeskInfo getCoinDeskInfo() {
        try {
            return objectMapper.readValue(getOriginal(), CoinDeskInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
