package es.uv.andoni.data.controllers;

import es.uv.andoni.data.models.Producer;
import es.uv.andoni.data.services.ProducerService;
import es.uv.andoni.shared.domain.ProducerDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producers")
public class ProducerController {

  Logger log = LoggerFactory.getLogger(ProducerController.class);

  @Autowired
  private ProducerService producerService;

  @GetMapping
  public ResponseEntity<?> getProducers(
    @RequestParam(required = false) String filter
  ) {
    log.info("Getting all producers");
    List<Producer> producers = producerService.getProducers(filter);
    return new ResponseEntity<List<Producer>>(producers, HttpStatus.OK);
  }

  @GetMapping("/{producerId}")
  public ResponseEntity<?> getProducerById(@PathVariable Long producerId) {
    log.info("Getting producer with id: " + producerId);
    Producer producer = producerService.getProducerById(producerId);
    return new ResponseEntity<Producer>(producer, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> saveProducer(@RequestBody ProducerDTO producer) {
    log.info("Saving producer: " + producer.toString());

    Producer newProducer = producerService.saveProducer(producer);
    return new ResponseEntity<Producer>(newProducer, HttpStatus.CREATED);
  }

  @DeleteMapping("/{producerId}")
  public ResponseEntity<?> deleteProducer(@PathVariable Long producerId) {
    log.info("Deleting producer with id: " + producerId);
    producerService.deleteProducer(producerId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
