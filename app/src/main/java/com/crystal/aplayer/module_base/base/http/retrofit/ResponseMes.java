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

    public ErrorContent getError() {
        return null;
    }

    public boolean hasError() {
        return false;
    }

    public static class ErrorContent {

    }

}
