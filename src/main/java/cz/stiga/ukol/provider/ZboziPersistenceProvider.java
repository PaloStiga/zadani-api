package cz.stiga.ukol.provider;

import cz.stiga.ukol.persistence.dto.ZboziDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ZboziPersistenceProvider {

    @Value("${persistence.h2.url}")
    private String persistenceH2Url;

    /**
     *
     * @param zboziDto
     */
    public void createZbozi(ZboziDto zboziDto) {
        URI uri = UriComponentsBuilder.fromUriString(persistenceH2Url)
                .pathSegment("create")
                .build()
                .encode()
                .toUri();
        log.info("Volám URL: {}"+uri.toString());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ZboziDto> requestEntity = new HttpEntity<>(zboziDto, headers);
        restTemplate.postForObject(uri, requestEntity, ZboziDto.class);
    }

    public List<ZboziDto> findAll() {
        URI uri = UriComponentsBuilder.fromUriString(persistenceH2Url)
                .pathSegment("findAll")
                .build()
                .encode()
                .toUri();
        return getZboziDtos(uri);
    }

    public List<ZboziDto> findZboziByEanAndDate(String ean, LocalDate datumOd, LocalDate datumDo) {
        URI uri = UriComponentsBuilder.fromUriString(persistenceH2Url)
                .pathSegment("findZboziByEanAndDate", ean)
                .queryParam("datumOd", datumOd)
                .queryParam("datumDo", datumDo)
                .build()
                .encode()
                .toUri();
        return getZboziDtos(uri);
    }

    private List<ZboziDto>  getZboziDtos(URI uri) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ZboziDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

}
