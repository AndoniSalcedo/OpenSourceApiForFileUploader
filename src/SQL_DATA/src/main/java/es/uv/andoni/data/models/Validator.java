package es.uv.andoni.data.models;

import es.uv.andoni.shared.domain.UserDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "validators")
@DiscriminatorValue("Validator")
public class Validator extends User {

  public Validator() {}

  public Validator(UserDTO userDTO) {
    this.id = userDTO.getId();
  }

  public UserDTO toDTO() {
    return new UserDTO(
      this.id,
      this.name,
      this.email,
      "Validator",
      this.password
    );
  }
}
