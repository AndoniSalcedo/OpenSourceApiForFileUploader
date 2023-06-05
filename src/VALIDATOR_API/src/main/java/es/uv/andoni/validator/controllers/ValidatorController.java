package es.uv.andoni.validator.controllers;

import es.uv.andoni.shared.domain.FileDTO;
import es.uv.andoni.shared.domain.ProducerDTO;
import es.uv.andoni.shared.domain.UserDTO;
import es.uv.andoni.validator.services.ValidatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/validator")
public class ValidatorController {

  Logger log = LoggerFactory.getLogger(ValidatorController.class);

  @Autowired
  private ValidatorService validatorService;

  @GetMapping("/producers")
  @Operation(
    summary = "Get Producers",
    description = "Obtener el listado de productores. Si no se indica ningún filtro se devolverá todo el listado de productores. Opcionalmente se pueden indicar los siguientes filtros: solo pendientes de aprobación, solo los que haya consumido su cuota anual o solo los que tengan algún fichero erróneo. Requerirá autenticación.",
    parameters = {
      @Parameter(
        name = "filter",
        required = false,
        description = "Filtro opcional. Puede ser 'unapproved' (solo pendientes de aprobación), 'quota_exceeded' (solo los que haya consumido su cuota anual), o 'error_files' (solo los que tengan algún fichero erróneo)."
      ),
    }
  )
  public ResponseEntity<List<ProducerDTO>> getProducers(
    @RequestParam(required = false) String filter
  ) {
    return validatorService.getProducers(filter);
  }

  @PostMapping("/producers/approve/{producerId}")
  @Operation(
    summary = "Approve Producer",
    description = "Aprobar un nuevo productor. Se indicará el identificador del productor y la cuota anual. Cambiará el estado a activo. Requerirá autenticación."
  )
  public ResponseEntity<?> approveProducer(
    @PathVariable Long producerId,
    @RequestParam Double quota
  ) {
    ProducerDTO producer = validatorService.getProducer(producerId).getBody();
    if (producer == null) {
      return ResponseEntity.notFound().build();
    }

    producer.setState(true);
    producer.setQuota(quota);

    return validatorService.saveProducer(producer);
  }

  @PutMapping("/producers/{producerId}")
  @Operation(
    summary = "Update Producer",
    description = "Modificación de la información de un productor (VF3). Se podrá actualizar cualquier campo del productor a través de su identificador. Requerirá autenticación."
  )
  public ResponseEntity<?> updateProducer(
    @PathVariable Long producerId,
    @RequestBody ProducerDTO producerDTO
  ) {
    ProducerDTO producer = validatorService.getProducer(producerId).getBody();
    if (producer == null) {
      return ResponseEntity.notFound().build();
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
    if (producerDTO.getId() != null) {
      producer.setId(producerDTO.getId());
    }
    if (producerDTO.getState() != null) {
      producer.setState(producerDTO.getState());
    }
    if (producerDTO.getQuota() != null) {
      producer.setQuota(producerDTO.getQuota());
    }
    return validatorService.saveProducer(producer);
  }

  @Operation(
    summary = "Delete Producer",
    description = "Eliminar un productor. Se indicará el identificador del productor. Requerirá autenticación"
  )
  @DeleteMapping("/producers/{producerId}")
  public ResponseEntity<?> deleteProducer(@PathVariable Long producerId) {
    return validatorService.deleteProducer(producerId);
  }

  @Operation(
    summary = "Get Files Pending Review",
    description = "Obtener el listado de ficheros pendientes de revisión. Para cada fichero se obtendrá el identificador, título, descripción, nombre del productor y las primeras 10 observaciones. Requerirá autenticación."
  )
  @GetMapping("/files/pending")
  public ResponseEntity<List<FileDTO>> getFilesPendingReview() {
    return validatorService.getFilesPending();
  }

  @Operation(
    summary = "Prepare And Publish File",
    description = "Preparación y publicación de un fichero. Se indicará el identificador del fichero y se cambiará su estado a “en preparación”. Se enviará un mensaje a una cola en RabbitMQ para que se realice una tarea de procesamiento previa a la publicación. El  mensaje será atendido por un servicio de procesado (balanceado entre 4 instancias)  que implementará una espera en función del tamaño del fichero con el objetivo de emular la ejecución de un conjunto de acciones de larga duración. Tras este procesamiento se cambiará el estado a publicado. Si el productor especificó la palabra clave “error”, simularemos que se ha producido un error en la tarea de  procesamiento y el estado cambiará a erróneo. Requerirá autenticació."
  )
  @PostMapping("/process/{fileId}")
  public ResponseEntity<?> prepareAndPublishFile(
    @PathVariable Long fileId,
    @RequestHeader("x-auth-user-id") Long userId
  ) {
    if (userId == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    log.info("Processing file id " + fileId);
    FileDTO file = validatorService.getFile(fileId).getBody();

    if (file == null) {
      return ResponseEntity.notFound().build();
    }

    file.setValidator(new UserDTO(userId));

    log.info(file.toString());

    HttpStatusCode status = validatorService.processFile(file).getStatusCode();

    if (!status.is2xxSuccessful()) {
      return ResponseEntity.status(status).build();
    }

    file.setState(FileDTO.State.PREPARING);
    return validatorService.saveFile(file);
  }
}
