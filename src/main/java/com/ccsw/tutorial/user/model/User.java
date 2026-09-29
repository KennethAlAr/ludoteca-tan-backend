package com.ccsw.tutorial.user.model;

import com.ccsw.tutorial.role.model.Role;
import jakarta.persistence.*;

/**
 * @author  ccsw
 *
 */
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

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

    /**
     * @return role
     */
    public Role getRole() {

        return this.role;
    }

    /**
     * @param role new value of {@link #getRole}.
     */
    public void setRole(Role role) {

        this.role = role;
    }
}
