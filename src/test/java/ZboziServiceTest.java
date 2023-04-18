import cz.stiga.ukol.model.StatsDto;
import cz.stiga.ukol.persistence.dto.ZboziDto;
import cz.stiga.ukol.provider.ZboziPersistenceProvider;
import cz.stiga.ukol.service.ZboziService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZboziServiceTest {

    @Test
    void testGetAllArticlesStats() {
        ZboziDto zbozi1 = new ZboziDto(1l, "A00123", 2, BigDecimal.valueOf(500),  LocalDateTime.of(2022, 10, 24, 10, 23, 59));
        ZboziDto zbozi2 = new ZboziDto(2l, "A00123", 3, BigDecimal.valueOf(700),  LocalDateTime.of(2022, 11, 4, 9, 0, 05));
        ZboziDto zbozi3 = new ZboziDto(3l, "B91201", 1, BigDecimal.valueOf(12),  LocalDateTime.of(2024, 1, 1, 12, 0, 0));
        List<ZboziDto> zboziList = Arrays.asList(zbozi1, zbozi2, zbozi3);
        ZboziPersistenceProvider persistenceProvider = mock(ZboziPersistenceProvider.class);
        when(persistenceProvider.findAll()).thenReturn(zboziList);
        ZboziService zboziService = new ZboziService(persistenceProvider);

        List<StatsDto> result = zboziService.getAllArticlesStats();

        assertEquals(2, result.size());
        StatsDto statsA00123 = result.get(0);
        assertEquals("A00123", statsA00123.getEan());
        assertEquals(5, statsA00123.getPocet());
        assertEquals(BigDecimal.valueOf(1200), statsA00123.getCenaCelkem());
        assertEquals(BigDecimal.valueOf(240), statsA00123.getPrumernaCenaZaKus());

    }

}
