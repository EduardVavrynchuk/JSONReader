package com.json.services;

import com.google.common.collect.Iterators;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static com.json.utils.TestObjectAndVariableGenerator.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class DocumentServiceTest {

    @Autowired
    private DocumentService documentService;

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
    public void saveDocumentByLink() throws IOException, JSONException {
        Iterable<Document> documents = documentService.saveDocumentByLink(linkOnDocuments);

        assertNotNull(documents);
    }

    @Test
    public void saveDocumentByLinkWithNotFoundResponseForRequest() throws IOException, JSONException {
        doReturn(generateResponseWithNotFoundStatus()).when(call).execute();
        Iterable<Document> documents = documentService.saveDocumentByLink(linkOnDocuments);

        assertNull(documents);
    }

    @Test
    public void getDocuments() throws IOException, JSONException {
        Iterable<Document> documents = documentService.saveDocumentByLink(linkOnDocuments);
        assertNotNull(documents);

        int amountAddedDocuments = Iterators.size(documents.iterator());

        List<Document> savedDocuments = documentService.getDocuments();
        assertNotNull(savedDocuments);

        assertEquals(amountAddedDocuments, savedDocuments.size());
    }
}