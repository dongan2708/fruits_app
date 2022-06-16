package com.android.appfruit.entity;

import com.android.appfruit.dto.LoginToken;

public class AccountResponse {
        private Object headers;
        private LoginToken body;
        private String statusCode;
        private int statusCodeValue;

        public AccountResponse() {
        }

        public AccountResponse(Object headers, LoginToken body, String statusCode, int statusCodeValue) {
            this.headers = headers;
            this.body = body;
            this.statusCode = statusCode;
            this.statusCodeValue = statusCodeValue;
        }

        public Object getHeaders() {
            return headers;
        }

        public void setHeaders(Object headers) {
            this.headers = headers;
        }

        public LoginToken getBody() {
            return body;
        }

        public void setBody(LoginToken body) {
            this.body = body;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public int getStatusCodeValue() {
            return statusCodeValue;
        }

        public void setStatusCodeValue(int statusCodeValue) {
            this.statusCodeValue = statusCodeValue;
        }
    }

