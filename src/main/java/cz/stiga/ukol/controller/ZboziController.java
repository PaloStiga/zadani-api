package cz.stiga.ukol.controller;

import cz.stiga.ukol.model.SeznamZboziDto;
import cz.stiga.ukol.model.StatsDto;
import cz.stiga.ukol.persistence.dto.ZboziDto;
import cz.stiga.ukol.service.ZboziService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/ukol/api/zbozi")
public class ZboziController {

    @Autowired
    ZboziService zboziService;

    @Operation(summary = "Vytvorí zboží dle seznamu")
    @PostMapping
    public void create(@RequestBody SeznamZboziDto seznamZbozi) {
        validateSeznamZbozi(seznamZbozi);
        zboziService.createFromSeznamZbozi(seznamZbozi);
    }

    @Operation(summary = "vrátí všechny položky nákupu zboží dle EAN mezi uvedenými datumy (včetně)")
    @ApiResponse(responseCode = "200", description = "List vyhovujúcich ZboziDto", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ZboziDto.class)))
    @GetMapping("/{ean}")
    public List<ZboziDto> findZboziByEanAndDate(@Parameter(description = "The EAN of the product") @PathVariable  @NotNull(message = "EAN cannot be null") String ean,
                                                @Parameter(description = "Datum od (včetne)") @RequestParam("datumOd") @DateTimeFormat(pattern = "d.M.yyyy") @NotNull(message = "Datum od cannot be null") LocalDate datumOd,
                                                @Parameter(description = "Datum do (včetne)") @RequestParam("datumDo") @DateTimeFormat(pattern = "d.M.yyyy") @NotNull(message = "Datum do cannot be null") LocalDate datumDo) {
        return zboziService.findZboziByEanAndDate(ean, datumOd, datumDo);
    }

    @Operation(summary = "vrátí všechny artikly, celkový počet prodaných ks, průměrnou cenu za 1 ks")
    @ApiResponse(responseCode = "200", description = "List StatsDto", content = @Content(mediaType = "application/json", schema = @Schema(implementation = StatsDto.class)))
    @GetMapping("/getAllArticlesStats")
    public List<StatsDto> getAllArticlesStats() {
        return zboziService.getAllArticlesStats();
    }

    private void validateSeznamZbozi(SeznamZboziDto seznamZbozi) {
        if (seznamZbozi.getCasNakupu() == null) {
            throw new IllegalArgumentException("Čas nákupu nesmí být null.");
        }
        if (seznamZbozi.getZboziDtoList() == null) {
            throw new IllegalArgumentException("Seznam zboží nesmí být null.");
        }
        seznamZbozi.getZboziDtoList().forEach(zboziDto -> {
            if (zboziDto == null) {
                throw new IllegalArgumentException("Položka zboží nesmí být null.");
            }
            if (zboziDto.getEan() == null) {
                throw new IllegalArgumentException("EAN nesmí být null.");
            }
            if (zboziDto.getPocet() == null) {
                throw new IllegalArgumentException("Počet nesmí být null.");
            }
            if (zboziDto.getCenaCelkem() == null) {
                throw new IllegalArgumentException("Cena celkem nesmí být null.");
            }
        });
    }

}