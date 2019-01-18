package com.json.services;

import com.json.db.entities.Document;
import com.json.db.repositories.DocumentRepository;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.json.utils.ObjectGenerator.convertResponseToObject;

@Service
public class DocumentService {

    private static final Logger logger = LogManager.getLogger(DocumentService.class);

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private OkHttpClient okHttpClient;

    public Iterable<Document> saveDocumentByLink(String link) throws IOException, JSONException {
        ResponseBody responseBody = getResponseByLink(link);

        if (responseBody != null) {
            List<Document> documents = convertResponseToObject(responseBody);

            if (documents.size() > 0) {
                return documentRepository.saveAll(documents);
            } else {
                logger.info("Documents not found");
                return null;
            }
        } else {
            logger.info("Resource was not loaded");
            return null;
        }
    }

    public List<Document> getDocuments() {
        return documentRepository.findAll();
    }

    private ResponseBody getResponseByLink(String link) throws IOException {
        Request request = new Request.Builder()
                .url(link)
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();

        if (response.code() == HttpStatus.OK.value()) {
            logger.info("Resource was loaded success");
            return response.body();
        }

        logger.warn("Returned status of http-request: " + response.code() + " by link: " + link);
        return null;
    }
}
