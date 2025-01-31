package com.app.servlets;

import com.app.core.DataBaseService;
import com.app.entities.Post;
import com.app.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

@WebServlet("/getuserpost")
public class UserPostServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        System.out.println("Inside home servlet");
        if(session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            JSONObject responseData = new JSONObject();
            responseData.put("authenticated", true);
            responseData.put("user", new JSONObject(user));
            
            List<Post> Posts = new ArrayList<>();
            Posts = DataBaseService.getInstance().loadPost(user.getId());
            Collections.reverse(Posts);
            responseData.put("posts",Posts);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(responseData);   
        }
        else {
            JSONObject responseData = new JSONObject();
            responseData.put("authenticated", false);
            response.getWriter().println(responseData);
        }
    }
}