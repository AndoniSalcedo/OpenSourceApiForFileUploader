package es.uv.andoni.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FileDTO implements Serializable {

  public enum State {
    READY,
    ERROR,
    PENDING,
    PREPARING,
  }

  //SYSTEM VALUES
  private Long id;

  private Date creationDate;

  private String state;

  private Integer numViews;

  private Integer numDownloads;

  private ProducerDTO producer;

  private UserDTO validator;
  // USER VALUES
  private String title;

  private String description;

  private List<String> keyWords;

  private Integer size;

  public FileDTO() {}

  public FileDTO(NewFileDTO f) {
    this.title = f.getTitle();
    this.description = f.getDescription();
    this.keyWords = f.getKeyWords();
    this.size = f.getSize();

    this.state = State.PENDING.name();
    this.numViews = 0;
    this.numDownloads = 0;
  }

  public FileDTO(
    Long id,
    Date creationDate,
    String state,
    Integer numViews,
    Integer numDownloads,
    ProducerDTO producer,
    UserDTO validator,
    String title,
    String description,
    List<String> keyWords,
    Integer size
  ) {
    this.id = id;
    this.creationDate = creationDate;
    this.state = state;
    this.numViews = numViews;
    this.numDownloads = numDownloads;
    this.producer = producer;
    this.title = title;
    this.description = description;
    this.keyWords = keyWords;
    this.validator = validator;
    this.size = size;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
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

  public String getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state.name();
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Integer getNumViews() {
    return numViews;
  }

  public void setNumViews(Integer numViews) {
    this.numViews = numViews;
  }

  public Integer getNumDownloads() {
    return numDownloads;
  }

  public void setNumDownloads(Integer numDownloads) {
    this.numDownloads = numDownloads;
  }

  public ProducerDTO getProducer() {
    return producer;
  }

  public void setProducer(ProducerDTO producer) {
    this.producer = producer;
  }

  public void setState(String state) {
    this.state = state;
  }

  public UserDTO getValidator() {
    return validator;
  }

  public void setValidator(UserDTO validator) {
    this.validator = validator;
  }

  @Override
  public String toString() {
    return (
      "FileDTO [id=" +
      id +
      ", creationDate=" +
      creationDate +
      ", state=" +
      state +
      ", numViews=" +
      numViews +
      ", numDownloads=" +
      numDownloads +
      ", producer=" +
      producer +
      ", title=" +
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
