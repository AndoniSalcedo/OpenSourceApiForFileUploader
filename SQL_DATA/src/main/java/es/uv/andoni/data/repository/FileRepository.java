package es.uv.andoni.data.repository;

import es.uv.andoni.data.models.File;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends JpaRepository<File, Long> {
  List<File> findAllByProducerId(Long producerId);
  Optional<File> findByIdAndProducerId(Long id, Long producerId);
  int deleteByIdAndProducerId(Long id, Long producerId);
  List<File> findAllByState(String state);

  @Query(
    "SELECT f FROM File f WHERE f.state = 'READY' AND :keyword MEMBER OF f.keyWords"
  )
  List<File> findPublishedFilesByKeywordSorted(
    @Param("keyword") String keyword,
    Sort sort
  );

  List<File> findByStateAndProducerName(String state, String producerName);

  @Modifying
  @Query("UPDATE File f SET f.numViews = f.numViews + 1 WHERE f.id = :id")
  void incrementNumViews(@Param("id") Long id);

  @Modifying
  @Query(
    "UPDATE File f SET f.numDownloads = f.numDownloads + 1 WHERE f.id = :id"
  )
  void incrementNumDownloads(@Param("id") Long id);
}
