package com.fundo.repository;

import com.fundo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String>, QuerydslPredicateExecutor<User> {
    List<User> findByName(String name);
}
