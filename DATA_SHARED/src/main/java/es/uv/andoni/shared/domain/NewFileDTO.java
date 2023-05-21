package es.uv.andoni.shared.domain;

import java.io.Serializable;
import java.util.List;

public class NewFileDTO implements Serializable {

  private String title;

  private String description;

  private List<String> keyWords;

  private Integer size;

  public NewFileDTO() {}

  public NewFileDTO(
    String title,
    String description,
    List<String> keyWords,
    Integer size
  ) {
    this.title = title;
    this.description = description;
    this.keyWords = keyWords;
    this.size = size;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getKeyWords() {
    return keyWords;
  }

  public void setKeyWords(List<String> keyWords) {
    this.keyWords = keyWords;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return (
      "NewFileDTO [title=" +
      title +
      ", description=" +
      description +
      ", keyWords=" +
      keyWords +
      ", size=" +
      size +
      "]"
    );
  }
}
