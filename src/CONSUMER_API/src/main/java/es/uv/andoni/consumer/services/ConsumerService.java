package es.uv.andoni.consumer.services;

import es.uv.andoni.shared.domain.FileDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ConsumerService {
  ResponseEntity<List<FileDTO>> getFilesByKeyword(
    String keyword,
    String orderBy
  );
  ResponseEntity<List<FileDTO>> getFilesByProducerName(String producerName);

  ResponseEntity<FileDTO> addPreviewFile(Long fileId);

  ResponseEntity<List<Object>> previewFile(Long fileId);

  ResponseEntity<FileDTO> addDownloadFile(Long fileId);

  ResponseEntity<List<Object>> downloadFile(Long fileId);
}
