package es.uv.andoni.data.services;

import es.uv.andoni.data.models.Producer;
import es.uv.andoni.data.repository.ProducerRepository;
import es.uv.andoni.shared.domain.ProducerDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class ProducerService {

  @Autowired
  private ProducerRepository producerRepository;

  public List<Producer> getAllProducers() {
    return producerRepository.findAll();
  }

  public List<Producer> getProducers(String filter) {
    if (filter == null) {
      return producerRepository.findAll();
    }

    switch (filter) {
      case "unapproved":
        return producerRepository.findByStateFalse();
      case "quota_exceeded":
        return producerRepository.findByQuotaExceeded();
      case "error_files":
        return producerRepository.findProducersWithErrors();
    }
    throw new HttpServerErrorException(
      HttpStatus.NOT_IMPLEMENTED,
      "Filtro no implementado"
    );
  }

  public Producer getProducerById(Long producerId) {
    Optional<Producer> optionalProducer = producerRepository.findById(
      producerId
    );
    if (optionalProducer.isPresent()) {
      return optionalProducer.get();
    }
    throw new EntityNotFoundException(
      "No se pudo encontrar un productor con el ID: " + producerId
    );
  }

  public Producer saveProducer(ProducerDTO producerDTO) {
    Producer producer = new Producer(producerDTO);
    return producerRepository.save(producer);
  }

  public void deleteProducer(Long producerId) {
    producerRepository.deleteById(producerId);
  }
}
