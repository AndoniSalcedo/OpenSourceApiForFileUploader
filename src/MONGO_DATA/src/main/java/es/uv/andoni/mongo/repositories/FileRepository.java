package es.uv.andoni.mongo.repositories;

import es.uv.andoni.mongo.domain.File;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, Long> {}
