package es.uv.andoni.process.services;

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
public class ProcessService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${url.data.service}")
  private String DATA_SERVICE_URL;

  public ResponseEntity<FileDTO> saveFile(FileDTO fileDTO) {
    String url = DATA_SERVICE_URL + "/files";
    return restTemplate.postForEntity(url, fileDTO, FileDTO.class);
  }
}
