package cc.ytleonlin.currencyapi.web.rest;

import cc.ytleonlin.currencyapi.configuration.ApplicationConfig;
import cc.ytleonlin.currencyapi.domain.CurrencyDisplay;
import cc.ytleonlin.currencyapi.service.CurrencyDisplayService;
import cc.ytleonlin.currencyapi.web.rest.error.BadRequestException;
import cc.ytleonlin.currencyapi.web.rest.error.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<CurrencyDisplay> create(@Validated @RequestBody CurrencyDisplay display) throws URISyntaxException {
        log.debug("Request to create CurrencyDisplay: {}", display);
        if (display.getId() != null)
            throw new BadRequestException("The create CurrencyDisplay operation can't have ID.");

        CurrencyDisplay saved = currencyDisplayService.save(display);
        return ResponseEntity.created(new URI(PATH + "/id/" + saved.getId())).body(saved);
    }

    @GetMapping(PATH_WITH_ID)
    public Optional<CurrencyDisplay> getById(@PathVariable Long id) {
        Optional<CurrencyDisplay> result = currencyDisplayService.findById(id);
        if (!result.isPresent())
            throw new NotFoundException(String.format("Not found with id: %s", id));
        return result;
    }

    @GetMapping(PATH_WITH_CODE)
    public Optional<CurrencyDisplay> getByCode(@PathVariable String code) {
        Optional<CurrencyDisplay> result = currencyDisplayService.findByCodeAndLanguage(code, applicationConfig.getDefaultLanguage());
        if (!result.isPresent())
            throw new NotFoundException(String.format("Not found with code: %s and language: %s", code, applicationConfig.getDefaultLanguage()));
        return result;
    }

    @PatchMapping(PATH_WITH_ID)
    public Optional<CurrencyDisplay> partialUpdateById(@PathVariable Long id, @RequestBody CurrencyDisplay display) throws URISyntaxException {
        log.debug("Request to partial update CurrencyDisplay: {}", id);
        if (display.getId() != null && !id.equals(display.getId()))
            throw new BadRequestException("The id in body should be empty or same as the id on the path.");
        return currencyDisplayService.partialUpdateById(id, display);
    }

    @DeleteMapping(PATH_WITH_ID)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete CurrencyDisplay: {}", id);
        currencyDisplayService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
