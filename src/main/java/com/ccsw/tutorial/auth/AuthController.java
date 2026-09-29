package com.ccsw.tutorial.auth;

import com.ccsw.tutorial.auth.model.LoginDto;
import com.ccsw.tutorial.auth.model.LoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ccsw
 *
 */
@Tag(name = "Auth")
@RequestMapping(value = "/auth")
@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    AuthService authService;

    @Operation(summary = "Login", description = "Method that logs in a user")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public LoginResponseDto login(@RequestBody LoginDto dto) {

        return this.authService.login(dto);
    }
}
