package es.uv.andoni.data.services;

import es.uv.andoni.data.models.File;
import es.uv.andoni.data.models.Producer;
import es.uv.andoni.data.models.Validator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

  @Autowired
  DataInitService dataInitService;

  @Autowired
  PRPNRService prpnrService;

  public void test() {
    // Generar datos
    List<Validator> validators = dataInitService.getValidators();
    List<Producer> producers = dataInitService.getProducers();
    dataInitService.getFiles();

    // Prueba Q1
    List<File> topFiles = prpnrService.findTop10ByViewsAndDownloads();
    System.out.println("Top 10 Files:");
    for (File file : topFiles) {
      System.out.println(file.getTitle());
    }

    // Prueba Q2
    Validator validator = validators.get(0);
    Producer producer = producers.get(0);
    List<File> validatorFiles = prpnrService.findByValidatorAndProducer(
      validator,
      producer
    );
    System.out.println(
      "\nFiles validated by validator and produced by producer:"
    );
    for (File file : validatorFiles) {
      System.out.println(file.getTitle());
    }
    Map<Boolean, Long> producerStateCounts = prpnrService.countProducersByState();
    System.out.println("\nNumber of Producers by state:");
    for (Map.Entry<Boolean, Long> entry : producerStateCounts.entrySet()) {
      System.out.println(
        "State: " +
        (entry.getKey() ? "Active" : "Inactive") +
        ", Count: " +
        entry.getValue()
      );
    }

    // Prueba Q4
    List<Producer> activeProducers = prpnrService.findProducersWithMoreThan5Files();
    System.out.println("\nProducers with more than 5 files:");
    for (Producer activeProducer : activeProducers) {
      System.out.println(activeProducer.getName());
    }
  }
}
