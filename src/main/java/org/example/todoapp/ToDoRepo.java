package org.example.todoapp;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ToDoRepo extends MongoRepository<Character, String> {
}
