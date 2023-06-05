package es.uv.andoni.mongo.services;

import es.uv.andoni.mongo.domain.File;
import es.uv.andoni.mongo.repositories.FileRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

  @Autowired
  FileRepository fr;

  public List<File> findAll() {
    return this.fr.findAll();
  }

  public File create(File file) {
    return this.fr.save(file);
  }

  public File findById(Long id) {
    return this.fr.findById(id).orElse(null);
  }

  public void deleteById(Long id) {
    this.fr.deleteById(id);
  }
}
