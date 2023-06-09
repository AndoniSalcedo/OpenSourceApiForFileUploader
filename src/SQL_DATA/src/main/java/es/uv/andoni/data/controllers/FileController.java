package es.uv.andoni.data.controllers;

import es.uv.andoni.data.models.File;
import es.uv.andoni.data.services.FileService;
import es.uv.andoni.shared.domain.FileDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class FileController {

  Logger log = LoggerFactory.getLogger(FileController.class);

  @Autowired
  private FileService fileService;

  @GetMapping
  public ResponseEntity<?> getFile(
    @RequestParam(required = false) Long fileId,
    @RequestParam(required = false) Long producerId,
    @RequestParam(required = false) String keyword,
    @RequestParam(required = false) String orderBy,
    @RequestParam(required = false) String producerName
  ) {
    try {
      log.info("Getting file");
      log.info(
        "fileId: " +
        fileId +
        " producerId: " +
        producerId +
        " keyword: " +
        keyword +
        " orderBy: " +
        orderBy +
        " producerName: " +
        producerName
      );

      if (fileId != null) {
        if (producerId != null) {
          File files = fileService.getFile(producerId, fileId);
          return new ResponseEntity<File>(files, HttpStatus.OK);
        }
        File files = fileService.getFile(fileId);
        return new ResponseEntity<File>(files, HttpStatus.OK);
      }

      if (producerId != null) {
        List<File> files = fileService.getFiles(producerId);
        return new ResponseEntity<List<File>>(files, HttpStatus.OK);
      }

      if (keyword != null && orderBy != null) {
        List<File> files = fileService.getFiles(keyword, orderBy);
        return new ResponseEntity<List<File>>(files, HttpStatus.OK);
      }

      if (producerName != null) {
        List<File> files = fileService.getFiles(producerName);
        return new ResponseEntity<List<File>>(files, HttpStatus.OK);
      }
      List<File> files = fileService.getAllFiles();
      return new ResponseEntity<List<File>>(files, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/pending")
  public ResponseEntity<?> getFilesPending() {
    log.info("Getting files pending");

    try {
      List<File> files = fileService.getFilesPending();
      return new ResponseEntity<List<File>>(files, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping
  public ResponseEntity<?> saveFile(@RequestBody FileDTO file) {
    try {
      log.info("Saving file");
      log.info("file: " + file);
      File newFile = fileService.saveFile(file);
      System.out.println(newFile);
      return new ResponseEntity<File>(newFile, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("preview/{fileId}")
  public ResponseEntity<?> previewFileFile(@PathVariable Long fileId) {
    try {
      fileService.incrementViews(fileId);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping("download/{fileId}")
  public ResponseEntity<?> downloadFileFile(@PathVariable Long fileId) {
    try {
      fileService.incrementDownloads(fileId);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{fileId}")
  public ResponseEntity<?> deleteFile(@PathVariable Long fileId) {
    try {
      log.info("Deleting file with id " + fileId);
      fileService.deleteFile(fileId);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
