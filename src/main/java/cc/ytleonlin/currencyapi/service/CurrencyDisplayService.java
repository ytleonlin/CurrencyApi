package cc.ytleonlin.currencyapi.service;

import cc.ytleonlin.currencyapi.domain.CurrencyDisplay;
import cc.ytleonlin.currencyapi.repository.CurrencyDisplayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class CurrencyDisplayService {

    private final CurrencyDisplayRepository currencyDisplayRepository;

    public CurrencyDisplayService(CurrencyDisplayRepository currencyDisplayRepository) {
        this.currencyDisplayRepository = currencyDisplayRepository;
    }

    @Transactional(readOnly = true)
    public Optional<CurrencyDisplay> findByCodeAndLanguage(String code, String language) {
        return currencyDisplayRepository.findByCodeAndLanguage(code, language);
    }

    @Transactional(readOnly = true)
    public Optional<CurrencyDisplay> findById(Long id) {
        return currencyDisplayRepository.findById(id);
    }

    @Transactional
    public CurrencyDisplay save(CurrencyDisplay display) {
        CurrencyDisplay saved = currencyDisplayRepository.save(display);
        log.info("new CurrencyDisplay saved, id: {}", saved.getId());
        return saved;
    }

    @Transactional
    public Optional<CurrencyDisplay> partialUpdateById(Long id, CurrencyDisplay displayToUpdate) {
        return findById(id).map(old -> {
            if (isNotEmpty(displayToUpdate.getCode())) old.setCode(displayToUpdate.getCode());
            if (isNotEmpty(displayToUpdate.getDisplay())) old.setDisplay(displayToUpdate.getDisplay());
            if (isNotEmpty(displayToUpdate.getLanguage())) old.setLanguage(displayToUpdate.getLanguage());

            return old;
        }).map(currencyDisplayRepository::save);
    }

    private boolean isNotEmpty(String target) {
        return target != null && !"".equals(target);
    }

    @Transactional
    public void deleteById(Long id) {
        currencyDisplayRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countAll() {
        return currencyDisplayRepository.count();
    }
}
