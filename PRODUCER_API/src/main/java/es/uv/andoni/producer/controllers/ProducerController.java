package es.uv.andoni.producer.controllers;

import es.uv.andoni.producer.services.ProducerService;
import es.uv.andoni.shared.domain.FileDTO;
import es.uv.andoni.shared.domain.NewFileDTO;
import es.uv.andoni.shared.domain.NewProducerDTO;
import es.uv.andoni.shared.domain.ProducerDTO;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/producer")
public class ProducerController {

  Logger log = LoggerFactory.getLogger(ProducerController.class);

  @Autowired
  private ProducerService producerService;

  @PostMapping
  @Operation(
    summary = "Register Producer",
    description = "Solicitud de registro de un nuevo productor. Se indicará NIF/CIF, nombre completo o razón social, tipo (persona física o jurídica), e-mail y contraseña. El usuario se creará con estado pendiente de aprobación (por un validador) y sin cuota anual asignada. No se requerirá autenticación."
  )
  public ResponseEntity<?> registerProducer(
    @RequestBody NewProducerDTO producerDTO
  ) {
    ProducerDTO p = new ProducerDTO(producerDTO);
    log.info("Registering Producer: " + p.toString());

    return producerService.saveProducer(p);
  }

  @PutMapping("/{producerId}")
  @Operation(
    summary = "Update Producer",
    description = "Modificación de la información del productor (PF2). Se podrán actualizar los campos especificados en la solicitud de registro Requerirá autenticación y que su estado sea activo."
  )
  public ResponseEntity<?> updateProducer(
    @PathVariable Long producerId,
    @RequestHeader("x-auth-user-id") Long userId,
    @RequestBody ProducerDTO producerDTO
  ) {
    log.info("Updating Producer: " + producerDTO.toString());

    if (userId != producerId) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Usuario no autorizado");
    }

    ProducerDTO producer = producerService.getProducer(producerId).getBody();
    if (producer == null) {
      return ResponseEntity.notFound().build();
    }

    if (producer.getState() == false) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Usuario no activo");
    }

    if (producerDTO.getNif() != null) {
      producer.setNif(producerDTO.getNif());
    }
    if (producerDTO.getName() != null) {
      producer.setName(producerDTO.getName());
    }
    if (producerDTO.getEmail() != null) {
      producer.setEmail(producerDTO.getEmail());
    }
    if (producerDTO.getPassword() != null) {
      producer.setPassword(producerDTO.getPassword());
    }
    if (producerDTO.getType() != null) {
      producer.setType(producerDTO.getType());
    }

    return producerService.saveProducer(producer);
  }

  @GetMapping("/files/{producerId}")
  @Operation(
    summary = "Get Files",
    description = " Consultar el listado de ficheros de datos del productor. Requerirá autenticación y que su estado sea activo."
  )
  public ResponseEntity<?> getFiles(
    @PathVariable Long producerId,
    @RequestHeader("x-auth-user-id") Long userId
  ) {
    log.info("Getting Files for Producer: " + producerId);

    if (userId != producerId) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Usuario no autorizado");
    }

    return producerService.getFiles(producerId);
  }

  @PostMapping("/files/{producerId}")
  @Operation(
    summary = "Upload File",
    description = "Subir un fichero de datos. Junto con el fichero de texto, se indicará el título, descripción y palabras clave. Para emular la carga de ficheros grandes, se indicará de manera manual un tamaño de fichero (en MB) que no tiene porqué coincidir con  el tamaño real del fichero. Se comprobará el formato y que no se exceda la cuota anual. El fichero se creará en estado pendiente de revisión (por un validador). Requerirá autenticación y que su estado sea activo."
  )
  public ResponseEntity<?> uploadFile(
    @PathVariable Long producerId,
    @RequestHeader("x-auth-user-id") Long userId,
    @RequestParam String title,
    @RequestParam String description,
    @RequestParam List<String> keyWords,
    @RequestParam Integer size,
    @RequestParam MultipartFile fileData
  ) {
    log.info("Uploading file " + title);
    if (userId != producerId) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Usuario no autorizado");
    }
    ProducerDTO producer = producerService.getProducer(producerId).getBody();
    if (producer == null) {
      return ResponseEntity.notFound().build();
    }

    if (producer.getState() == false) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Usuario no activo");
    }

    if (producer.getQuota() < 1.0) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Usuario no sin quota restante");
    }

    producer.setQuota(producer.getQuota() - 1.0);

    producerService.saveProducer(producer);

    FileDTO file = new FileDTO(
      new NewFileDTO(title, description, keyWords, size)
    );

    file.setProducer(producer);

    FileDTO newFile = producerService.saveFile(file).getBody();

    if (newFile == null) {
      return ResponseEntity.badRequest().build();
    }

    producerService.saveFile(fileData, newFile.getId());

    return new ResponseEntity<FileDTO>(newFile, HttpStatus.CREATED);
  }

  @PutMapping("/files/{producerId}/{fileId}")
  @Operation(
    summary = "Update File",
    description = "Modificar la información de un fichero de datos del productor. Se podrán actualizar el título, descripción y palabras clave. Requerirá autenticación y que su  estado sea activo."
  )
  public ResponseEntity<?> updateFile(
    @PathVariable Long producerId,
    @RequestHeader("x-auth-user-id") Long userId,
    @PathVariable Long fileId,
    @RequestBody NewFileDTO fileDTO
  ) {
    log.info("Updating File: " + fileDTO.toString());

    if (userId != producerId) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Usuario no autorizado");
    }

    FileDTO file = producerService.getFile(producerId, fileId).getBody();

    if (file == null) {
      return ResponseEntity.notFound().build();
    }

    log.info(file.toString());
    if (fileDTO.getTitle() != null) {
      file.setTitle(fileDTO.getTitle());
    }
    if (fileDTO.getDescription() != null) {
      file.setDescription(fileDTO.getDescription());
    }
    if (fileDTO.getKeyWords() != null) {
      file.setKeyWords(fileDTO.getKeyWords());
    }
    if (fileDTO.getSize() != null) {
      file.setSize(fileDTO.getSize());
    }

    return producerService.saveFile(file);
  }

  @DeleteMapping("/files/{producerId}/{fileId}")
  @Operation(
    summary = "Delete File",
    description = "Eliminar un fichero de datos del productor. Requerirá autenticación y que su estado sea activo."
  )
  public ResponseEntity<?> deleteFile(
    @PathVariable Long producerId,
    @RequestHeader("x-auth-user-id") Long userId,
    @PathVariable Long fileId
  ) {
    log.info("Deleting file  " + fileId);
    if (userId != producerId) {
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("Usuario no autorizado");
    }
    FileDTO file = producerService.getFile(producerId, fileId).getBody();

    if (file == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    log.info("Deleting File: " + fileId);
    producerService.deleteDataFile(fileId);
    return producerService.deleteFile(fileId);
  }
}
