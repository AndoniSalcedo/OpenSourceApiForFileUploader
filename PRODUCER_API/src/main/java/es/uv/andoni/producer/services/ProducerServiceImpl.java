package es.uv.andoni.producer.services;

import es.uv.andoni.shared.domain.FileDTO;
import es.uv.andoni.shared.domain.ProducerDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ProducerServiceImpl implements ProducerService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${url.mongo.service}")
  private String MONGO_SERVICE_URL;

  @Value("${url.data.service}")
  private String DATA_SERVICE_URL;

  // PRODUCER
  @Override
  public ResponseEntity<ProducerDTO> getProducer(Long producerId) {
    try {
      String url = DATA_SERVICE_URL + "/producers/" + producerId;
      return restTemplate.getForEntity(url, ProducerDTO.class);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  public ResponseEntity<ProducerDTO> saveProducer(ProducerDTO producerDTO) {
    try {
      String url = DATA_SERVICE_URL + "/producers";
      return restTemplate.postForEntity(url, producerDTO, ProducerDTO.class);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  // FILES

  @Override
  public ResponseEntity<List<FileDTO>> getFiles(Long producerId) {
    try {
      UriComponentsBuilder url = UriComponentsBuilder
        .fromHttpUrl(DATA_SERVICE_URL + "/files")
        .queryParam("producerId", producerId);

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
  public ResponseEntity<FileDTO> getFile(Long producerId, Long fileId) {
    try {
      UriComponentsBuilder url = UriComponentsBuilder
        .fromHttpUrl(DATA_SERVICE_URL + "/files")
        .queryParam("producerId", producerId)
        .queryParam("fileId", fileId);

      return restTemplate.getForEntity(url.toUriString(), FileDTO.class);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  public ResponseEntity<FileDTO> saveFile(FileDTO fileDTO) {
    try {
      String url = DATA_SERVICE_URL + "/files";
      ResponseEntity<FileDTO> responseEntity = restTemplate.postForEntity(
        url,
        fileDTO,
        FileDTO.class
      );

      return responseEntity;
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  public ResponseEntity<Void> saveFile(MultipartFile file, Long fileId) {
    String url = MONGO_SERVICE_URL + "/files";
    try {
      ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
        @Override
        public String getFilename() {
          return file.getOriginalFilename();
        }
      };

      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      body.add("file", fileResource);
      body.add("fileId", fileId);

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
        body,
        headers
      );

      return restTemplate.postForEntity(url, requestEntity, Void.class);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  public ResponseEntity<Void> deleteFile(Long fileId) {
    try {
      String url = DATA_SERVICE_URL + "/files/" + fileId;
      restTemplate.delete(url);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Override
  public ResponseEntity<Void> deleteDataFile(Long fileId) {
    try {
      String url = MONGO_SERVICE_URL + "/files/" + fileId;
      restTemplate.delete(url);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
