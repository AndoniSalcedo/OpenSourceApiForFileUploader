package es.uv.andoni.shared.domain;

import java.io.Serializable;

public class NewProducerDTO implements Serializable {

  private String nif;
  private String name;
  private String email;
  private String password;
  private String type;

  public NewProducerDTO() {}

  public String getNif() {
    return nif;
  }

  public void setNif(String nif) {
    this.nif = nif;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return (
      "NewProducerDTO [nif=" +
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
