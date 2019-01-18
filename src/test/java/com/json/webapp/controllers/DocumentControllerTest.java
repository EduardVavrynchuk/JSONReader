package com.json.webapp.controllers;

import com.json.db.entities.Document;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static com.json.utils.TestObjectAndVariableGenerator.generateResponseForRequest;
import static com.json.utils.TestObjectAndVariableGenerator.generateResponseWithNotFoundStatus;
import static com.json.utils.TestObjectAndVariableGenerator.linkOnDocuments;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class DocumentControllerTest {

    @Autowired
    private DocumentController documentController;

    @MockBean
    private OkHttpClient okHttpClient;

    @MockBean
    private Call call;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        doReturn(generateResponseForRequest()).when(call).execute();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveDocumentInfoByLink() throws IOException, JSONException {
        ResponseEntity responseEntity = documentController.saveDocumentInfoByLink(linkOnDocuments);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void saveDocumentInfoByLinkWithNullableLink() throws IOException, JSONException {
        ResponseEntity responseEntity = documentController.saveDocumentInfoByLink("");

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void saveDocumentInfoByLinkWithNotFoundResponseForRequest() throws IOException, JSONException {
        doReturn(generateResponseWithNotFoundStatus()).when(call).execute();
        ResponseEntity responseEntity = documentController.saveDocumentInfoByLink(linkOnDocuments);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void getSavedDocuments() throws IOException, JSONException {
        ResponseEntity responseEntity = documentController.saveDocumentInfoByLink(linkOnDocuments);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        int amountSavedDocuments = ((List<Document>) responseEntity.getBody()).size();

        responseEntity = documentController.getSavedDocuments();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        int amountLoadedDocuments = ((List<Document>) responseEntity.getBody()).size();

        assertEquals(amountSavedDocuments, amountLoadedDocuments);
    }
}