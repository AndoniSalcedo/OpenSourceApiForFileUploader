package es.uv.andoni.data;

import es.uv.andoni.data.models.Validator;
import es.uv.andoni.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SQLDataApplication implements CommandLineRunner {

  @Autowired
  UserRepository us;

  public static void main(String[] args) {
    SpringApplication.run(SQLDataApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Validator val1 = new Validator();
    val1.setEmail("validator@gmail.com");
    val1.setPassword("root");
    val1.setName("validator");
    val1.setState(true);
    if (!us.findUserByEmail(val1.getEmail()).isPresent()) {
      System.out.println("Guardando usuario: " + val1.toString());

      us.save(val1);
    } else {
      System.out.println("Usuario ya existe");
    }
  }
}
