package es.uv.andoni.shared.domain;

import java.io.Serializable;

public class ProducerDTO implements Serializable {

  //SYSTEM
  private Long id;
  private Boolean state;
  private Double quota;

  //USER
  private String nif;
  private String name;
  private String email;
  private String password;
  private String type;

  public ProducerDTO() {}

  public ProducerDTO(NewProducerDTO p) {
    this.nif = p.getNif();
    this.name = p.getName();
    this.email = p.getEmail();
    this.password = p.getPassword();
    this.type = p.getType();
    this.state = false;
    this.quota = 0.0;
    this.id = 0L;
  }

  public ProducerDTO(
    Long id,
    String name,
    String email,
    String password,
    String nif,
    Boolean state,
    Double quota,
    String type
  ) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.nif = nif;
    this.state = state;
    this.quota = quota;
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNif() {
    return nif;
  }

  public void setNif(String nif) {
    this.nif = nif;
  }

  public Boolean getState() {
    return state;
  }

  public void setState(Boolean state) {
    this.state = state;
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

  @Override
  public String toString() {
    return (
      "ProducerDTO [id=" +
      id +
      ", state=" +
      state +
      ", quota=" +
      quota +
      ", nif=" +
      nif +
      ", name=" +
      name +
      ", email=" +
      email +
      ", password=" +
      password +
      ", type=" +
      type +
      "]"
    );
  }
}
