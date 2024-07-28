package com.get_info_activites;

import java.util.ArrayList;

public class UserInfoManager {
    private static UserInfoManager instance;
    private ArrayList<UserInfoHolder> users;
    private UserInfoHolder userInfo;

    private UserInfoManager() {
        users = new ArrayList<>();
     }

    public static synchronized UserInfoManager getInstance() {
        if (instance == null) {
            instance = new UserInfoManager();
        }
        return instance;
    }

    public void setUserInfo(UserInfoHolder userInfo) {
        this.userInfo = userInfo;
        users.add(userInfo); //adds the new user
    }

    public UserInfoHolder getUserInfoByEmail(String email) {
        for (UserInfoHolder user : users) {
            
            if (user.getEmail().equals(email)) { //checks user's email
                return user;
            }
        }
        return null; //if user does not exist returns null
    }
    
}