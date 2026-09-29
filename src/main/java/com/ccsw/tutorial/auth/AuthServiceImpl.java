package com.ccsw.tutorial.auth;

import com.ccsw.tutorial.auth.model.LoginDto;
import com.ccsw.tutorial.auth.model.LoginResponseDto;
import com.ccsw.tutorial.jwt.JwtService;
import com.ccsw.tutorial.user.UserService;
import com.ccsw.tutorial.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    /**
     * {@inheritDoc}
     */
    @Override
    public LoginResponseDto login(LoginDto dto) {

        String name = dto.getName();
        String password = dto.getPassword();

        User user = this.userService.get(name);

        if (user != null) {
            if (password.equals(user.getPassword())) {

                LoginResponseDto loginResponseDto = new LoginResponseDto();
                String token = jwtService.getToken(user.getName(), user.getRole());
                loginResponseDto.setToken(token);
                return loginResponseDto;
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos.");
    }
}
