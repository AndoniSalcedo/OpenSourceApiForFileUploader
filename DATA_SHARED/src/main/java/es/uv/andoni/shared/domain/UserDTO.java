package es.uv.andoni.shared.domain;

public class UserDTO {

  private Long id;
  private String password;
  private String role;

  public UserDTO() {}

  public UserDTO(Long id, String name, String email, String role) {
    this.id = id;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
