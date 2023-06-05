package es.uv.andoni.data.models;

import es.uv.andoni.shared.domain.ProducerDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "producers")
@DiscriminatorValue("Producer")
public class Producer extends User {

  private String nif;

  private String type;

  private Double quota;

  public Producer() {}

  public Producer(ProducerDTO producerDTO) {
    this.nif = producerDTO.getNif();
    this.type = producerDTO.getType();
    this.quota = producerDTO.getQuota();
    this.state = producerDTO.getState();
    this.id = producerDTO.getId();
    this.email = producerDTO.getEmail();
    this.password = producerDTO.getPassword();
    this.name = producerDTO.getName();
  }

  public ProducerDTO toDTO() {
    return new ProducerDTO(
      this.id,
      this.name,
      this.email,
      this.password,
      this.nif,
      this.state,
      this.quota,
      this.type
    );
  }

  public String getNif() {
    return nif;
  }

  public void setNif(String nif) {
    this.nif = nif;
  }

  public Double getQuota() {
    return quota;
  }

  public void setQuota(Double quota) {
    this.quota = quota;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
