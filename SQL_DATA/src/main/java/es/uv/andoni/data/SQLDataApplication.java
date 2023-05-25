package es.uv.andoni.data;

import es.uv.andoni.data.services.DataInitService;
import es.uv.andoni.data.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SQLDataApplication implements CommandLineRunner {

  @Autowired
  DataInitService ds;

  @Autowired
  TestService ts;

  public static void main(String[] args) {
    SpringApplication.run(SQLDataApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    ds.load();
    ts.test();
  }
}
