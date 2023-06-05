package es.uv.andoni.shared.domain;

public class UserDTO {

  private Long id;
  private String name;
  private String email;
  private String password;

  private String role;

  public UserDTO() {}

  public UserDTO(
    Long id,
    String name,
    String email,
    String role,
    String password
  ) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.role = role;
    this.password = password;
  }

  public UserDTO(Long id) {
    this.id = id;
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
}
