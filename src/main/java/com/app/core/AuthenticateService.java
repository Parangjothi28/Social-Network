package com.app.core;

import com.app.entities.User;

import java.util.Optional;

public class AuthenticateService {

    public static boolean isValidUser(String userEmailId, String password) {
        Optional<User> optionalUser = DataBaseService.getInstance().loadUserByEmailId(userEmailId);
        if(!optionalUser.isEmpty() && optionalUser.get().getPassword().equals(password)) {
            return true;
        }

        return false;
    }

    public static boolean userExist(String userEmailId) {
        Optional<User> optionalUser = DataBaseService.getInstance().loadUserByEmailId(userEmailId);
        if(!optionalUser.isEmpty()) {
            return true;
        }

        return false;
    }
}