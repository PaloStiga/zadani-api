package cz.stiga.ukol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsDto {

    private String ean;

    private Integer pocet;

    @JsonIgnore
    private BigDecimal cenaCelkem;

    private BigDecimal prumernaCenaZaKus;

}
