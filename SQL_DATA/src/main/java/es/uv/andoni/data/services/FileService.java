package es.uv.andoni.data.services;

import es.uv.andoni.data.models.File;
import es.uv.andoni.data.repository.FileRepository;
import es.uv.andoni.shared.domain.FileDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FileService {

  @Autowired
  private FileRepository fileRepository;

  public File saveFile(FileDTO fileDTO) {
    File file = new File(fileDTO);

    return fileRepository.save(file);
  }

  public List<File> getAllFiles() {
    return fileRepository.findAll();
  }

  public List<File> getFiles(Long producerId) {
    return fileRepository.findAllByProducerId(producerId);
  }

  public List<File> getFiles(String keyword, String orderBy) {
    Sort sort = Sort.by(Sort.Direction.DESC, orderBy);
    return fileRepository.findPublishedFilesByKeywordSorted(keyword, sort);
  }

  public List<File> getFiles(String producerName) {
    return fileRepository.findByStateAndProducerName("READY", producerName);
  }

  public List<File> getFilesPending() {
    return fileRepository.findAllByState(FileDTO.State.PENDING.name());
  }

  public File getFile(Long fileId) {
    Optional<File> file = fileRepository.findById(fileId);

    if (file.isPresent()) {
      return file.get();
    }
    throw new EntityNotFoundException(
      "No se pudo encontrar un fichero con el ID: " + fileId
    );
  }

  public File getFile(Long producerId, Long fileId) {
    Optional<File> file = fileRepository.findByIdAndProducerId(
      fileId,
      producerId
    );

    if (file.isPresent()) {
      return file.get();
    }
    throw new EntityNotFoundException(
      "No se pudo encontrar un fichero con el ID: " +
      fileId +
      " que pertenezca al productor con el ID: " +
      producerId
    );
  }

  @Transactional
  public void incrementViews(Long id) {
    fileRepository.incrementNumViews(id);
  }

  @Transactional
  public void incrementDownloads(Long id) {
    fileRepository.incrementNumDownloads(id);
  }

  public void deleteFile(Long fileId) {
    fileRepository.deleteById(fileId);
  }
  //////////////////////////////////////////////////////
  //////////////////////////////////////////////////////
  //////////////////////////////////////////////////////
  //////////////////////////////////////////////////////
  //////////////////////////////////////////////////////

  /*   public List<File> findByTitleContaining(String title) {
    return fileRepository.findByTitleContaining(title);
  }

  public List<File> findByKeyWordsIn(List<String> keyWords) {
    return fileRepository.findByKeyWordsIn(keyWords);
  }

  public List<File> findTop10OrderBynumViewsDescnumDownloadsDesc() {
    return fileRepository.findTop10OrderBynumViewsDescnumDownloadsDesc();
  }

  public List<File> findFilesOfProductor(Long productorId) {
    return fileRepository.findFilesOfProductor(productorId);
  } */
}
