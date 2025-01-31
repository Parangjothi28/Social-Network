package org.example;

import org.example.entities.User;
import org.example.services.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {

    private static Application instance;   // Singleton pattern

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    private User currentUser;

    private PricingService pricingService;

    public PricingService getPricingService() {
        return pricingService;
    }

    public void setPricingService(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public ProfileUpdateService getProfileUpdateService() {
        return profileUpdateService;
    }

    public void setProfileUpdateService(ProfileUpdateService profileUpdateService) {
        this.profileUpdateService = profileUpdateService;
    }

    private ProfileUpdateService profileUpdateService;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    private LoginService loginService;

    private PostService postService;

    public PostService getPostService() {
        return postService;
    }

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    private Application() {
        loginService = new LoginService();
        postService = new PostService();
        pricingService = new PricingService();
        profileUpdateService = new ProfileUpdateService();
    }
    public static void main(String[] args) {
        Application.getInstance().getLoginService().setVisible(true);
    }
}
