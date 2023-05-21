package es.uv.andoni.validator.services;

import es.uv.andoni.shared.domain.FileDTO;
import es.uv.andoni.shared.domain.ProducerDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ValidatorService {
  ResponseEntity<List<ProducerDTO>> getProducers(String filter);
  ResponseEntity<ProducerDTO> getProducer(Long producerId);
  ResponseEntity<ProducerDTO> saveProducer(ProducerDTO producerDTO);
  ResponseEntity<Void> deleteProducer(Long producerId);
  ResponseEntity<List<FileDTO>> getFilesPending();
  ResponseEntity<FileDTO> getFile(Long fileId);
  ResponseEntity<FileDTO> saveFile(FileDTO fileDTO);
  ResponseEntity<Void> processFile(FileDTO fileDTO);
}
