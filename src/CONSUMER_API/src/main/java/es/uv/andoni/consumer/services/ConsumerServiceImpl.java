package es.uv.andoni.consumer.services;

import es.uv.andoni.shared.domain.FileDTO;
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
public class ConsumerServiceImpl implements ConsumerService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${url.data.service}")
  private String DATA_SERVICE_URL;

  @Value("${url.mongo.service}")
  private String MONGO_SERVICE_URL;

  @Override
  public ResponseEntity<List<FileDTO>> getFilesByKeyword(
    String keyword,
    String orderBy
  ) {
    try {
      UriComponentsBuilder url = UriComponentsBuilder
        .fromHttpUrl(DATA_SERVICE_URL + "/files")
        .queryParam("keyword", keyword)
        .queryParam("orderBy", orderBy);
      return restTemplate.exchange(
        url.toUriString(),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<FileDTO>>() {}
      );
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  public ResponseEntity<List<FileDTO>> getFilesByProducerName(
    String producerName
  ) {
    try {
      UriComponentsBuilder url = UriComponentsBuilder
        .fromHttpUrl(DATA_SERVICE_URL + "/files")
        .queryParam("producerName", producerName);
      return restTemplate.exchange(
        url.toUriString(),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<FileDTO>>() {}
      );
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  public ResponseEntity<FileDTO> addPreviewFile(Long fileId) {
    try {
      String url = DATA_SERVICE_URL + "/files" + "/preview/" + fileId;
      return restTemplate.exchange(url, HttpMethod.PUT, null, FileDTO.class);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  public ResponseEntity<List<Object>> previewFile(Long fileId) {
    try {
      String url = MONGO_SERVICE_URL + "/files" + "/preview/" + fileId;
      return restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Object>>() {}
      );
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  public ResponseEntity<FileDTO> addDownloadFile(Long fileId) {
    try {
      String url = DATA_SERVICE_URL + "/files" + "/download/" + fileId;
      return restTemplate.exchange(url, HttpMethod.PUT, null, FileDTO.class);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  public ResponseEntity<List<Object>> downloadFile(Long fileId) {
    try {
      String url = MONGO_SERVICE_URL + "/files" + "/download/" + fileId;
      return restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Object>>() {}
      );
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
