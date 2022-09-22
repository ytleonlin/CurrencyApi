package cc.ytleonlin.currencyapi.web.rest;

import cc.ytleonlin.currencyapi.configuration.ApplicationConfig;
import cc.ytleonlin.currencyapi.domain.CurrencyDisplay;
import cc.ytleonlin.currencyapi.service.CurrencyDisplayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = CurrencyDisplayResource.PATH, produces = "application/json")
public class CurrencyDisplayResource {

    static final String PATH = "/currency-displays";
    static final String PATH_WITH_CODE = "/code/{code}";
    static final String PATH_WITH_ID = "/id/{id}";

    private final CurrencyDisplayService currencyDisplayService;

    private final ApplicationConfig applicationConfig;

    public CurrencyDisplayResource(CurrencyDisplayService currencyDisplayService, ApplicationConfig applicationConfig) {
        this.currencyDisplayService = currencyDisplayService;
        this.applicationConfig = applicationConfig;
    }

    @PostMapping
    public ResponseEntity<CurrencyDisplay> create(@RequestBody CurrencyDisplay display) throws URISyntaxException {
        if (display.getId() != null)
            throw new IllegalArgumentException("The create CurrencyDisplay operation can't have ID.");

        CurrencyDisplay saved = currencyDisplayService.save(display);
        return ResponseEntity.created(new URI(PATH + "/id/" + saved.getId())).body(saved);
    }

    @GetMapping(PATH_WITH_ID)
    public Optional<CurrencyDisplay> getById(@PathVariable Long id) {
        return currencyDisplayService.findById(id);
    }

    @GetMapping(PATH_WITH_CODE)
    public Optional<CurrencyDisplay> getByCode(@PathVariable String code) {
        return currencyDisplayService.findByCodeAndLanguage(code, applicationConfig.getDefaultLanguage());
    }

    @PatchMapping(PATH_WITH_ID)
    public Optional<CurrencyDisplay> partialUpdateById(@PathVariable Long id, @RequestBody CurrencyDisplay display) throws URISyntaxException {
        log.info("Request to partial update CurrencyDisplay: {}", id);
        if (display.getId() != null && !id.equals(display.getId()))
            throw new IllegalArgumentException("The id in body should be empty or same as the id on the path.");
        return currencyDisplayService.partialUpdateById(id, display);
    }

    @DeleteMapping(PATH_WITH_ID)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Request to delete CurrencyDisplay: {}", id);
        currencyDisplayService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
