package org.example;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Util {

    public static void sendFile(HttpExchange exchange, String filePath, String contentType) throws IOException {
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, fileBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(fileBytes);
            os.close();
        } catch (IOException e) {
            exchange.sendResponseHeaders(404, 0); // Handle 404 if the file is not found
            exchange.getResponseBody().close();
        }
    }
}
