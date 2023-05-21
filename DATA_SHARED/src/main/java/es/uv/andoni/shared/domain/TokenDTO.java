package es.uv.andoni.shared.domain;

import java.util.List;

public class TokenDTO {

  private String id;
  private List<String> roles;

  public TokenDTO() {}

  public TokenDTO(String id, List<String> roles) {
    this.id = id;
    this.roles = roles;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}
