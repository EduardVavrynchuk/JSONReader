package com.json.webapp.controllers;

import com.json.db.entities.Document;
import com.json.services.DocumentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/document")
public class DocumentController {

    private static final Logger logger = LogManager.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    @PostMapping
    public ResponseEntity<?> saveDocumentInfoByLink(@RequestParam("link") String link) throws IOException, JSONException {
        if (link.isEmpty()) {
            logger.warn("Link is null");
            return new ResponseEntity<>("Link is empty, please, fill link on document", HttpStatus.BAD_REQUEST);
        }

        Iterable<Document> documents = documentService.saveDocumentByLink(link);
        if (documents != null)
            return new ResponseEntity<>(documents, HttpStatus.OK);

        return new ResponseEntity<>("Error during saving documents", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getSavedDocuments() {
        return new ResponseEntity<>(documentService.getDocuments(), HttpStatus.OK);
    }
}
