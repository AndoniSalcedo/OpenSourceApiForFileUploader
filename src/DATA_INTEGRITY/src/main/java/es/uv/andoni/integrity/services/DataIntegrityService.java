package es.uv.andoni.integrity.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

@Service
public class DataIntegrityService {

  @Value("${url.data.service}")
  private String DATA_SERVICE_URL;

  @Value("${url.process.service}")
  private String PROCESS_SERVICE_URL;

  private final RestTemplate restTemplate = new RestTemplate();

  public void checkDataIntegrity() {
    List<Long> dataServiceIds = fetchIds(DATA_SERVICE_URL);
    List<Long> processServiceIds = fetchIds(PROCESS_SERVICE_URL);

    dataServiceIds
      .stream()
      .filter(id -> !processServiceIds.contains(id))
      .forEach(id -> deleteId(DATA_SERVICE_URL, id));

    processServiceIds
      .stream()
      .filter(id -> !dataServiceIds.contains(id))
      .forEach(id -> deleteId(PROCESS_SERVICE_URL, id));
  }

  private List<Long> fetchIds(String serviceUrl) {
    ResponseEntity<List<Long>> response = restTemplate.exchange(
      serviceUrl + "/files",
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<List<Long>>() {}
    );
    return response.getBody();
  }

  private void deleteId(String serviceUrl, Long id) {
    restTemplate.execute(
      serviceUrl + "/files/" + id,
      HttpMethod.DELETE,
      null,
      (ResponseExtractor<Void>) null
    );
  }

  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) {
    checkDataIntegrity();
  }
}
