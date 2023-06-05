package es.uv.andoni.producer.services;

import es.uv.andoni.shared.domain.FileDTO;
import es.uv.andoni.shared.domain.ProducerDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProducerService {
  ResponseEntity<ProducerDTO> getProducer(Long producerId);

  ResponseEntity<ProducerDTO> saveProducer(ProducerDTO producerDTO);

  ResponseEntity<FileDTO> saveFile(FileDTO fileDTO);
  ResponseEntity<Void> saveFile(MultipartFile file, Long fileId);

  ResponseEntity<List<FileDTO>> getFiles(Long producerId);

  ResponseEntity<FileDTO> getFile(Long producerId, Long fileId);

  ResponseEntity<Void> deleteFile(Long fileId);
  ResponseEntity<Void> deleteDataFile(Long fileId);
}
