package es.uv.andoni.mongo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.uv.andoni.mongo.domain.File;
import es.uv.andoni.mongo.services.FileService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class MongoController {

  @Autowired
  FileService fs;

  @GetMapping
  public ResponseEntity<List<File>> getAllFiles() {
    List<File> files = fs.findAll();
    return new ResponseEntity<>(files, HttpStatus.OK);
  }

  @GetMapping("/preview/{fileId}")
  public ResponseEntity<?> previewFileById(@PathVariable Long fileId) {
    File file = fs.findById(fileId);
    if (file == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    if (file.getData().size() > 10) {
      return new ResponseEntity<>(file.getData().subList(0, 10), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(file.getData(), HttpStatus.OK);
    }
  }

  @GetMapping("/download/{fileId}")
  public ResponseEntity<?> getFileById(@PathVariable Long fileId) {
    File file = fs.findById(fileId);
    if (file == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(file.getData(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> saveFile(
    @RequestParam MultipartFile file,
    @RequestParam Long fileId
  ) throws IOException {
    String content = new String(file.getBytes(), StandardCharsets.UTF_8);
    ObjectMapper mapper = new ObjectMapper();
    List<Object> data = mapper.readValue(
      content,
      mapper.getTypeFactory().constructCollectionType(List.class, Object.class)
    );

    fs.create(new File(fileId, data));

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{fileId}")
  public ResponseEntity<?> deleteFile(@PathVariable Long fileId) {
    fs.deleteById(fileId);
    return ResponseEntity.ok().build();
  }
}
