package com.ccsw.tutorial.auth;

import com.ccsw.tutorial.auth.model.LoginDto;
import com.ccsw.tutorial.auth.model.LoginResponseDto;

public interface AuthService {

    /**
     * Crea un {@link LoginResponseDto} a partir de un LoginDto
     *
     * @param dto LoginDto de la petición
     * @return {@link LoginResponseDto}
     */
    LoginResponseDto login(LoginDto dto);
}
