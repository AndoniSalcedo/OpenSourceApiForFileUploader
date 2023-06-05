package es.uv.ansalna.services;

import es.uv.andoni.shared.domain.FileDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WorkerService {

  @Autowired
  RestTemplate restTemplate;

  @Value("${url.process.service}")
  String PROCESS_SERVICE_URL;

  public static void simulate(int n) {
    long startTime = System.currentTimeMillis();

    while (System.currentTimeMillis() - startTime < n) {
      Math.pow(2, Math.random() * 1000000);
    }
  }

  @RabbitListener(queues = { "${rabbitmq.queue.worker}" })
  public void compute(FileDTO file) {
    System.out.println("EVENT RECIVED: " + file.getSize());

    simulate(file.getSize());

    if (file.getKeyWords().contains("error")) {
      file.setState(FileDTO.State.ERROR);
    } else {
      file.setState(FileDTO.State.READY);
    }

    HttpEntity<FileDTO> req = new HttpEntity<>(file);

    restTemplate.put(PROCESS_SERVICE_URL + "/process", req);
  }
}
