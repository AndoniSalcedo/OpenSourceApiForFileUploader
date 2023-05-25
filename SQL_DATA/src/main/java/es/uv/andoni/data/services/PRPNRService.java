package es.uv.andoni.data.services;

import es.uv.andoni.data.models.File;
import es.uv.andoni.data.models.Producer;
import es.uv.andoni.data.models.Validator;
import es.uv.andoni.data.repository.FileRepository;
import es.uv.andoni.data.repository.ProducerRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PRPNRService {

  @Autowired
  private FileRepository fileRepository;

  @Autowired
  private ProducerRepository producerRepository;

  public List<File> findTop10ByViewsAndDownloads() {
    return fileRepository.findTop10ByOrderByNumViewsDescNumDownloadsDesc(
      PageRequest.of(0, 10)
    );
  }

  public List<File> findByValidatorAndProducer(
    Validator validator,
    Producer producer
  ) {
    return fileRepository.findByValidatorAndProducer(validator, producer);
  }

  public Map<Boolean, Long> countProducersByState() {
    List<Object[]> results = producerRepository.countProducersByState();
    Map<Boolean, Long> resultMap = new HashMap<>();
    for (Object[] result : results) {
      resultMap.put((Boolean) result[0], (Long) result[1]);
    }
    return resultMap;
  }

  public List<Producer> findProducersWithMoreThan5Files() {
    return producerRepository.findProducersWithMoreThan5Files();
  }
}
