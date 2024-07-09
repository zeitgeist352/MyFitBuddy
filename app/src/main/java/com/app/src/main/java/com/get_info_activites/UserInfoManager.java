package com.get_info_activites;

public class UserInfoManager {
    private static UserInfoManager instance;
    private UserInfoHolder userInfo;

    private UserInfoManager() { }

    public static synchronized UserInfoManager getInstance() {
        if (instance == null) {
            instance = new UserInfoManager();
        }
        return instance;
    }

    public UserInfoHolder getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoHolder userInfo) {
        this.userInfo = userInfo;
    }
}
