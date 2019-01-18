package com.json.utils;

import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import okhttp3.*;

import java.io.FileReader;
import java.io.IOException;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

public class TestObjectAndVariableGenerator {

    public static String linkOnDocuments = "https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/23567e24f52746ef92c470be6059d193/documents";

    public static Response generateResponseForRequest() throws IOException, ParseException {
        JSONParser parser = new JSONParser(DEFAULT_PERMISSIVE_MODE);
        Object obj = parser.parse(new FileReader("src/test/resources/document.json"));

        return new Response.Builder()
                .request(new Request.Builder().url(linkOnDocuments).build())
                .protocol(Protocol.get("http/1.1"))
                .message("OK")
                .code(200)
                .body(generateResponseBody(obj))
                .build();
    }

    public static Response generateResponseWithNotFoundStatus() throws IOException {
        return new Response.Builder()
                .request(new Request.Builder().url(linkOnDocuments).build())
                .protocol(Protocol.get("http/1.1"))
                .message("Not found")
                .code(404)
                .body(null)
                .build();

    }

    private static ResponseBody generateResponseBody(Object obj) {
        return ResponseBody.create(MediaType.get("application/json"), obj.toString());
    }
}
