package com.secor.eatnow;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserCredentialRepository extends MongoRepository<UserCredential, String> {
}
