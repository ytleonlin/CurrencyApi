package cc.ytleonlin.currencyapi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {

    private CoinDeskConfig coinDesk;

    private String defaultLanguage;

    @Getter
    @Setter
    public static class CoinDeskConfig {
        private String apiUrl;
    }
}
