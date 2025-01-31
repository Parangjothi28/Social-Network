package com.app.servlets;

import com.app.core.DataBaseService;
import com.app.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/profile")
@MultipartConfig
public class ProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        System.out.println("Inside profile servlet");
        // System.out.println("user: "+session.getAttribute("user"));

        if(session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            String name = request.getParameter("name");
            String full_name = request.getParameter("full_name");
            String dob = request.getParameter("dob");
            DataBaseService.getInstance().updateUser(user.getId(),name,full_name,dob);
            user.setFullName(full_name);
            user.setUserName(name);
            user.setDateOfBirth(dob);
            response.getWriter().write("{\"profilestatus\": true}");
        }
        else if(request.getParameter("userid")!=null&&request.getParameter("useremail")!=null){
            String name = request.getParameter("name");
            String full_name = request.getParameter("full_name");
            String dob = request.getParameter("dob");
            DataBaseService.getInstance().updateUser(Integer.parseInt(request.getParameter("userid")),name,full_name,dob);
            String userEmail = request.getParameter("useremail");
            Optional<User> currentUser= DataBaseService.getInstance().loadUserByEmailId(userEmail);
            User user = currentUser.get();
            user.setFullName(full_name);
            user.setUserName(name);
            user.setDateOfBirth(dob);
            response.getWriter().write("{\"profilestatus\": true}");
        }
        else {
            response.getWriter().write("{\"profilestatus\": false}");
        }
    }
}