package com.json.db.repositories;

import com.json.db.entities.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document, String> {

    List<Document> findAll();

}
