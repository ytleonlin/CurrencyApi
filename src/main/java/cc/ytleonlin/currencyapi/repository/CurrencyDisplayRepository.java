package cc.ytleonlin.currencyapi.repository;

import cc.ytleonlin.currencyapi.domain.CurrencyDisplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyDisplayRepository extends JpaRepository<CurrencyDisplay, Long> {

    Optional<CurrencyDisplay> findByCodeAndLanguage(String code, String language);
}
