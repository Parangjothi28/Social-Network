package com.app.servlets;

import com.app.Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        System.out.println("Inside login servlet");

        String htmlContent = Util.readAllLinesAsString("./webapps/social-network/html/login.html");

        // Send the HTML content to the client
        response.getWriter().write(htmlContent);
    }
}
