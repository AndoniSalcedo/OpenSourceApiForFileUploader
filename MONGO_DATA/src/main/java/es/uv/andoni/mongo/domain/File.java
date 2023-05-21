package es.uv.andoni.mongo.domain;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class File {

  @Id
  private Long fileId;

  private List<Object> data;

  public File() {}

  public File(Long fileId, List<Object> data) {
    this.fileId = fileId;
    this.data = data;
  }

  public Long getFileId() {
    return fileId;
  }

  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }

  public List<Object> getData() {
    return data;
  }

  public void setData(List<Object> data) {
    this.data = data;
  }
}
