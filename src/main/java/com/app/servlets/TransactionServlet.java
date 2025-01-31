package com.app.servlets;

import com.app.UserType;
import com.app.core.DataBaseService;
import com.app.entities.CreditCard;
import com.app.entities.Transaction;
import com.app.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Random;


import org.json.JSONObject;

@WebServlet("/transaction")
@MultipartConfig
public class TransactionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        System.out.println("Inside transaction servlet");
        if(session != null && session.getAttribute("user") != null) {
            // try {
                System.out.println("Her1");
            User user = (User) session.getAttribute("user");
            Enumeration<String> parameterNames = request.getParameterNames();
            System.out.println("Outside");

            System.out.println("Her1"+user.getUserName());
            JSONObject responseData = new JSONObject();
            responseData.put("authenticated", true);
            responseData.put("user", new JSONObject(user));
            String cre = request.getParameter("credit_card");
            System.out.println(cre);
            long creditcard = Long.parseLong(request.getParameter("credit_card"));
            System.out.println("Her1"+creditcard);
            int cvv = Integer.parseInt(request.getParameter("cvv"));
            int month = Integer.parseInt(request.getParameter("expiry_month"));
            int year = Integer.parseInt(request.getParameter("expiry_year"));
            String address = request.getParameter("address");
            double price = Double.parseDouble(request.getParameter("price"));
            CreditCard cc = new CreditCard();
            Transaction tran = new Transaction();
            int ccId = new Random().nextInt();
            cc.setId(ccId);
            // cc.setDateCreated(java.time.Instant.now().toString());
            cc.setUserId(user.getId());
            cc.setCvv(cvv);
            cc.setMonth(month);
            cc.setYear(year);
            cc.setCard(creditcard);
            boolean suc = DataBaseService.getInstance().saveCC(cc);
            System.out.println("Her1"+suc);
            tran.setAddress(address);
            tran.setId(new Random().nextInt());
            tran.setCard(ccId);
            tran.setAmount(price);
            tran.setUserId(user.getId());
            tran.setReceipt(address);
            // suc = DataBaseService.getInstance().saveTransaction(tran);
            System.out.println("Her1"+suc);
            if(price == 15){
                DataBaseService.getInstance().updateUserType(user.getId(), UserType.PREMIUM.toString());
                user.setUserType(UserType.PREMIUM.toString());
            }
            else if(price == 35){
                DataBaseService.getInstance().updateUserType(user.getId(), UserType.BRAND.toString());
                user.setUserType(UserType.BRAND.toString());
            }
            else{
                response.getWriter().write("{\"transtatus\": false}");
            }
            response.getWriter().write("{\"transtatus\": true}");
            
        }
        else if(request.getParameter("email")!=null){
            System.out.println("Her2");
            String userEmail = request.getParameter("email");
            Optional<User> currentUser= DataBaseService.getInstance().loadUserByEmailId(userEmail);
            User user = currentUser.get();
            System.out.println("Her1"+user.getUserName());
            JSONObject responseData = new JSONObject();
            responseData.put("authenticated", true);
            String cre = request.getParameter("credit_card");
            System.out.println(cre);
            long creditcard = Long.parseLong(request.getParameter("credit_card"));
            System.out.println("Her1"+creditcard);
            int cvv = Integer.parseInt(request.getParameter("cvv"));
            int month = Integer.parseInt(request.getParameter("expiry_month"));
            int year = Integer.parseInt(request.getParameter("expiry_year"));
            String address = request.getParameter("address");
            double price = Double.parseDouble(request.getParameter("price"));
            CreditCard cc = new CreditCard();
            Transaction tran = new Transaction();
            int ccId = new Random().nextInt();
            cc.setId(ccId);
            // cc.setDateCreated(java.time.Instant.now().toString());
            cc.setUserId(user.getId());
            cc.setCvv(cvv);
            cc.setMonth(month);
            cc.setYear(year);
            cc.setCard(creditcard);
            boolean suc = DataBaseService.getInstance().saveCC(cc);
            System.out.println("Her1"+suc);
            tran.setAddress(address);
            tran.setId(new Random().nextInt());
            tran.setCard(ccId);
            tran.setAmount(price);
            tran.setUserId(user.getId());
            tran.setReceipt(address);
            // suc = DataBaseService.getInstance().saveTransaction(tran);
            System.out.println("Her1"+suc);
            if(price == 15){
                DataBaseService.getInstance().updateUserType(user.getId(), UserType.PREMIUM.toString());
                user.setUserType(UserType.PREMIUM.toString());
                responseData.put("usertype",UserType.PREMIUM.toString());
            }
            else if(price == 35){
                DataBaseService.getInstance().updateUserType(user.getId(), UserType.BRAND.toString());
                user.setUserType(UserType.BRAND.toString());
                responseData.put("usertype",UserType.BRAND.toString());
            }
            else{
                responseData.put("transtatus",false);
                response.getWriter().println(responseData);
            }
            responseData.put("transtatus",true);
            response.getWriter().println(responseData);
        }
        else {
            response.getWriter().write("{\"transtatus\": false}");
        }
    }
}