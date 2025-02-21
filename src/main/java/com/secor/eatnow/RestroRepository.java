package com.secor.eatnow;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RestroRepository extends MongoRepository<Restro, String> {
}
