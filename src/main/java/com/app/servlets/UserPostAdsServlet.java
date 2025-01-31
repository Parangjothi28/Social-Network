package com.app.servlets;

import com.app.core.DataBaseService;
import com.app.entities.Advertisements;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;

import java.util.Random;

@WebServlet("/postads")
@MultipartConfig
public class UserPostAdsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        System.out.println("Inside postads");
        System.out.println("user: "+session.getAttribute("user"));

        if(session != null && session.getAttribute("user") != null) {
            Advertisements advertisements = new Advertisements();
            advertisements.setId(new Random().nextInt(100));
            advertisements.setViewSpot(0);
            advertisements.setViews(0);
            advertisements.setSubscriptionEndDate(request.getParameter("duration"));

            Part imagePart = request.getPart("image");
            InputStream imageInputStream = imagePart.getInputStream();
            byte[] imageData = new byte[imageInputStream.available()];
            imageInputStream.read(imageData);

            advertisements.setContent(imageData);
            DataBaseService.getInstance().saveAds(advertisements);

            response.getWriter().write("{\"adstatus\": true}");
        }
        else {
            response.getWriter().write("{\"adstatus\": false}");
        }
    }
}