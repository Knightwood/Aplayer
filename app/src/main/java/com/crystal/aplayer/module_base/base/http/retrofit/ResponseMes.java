package com.crystal.aplayer.module_base.base.http.retrofit;

/**
 * 创建者 kiylx
 * 创建时间 2020/10/7 15:53
 * packageName：com.crystal.aplayer.module_base.common.http
 * 描述：描述response的code和message
 */
public class ResponseMes {
    public int code;
    public String mes;

    public ResponseMes(int code, String mes) {
        this.code = code;
        this.mes = mes;
    }

    public String getError() {
        return mes;
    }

    public boolean hasError() {
        return code != 200;
    }

    public static class ErrorContent {
public static final int ConnectFailed=999;
    }

}
