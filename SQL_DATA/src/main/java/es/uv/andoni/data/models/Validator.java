package es.uv.andoni.data.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "validators")
@DiscriminatorValue("Validator")
public class Validator extends User {

  public Validator() {}
}
