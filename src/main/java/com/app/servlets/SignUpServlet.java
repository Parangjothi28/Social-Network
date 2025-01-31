package com.app.servlets;

import com.app.UserType;
import com.app.core.DataBaseService;
import com.app.entities.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.json.JSONObject;

import static com.app.core.AuthenticateService.isValidUser;
import static com.app.core.AuthenticateService.userExist;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set the response content type to HTML
        response.setContentType("text/html");

        String userName = request.getParameter("name");
        String userEmailId = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("userName "+userName);
        System.out.println("userEmailId "+userEmailId);
        System.out.println("password "+password);

        // Check if username and password are not empty
        if (userEmailId != null && !userEmailId.isEmpty() && password != null && !password.isEmpty() && !userExist(userEmailId)) {
            System.out.println("in signup if");
            User user = new User();
            user.setAddress(userEmailId);
            user.setDateOfBirth(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()));
            user.setEmailId(userEmailId);
            user.setFullName(userName);
            user.setPassword(password);
            user.setId(new Random().nextInt());
            user.setUserType(UserType.GENERAL.toString());

            DataBaseService.getInstance().saveUser(user);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            JSONObject responseData = new JSONObject();
            responseData.put("authenticated", true);
            responseData.put("user", new JSONObject(user));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(responseData);
            System.out.println("in signup if end");
        } else {
            System.out.println("in signup else");
            JSONObject responseData = new JSONObject();
            responseData.put("authenticated", false);
            response.getWriter().println(responseData);
        }
    }
}