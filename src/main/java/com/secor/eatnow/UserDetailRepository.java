package com.secor.eatnow;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserDetailRepository extends MongoRepository<Userdetail, String> {
}
