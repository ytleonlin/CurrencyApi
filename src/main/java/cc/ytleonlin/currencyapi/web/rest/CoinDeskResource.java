package cc.ytleonlin.currencyapi.web.rest;

import cc.ytleonlin.currencyapi.configuration.ApplicationConfig;
import cc.ytleonlin.currencyapi.domain.CoinDeskInfo;
import cc.ytleonlin.currencyapi.service.CoinDeskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = CoinDeskResource.PATH, produces = "application/json")
public class CoinDeskResource {

    static final String PATH = "/coin-desk";
    static final String PATH_ORIGINAL = "/original";
    static final String PATH_TRANSFERRED = "/transferred";

    private final CoinDeskService coinDeskService;

    private final ApplicationConfig applicationConfig;

    public CoinDeskResource(CoinDeskService coinDeskService, ApplicationConfig applicationConfig) {
        this.coinDeskService = coinDeskService;
        this.applicationConfig = applicationConfig;
    }

    @GetMapping(PATH_ORIGINAL)
    public ResponseEntity<String> getOriginalData() {
        return ResponseEntity.ok(coinDeskService.getOriginal());
    }

    @GetMapping(PATH_TRANSFERRED)
    public CoinDeskInfo getTransferredData(@RequestParam(required = false) String language) {
        return coinDeskService.getTransferredCoinDeskInfo(language != null && !"".equals(language) ? language : applicationConfig.getDefaultLanguage());
    }
}
