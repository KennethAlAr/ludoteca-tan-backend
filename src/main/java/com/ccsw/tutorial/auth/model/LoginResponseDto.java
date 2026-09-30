package com.ccsw.tutorial.auth.model;

/**
 * @author ccsw
 *
 */
public class LoginResponseDto {

    private String token;

    /**
     * @return token
     */
    public String getToken() {

        return this.token;
    }

    /**
     * @param token new value of {@link #getToken}.
     */
    public void setToken(String token) {

        this.token = token;
    }
}
