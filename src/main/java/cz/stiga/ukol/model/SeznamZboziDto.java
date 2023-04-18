package cz.stiga.ukol.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import cz.stiga.ukol.persistence.dto.ZboziDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SeznamZboziDto {

    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    private LocalDateTime casNakupu;
    private List<ZboziDto> zboziDtoList;

}
