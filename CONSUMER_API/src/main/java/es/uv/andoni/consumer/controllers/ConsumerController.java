package es.uv.andoni.consumer.controllers;

import es.uv.andoni.consumer.services.ConsumerService;
import es.uv.andoni.shared.domain.FileDTO;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

  @Autowired
  private ConsumerService consumerService;

  @GetMapping("/files")
  @Operation(
    summary = "Get Files By Keyword",
    description = "Obtener listado ordenado de ficheros por palabra clave. Se buscarán solo ficheros con estado publicado, se indicará la palabra clave y se permitirá ordenar por fecha de creación (de más reciente a más antiguo, opción por defecto), por tamaño  (de más grande a más pequeño) o por número de descargas (de más a menos veces descargado). Se obtendrá el identificador del fichero, título, descripción,   nombre del productor, fecha de creación, formato, tamaño, número de previsualizaciones y de descargas. No se requerirá autenticación."
  )
  public ResponseEntity<List<FileDTO>> getFilesByKeyword(
    @RequestParam String keyword,
    @RequestParam(
      required = false,
      defaultValue = "creationDate"
    ) String orderBy
  ) {
    return consumerService.getFilesByKeyword(keyword, orderBy);
  }

  @GetMapping("/files/producers")
  @Operation(
    summary = "Get Files By Producer Name",
    description = "Obtener listado de ficheros por nombre de productor. Se buscarán solo ficheros con estado publicado, se indicará el nombre o razón social del productor. Se obtendrá el identificador del fichero, título, descripción, fecha de creación, formato, tamaño, número de previsualizaciones y de descargas. No se requerirá autenticación."
  )
  public ResponseEntity<List<FileDTO>> getFilesByProducerName(
    @RequestParam String producerName
  ) {
    return consumerService.getFilesByProducerName(producerName);
  }

  @GetMapping("/files/preview/{fileId}")
  @Operation(
    summary = "Get Files By Producer Name",
    description = "Obtener una previsualización de un fichero. Se proporcionará el identificador  de un fichero publicado y se obtendrán las primeras 10 observaciones del fichero. Se  incrementará el número de previsualizaciones del fichero. No se requerirá autenticación."
  )
  public ResponseEntity<?> previewFile(@PathVariable Long fileId) {
    consumerService.addPreviewFile(fileId);

    return consumerService.previewFile(fileId);
  }

  //TODO: Se debe descarga el archivo??

  @GetMapping("/files/download/{fileId}")
  @Operation(
    summary = "Get Files By Producer Name",
    description = "Obtener un fichero completo. Se proporcionará el identificador de un fichero  publicado y se obtendrá todo el fichero. Se incrementará el número de descargas del fichero. No se requerirá autenticación"
  )
  public ResponseEntity<?> downloadFile(@PathVariable Long fileId) {
    consumerService.addDownloadFile(fileId);

    return consumerService.downloadFile(fileId);
  }
}
