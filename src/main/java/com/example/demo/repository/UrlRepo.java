package com.example.demo.repository;

import com.example.demo.entities.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepo
        extends MongoRepository<UrlEntity, String> {
      
    Optional<UrlEntity> findByNewUrl(String newUrl);

    Optional<UrlEntity> findByBaseUrl(String baseUrl);


    boolean existsByNewUrl(String newUrl);

    void deleteByNewUrl(String newUrl);
}
