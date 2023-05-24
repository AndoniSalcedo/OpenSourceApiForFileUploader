package es.uv.andoni.data.models;

import es.uv.andoni.shared.domain.FileDTO;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "files")
public class File {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreationTimestamp
  @Column(updatable = false)
  private Date creationDate;

  private String title;

  private String description;

  @ElementCollection
  private List<String> keyWords;

  private String state;

  private Integer size;

  private Integer numViews;

  private Integer numDownloads;

  @ManyToOne
  @JoinColumn(name = "productor_id")
  private Producer producer;

  public File() {}

  public File(FileDTO fileDTO) {
    this.id = fileDTO.getId();
    this.creationDate = fileDTO.getCreationDate();
    this.title = fileDTO.getTitle();
    this.description = fileDTO.getDescription();
    this.keyWords = fileDTO.getKeyWords();
    this.state = fileDTO.getState();
    this.size = fileDTO.getSize();
    this.numViews = fileDTO.getNumViews();
    this.numDownloads = fileDTO.getNumDownloads();
    this.producer = new Producer(fileDTO.getProducer());
  }

  public FileDTO toDTO() {
    return new FileDTO(
      this.id,
      this.creationDate,
      this.state,
      this.numViews,
      this.numDownloads,
      this.producer.toDTO(),
      this.title,
      this.description,
      this.keyWords,
      this.size
    );
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

  public void setState(String state) {
    this.state = state;
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

  public Producer getProducer() {
    return producer;
  }

  public void setProducer(Producer producer) {
    this.producer = producer;
  }
}
