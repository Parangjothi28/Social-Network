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
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

@WebServlet("/post")
public class CreatePostServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Inside post servlet");
        HttpSession session = request.getSession(false);
        System.out.println("Inside post servlet");
        if(session != null && session.getAttribute("user")!= null)  {
            User user = (User) session.getAttribute("user");
            JSONObject responseData = new JSONObject();
            responseData.put("authenticated", true);
            responseData.put("user", new JSONObject(user));
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            Post post = new Post();
            post.setId(new Random().nextInt());
            post.setDisLikes(0);
            post.setLikes(0);
            post.setDateCreated(java.time.Instant.now().toString());
            post.setBlocked(false);
            post.setTitle(title);
            post.setContent(content);
            post.setUserId(user.getId());
            boolean success = DataBaseService.getInstance().savePost(post);
            if(success)
                response.sendRedirect("/social-network/html/home.html");
            else
                response.sendRedirect("/social-network/html/my-page.html");
        }
        else if(request.getParameter("userid")!=null){
            System.out.println("In here");
            JSONObject responseData = new JSONObject();
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            System.out.println(title+" "+content);
            Post post = new Post();
            post.setId(new Random().nextInt());
            post.setDisLikes(0);
            post.setLikes(0);
            post.setDateCreated(java.time.Instant.now().toString());
            post.setBlocked(false);
            post.setTitle(title);
            post.setContent(content);
            post.setUserId(Integer.parseInt(request.getParameter("userid")));
            boolean success = DataBaseService.getInstance().savePost(post);
            if(success){
                responseData.put("authenticated", true);
                response.getWriter().println(responseData);}
            else{
                responseData.put("authenticated", false);
                response.getWriter().println(responseData);}
        }
        else {
            JSONObject responseData = new JSONObject();
            responseData.put("authenticated", false);
            response.getWriter().println(responseData);
        }
    }
}