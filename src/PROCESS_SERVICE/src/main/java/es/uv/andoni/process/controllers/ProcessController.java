package es.uv.andoni.process.controllers;

import es.uv.andoni.process.services.ProcessService;
import es.uv.andoni.shared.domain.FileDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process")
public class ProcessController {

  @Autowired
  RabbitTemplate rabitt;

  @Autowired
  ProcessService processService;

  @Value("${rabbitmq.exchange.name}")
  private String exchange;

  @Value("${rabbitmq.routing.key.all}")
  private String kAll;

  @PostMapping("")
  public ResponseEntity<?> sendToProcess(@RequestBody FileDTO file) {
    this.rabitt.convertAndSend(this.exchange, this.kAll, file);
    System.out.println("POST RECIVED: " + file.getTitle());
    return ResponseEntity.ok().build();
  }

  @PutMapping("")
  public ResponseEntity<?> endOfProcess(@RequestBody FileDTO file) {
    System.out.println("PUT RECIVED: " + file.getTitle());
    processService.saveFile(file);
    return ResponseEntity.ok().build();
  }
}
