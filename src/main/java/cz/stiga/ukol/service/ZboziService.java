package cz.stiga.ukol.service;

import cz.stiga.ukol.model.SeznamZboziDto;
import cz.stiga.ukol.model.StatsDto;
import cz.stiga.ukol.persistence.dto.ZboziDto;
import cz.stiga.ukol.provider.ZboziPersistenceProvider;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZboziService {

    private final ZboziPersistenceProvider zboziPersistenceProvider;

    public ZboziService(ZboziPersistenceProvider zboziPersistenceProvider) {
        this.zboziPersistenceProvider = zboziPersistenceProvider;
    }

    public void createFromSeznamZbozi(SeznamZboziDto seznamZbozi) {
        seznamZbozi.getZboziDtoList().stream().forEach(zboziDto -> {
            zboziDto.setDatumCas(seznamZbozi.getCasNakupu());
            zboziPersistenceProvider.createZbozi(zboziDto);
        });
    }

    public List<ZboziDto> findZboziByEanAndDate(String ean, LocalDate datumOd, LocalDate datumDo) {
        return zboziPersistenceProvider.findZboziByEanAndDate(ean, datumOd, datumDo);
    }

    public List<StatsDto> getAllArticlesStats() {
        return zboziPersistenceProvider.findAll().stream()
                .collect(Collectors.groupingBy(
                        ZboziDto::getEan,
                        Collectors.reducing(new StatsDto("", 0, BigDecimal.ZERO, BigDecimal.ZERO),
                                zbozi -> new StatsDto(zbozi.getEan(),
                                        zbozi.getPocet(),
                                        zbozi.getCenaCelkem(),
                                        zbozi.getCenaCelkem().divide(BigDecimal.valueOf(zbozi.getPocet()), RoundingMode.HALF_UP)),
                                (obj1, obj2) -> new StatsDto(obj2.getEan(), obj1.getPocet() + obj2.getPocet(),
                                        obj1.getCenaCelkem().add(obj2.getCenaCelkem()),
                                        obj1.getCenaCelkem().add(obj2.getCenaCelkem())
                                                .divide(BigDecimal.valueOf(obj1.getPocet() + obj2.getPocet()), RoundingMode.HALF_UP))))
                )
                .values()
                .stream()
                .sorted(Comparator.comparing(StatsDto::getEan))
                .toList();
    }

}
