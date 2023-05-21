package es.uv.andoni.validator.services;

import es.uv.andoni.shared.domain.FileDTO;
import es.uv.andoni.shared.domain.ProducerDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ValidatorServiceImpl implements ValidatorService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${url.data.service}")
  private String DATA_SERVICE_URL;

  @Value("${url.process.service}")
  private String PROCESS_SERVICE_URL;

  //PRODUCERS

  @Override
  public ResponseEntity<List<ProducerDTO>> getProducers(String filter) {
    UriComponentsBuilder url;
    if (filter == null) {
      url = UriComponentsBuilder.fromHttpUrl(DATA_SERVICE_URL + "/producers");
    } else {
      url =
        UriComponentsBuilder
          .fromHttpUrl(DATA_SERVICE_URL + "/producers")
          .queryParam("filter", filter);
    }

    return restTemplate.exchange(
      url.toUriString(),
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<List<ProducerDTO>>() {}
    );
  }

  @Override
  public ResponseEntity<ProducerDTO> getProducer(Long producerId) {
    String url = DATA_SERVICE_URL + "/producers/" + producerId;
    return restTemplate.getForEntity(url, ProducerDTO.class);
  }

  @Override
  public ResponseEntity<ProducerDTO> saveProducer(ProducerDTO producerDTO) {
    String url = DATA_SERVICE_URL + "/producers";
    return restTemplate.postForEntity(url, producerDTO, ProducerDTO.class);
  }

  @Override
  public ResponseEntity<Void> deleteProducer(Long producerId) {
    String url = DATA_SERVICE_URL + "/producers/" + producerId;
    return restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
  }

  //FILES

  @Override
  public ResponseEntity<List<FileDTO>> getFilesPending() {
    String url = DATA_SERVICE_URL + "/files/pending";
    return restTemplate.exchange(
      url,
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<List<FileDTO>>() {}
    );
  }

  @Override
  public ResponseEntity<FileDTO> getFile(Long fileId) {
    UriComponentsBuilder url = UriComponentsBuilder
      .fromHttpUrl(DATA_SERVICE_URL + "/files")
      .queryParam("fileId", fileId);

    return restTemplate.getForEntity(url.toUriString(), FileDTO.class);
  }

  @Override
  public ResponseEntity<FileDTO> saveFile(FileDTO fileDTO) {
    String url = DATA_SERVICE_URL + "/files";
    return restTemplate.postForEntity(url, fileDTO, FileDTO.class);
  }

  //PROCESS

  @Override
  public ResponseEntity<Void> processFile(FileDTO fileDTO) {
    String url = PROCESS_SERVICE_URL + "/process";
    return restTemplate.postForEntity(url, fileDTO, Void.class);
  }
}
