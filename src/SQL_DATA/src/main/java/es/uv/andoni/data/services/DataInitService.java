package es.uv.andoni.data.services;

import es.uv.andoni.data.models.File;
import es.uv.andoni.data.models.Producer;
import es.uv.andoni.data.models.Validator;
import es.uv.andoni.data.repository.FileRepository;
import es.uv.andoni.data.repository.ProducerRepository;
import es.uv.andoni.data.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataInitService {

  @Autowired
  ProducerRepository pr;

  @Autowired
  FileRepository fr;

  @Autowired
  UserRepository us;

  List<Producer> producers = new ArrayList<>();
  List<Validator> validators = new ArrayList<>();
  List<File> files = new ArrayList<>();

  private static final Random RANDOM = new Random();
  private static final String[] STATES = { "ERROR", "PENDING", "READY" };

  private static final List<String> palabras = Arrays.asList(
    "manzana",
    "perro",
    "casa",
    "amor",
    "juego",
    "coche",
    "futbol",
    "ciencia",
    "java",
    "kubernetes",
    "libro",
    "musica",
    "guitarra",
    "teclado",
    "raton",
    "sol",
    "luna",
    "estrella",
    "planeta",
    "galaxia"
  );

  public static List<String> getTresPalabrasAleatorias() {
    Collections.shuffle(palabras);
    return palabras.subList(0, 3);
  }

  public void load() {
    this.validators = generateValidators(5);
    this.producers = generateProducers(10);
    this.files = generateFiles(52, producers, validators);
  }

  private List<Validator> generateValidators(int count) {
    List<Validator> validators = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      Validator validator = new Validator();
      validator.setName("Validator " + (i + 1));
      validator.setEmail("validator" + (i + 1) + "@example.com");
      validator.setPassword("password");
      validator.setState(true);
      validators.add(validator);

      us.save(validator);
    }
    return validators;
  }

  private List<Producer> generateProducers(int count) {
    List<Producer> producers = new ArrayList<>();
    IntStream
      .range(0, count)
      .forEach(i -> {
        Producer producer = new Producer();
        producer.setName("Producer " + (i + 1));
        producer.setEmail("producer" + (i + 1) + "@example.com");
        producer.setPassword("password");
        producer.setState(i < 8);
        producer.setNif("NIF" + (i + 1));
        producer.setType("Type " + (i + 1));
        producer.setQuota(100.0 + i);
        producers.add(producer);

        pr.save(producer);
      });
    return producers;
  }

  private List<File> generateFiles(
    int count,
    List<Producer> producers,
    List<Validator> validators
  ) {
    List<File> files = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      File file = new File();
      file.setTitle("File " + (i + 1));
      file.setDescription("Description for File " + (i + 1));
      file.setState(STATES[i % STATES.length]);
      file.setSize(RANDOM.nextInt(1000) + 500);
      file.setNumViews(RANDOM.nextInt(1000));
      file.setNumDownloads(RANDOM.nextInt(500));
      file.setProducer(producers.get(i % producers.size()));
      file.setKeyWords(getTresPalabrasAleatorias());
      file.setCreationDate(new Date());
      file.setValidator(validators.get(0));
      files.add(file);
      fr.save(file);
    }
    return files;
  }

  public List<Producer> getProducers() {
    return producers;
  }

  public List<Validator> getValidators() {
    return validators;
  }

  public List<File> getFiles() {
    return files;
  }
}
