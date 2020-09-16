package com.crystal.aplayer.login.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 * 登陆状态的标志，标记当前登录是否成功，以及记录登陆显示信息
 */
class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
