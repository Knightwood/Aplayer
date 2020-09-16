package com.crystal.aplayer.login.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 * 用户信息，用户的id，名称等东西
 */
public class LoggedInUser {

    private String userId;
    private String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
