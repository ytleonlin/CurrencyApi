package cc.ytleonlin.currencyapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

/***
 * Currency and exchange rate information from CoinDesk
 */
@Getter
@Setter
public class CoinDeskInfo {

    private TimeInfo time;

    private String chartName;

    private Map<String, CurrencyInfo> bpi;

    @Getter
    @Setter
    public static class TimeInfo {

        private Instant updatedISO;

        private String updatedDisplay;
    }

    @Getter
    @Setter
    public static class CurrencyInfo {

        private String code;

        private String rate;

        @JsonProperty("rate_float")
        private float rateFloat;

        private String codeDisplay;

        private String codeDisplayLanguage;
    }
}
