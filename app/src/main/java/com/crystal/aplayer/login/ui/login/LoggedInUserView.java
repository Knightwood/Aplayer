package com.crystal.aplayer.login.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 * UI类暴露身份验证的用户的细节。
 */
class LoggedInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}
