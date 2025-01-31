package com.app.servlets;

import com.app.Util;
import com.app.core.DataBaseService;
import com.app.entities.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Optional;

import org.json.JSONObject;

import static com.app.core.AuthenticateService.isValidUser;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // response.setContentType("text/html");

        // Get the parameters (data) from the form
        String userEmailId = request.getParameter("signinEmail");
        String password = request.getParameter("signinPassword");

        System.out.println("in signin");
        System.out.println("email id: "+userEmailId);
        System.out.println("password: "+password);

        // Check if username and password are not empty
        if (userEmailId != null && !userEmailId.isEmpty() && password != null && !password.isEmpty() && isValidUser(userEmailId, password)) {
            Optional<User> currentUser = DataBaseService.getInstance().loadUserByEmailId(userEmailId);
            HttpSession session = request.getSession();
            User user = currentUser.get();
            session.setAttribute("user", user);
            JSONObject responseData = new JSONObject();
            responseData.put("authenticated", true);
            responseData.put("user", new JSONObject(user));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(responseData);
        } else {
            JSONObject responseData = new JSONObject();
            responseData.put("authenticated", false);
            response.getWriter().println(responseData);
        }
    }
}