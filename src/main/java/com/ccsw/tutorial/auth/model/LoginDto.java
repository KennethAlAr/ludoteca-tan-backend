package com.ccsw.tutorial.auth.model;

/**
 * @author ccsw
 *
 */
public class LoginDto {

    private String name;

    private String password;

    /**
     * @return name
     */
    public String getName() {

        return this.name;
    }

    /**
     * @param name new value of {@link #getName}.
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * @return password
     */
    public String getPassword() {

        return this.password;
    }

    /**
     * @param password new value of {@link #getPassword}.
     */
    public void setPassword(String password) {

        this.password = password;
    }
}
