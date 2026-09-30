package com.ccsw.tutorial.user;

import com.ccsw.tutorial.user.model.User;

public interface UserService {

    /**
     * Recupera un {@link User} a partir de su nombre
     *
     * @param name Nombre de la entidad
     * @return {@link User}
     */
    User get(String name);
}
