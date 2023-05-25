package es.uv.andoni.data.repository;

import es.uv.andoni.data.models.Producer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
  List<Producer> findByStateFalse();

  @Query("SELECT p FROM Producer p WHERE p.quota <= 0.0")
  List<Producer> findByQuotaExceeded();

  @Query("SELECT DISTINCT f.producer FROM File f WHERE f.state = 'ERROR'")
  List<Producer> findProducersWithErrors();

  //////////////////////////////////////////////////////////////////////////////
  //Persistencia Relacional y no Relacional//
  //////////////////////////////////////////////////////////////////////////////
  @Query("SELECT p.state, COUNT(p) FROM Producer p GROUP BY p.state")
  List<Object[]> countProducersByState();

  @Query(
    "SELECT p FROM Producer p JOIN File f ON p.id = f.producer.id GROUP BY p HAVING COUNT(f) > 5"
  )
  List<Producer> findProducersWithMoreThan5Files();
}
