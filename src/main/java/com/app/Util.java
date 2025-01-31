package com.app;

import com.sun.net.httpserver.HttpExchange;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Util {

    public static String readAllLinesAsString(String filePath) throws IOException {
        // Read all lines from the file into a List<String>
        var lines = Files.readAllLines(Paths.get(filePath));

        // Join the lines into a single string, separated by newline characters
        return String.join(System.lineSeparator(), lines);
    }
}
