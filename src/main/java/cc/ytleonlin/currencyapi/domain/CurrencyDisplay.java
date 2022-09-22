package cc.ytleonlin.currencyapi.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Entity
@Table(name = "currency_display")
public class CurrencyDisplay {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 3)
    @NotBlank
    @Column(name = "code", length = 3, nullable = false)
    private String code;

    @NotBlank
    @Column(name = "display", nullable = false)
    private String display;

    @Size(max = 10)
    @NotBlank
    @Column(name = "language", length = 10, nullable = false)
    private String language;

}
