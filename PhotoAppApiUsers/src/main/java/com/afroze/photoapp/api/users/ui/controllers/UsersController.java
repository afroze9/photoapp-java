package com.afroze.photoapp.api.users.ui.controllers;

import com.afroze.photoapp.api.users.security.JwtUtil;
import com.afroze.photoapp.api.users.service.UsersService;
import com.afroze.photoapp.api.users.shared.UserDto;
import com.afroze.photoapp.api.users.ui.model.AuthenticationRequestModel;
import com.afroze.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.afroze.photoapp.api.users.ui.model.CreateUserResponseModel;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final Environment env;
    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UsersController(Environment env, UsersService usersService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.env = env;
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/status/check")
    public String status() {
        return "Working on port: " + env.getProperty("local.server.port");
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody @Valid CreateUserRequestModel model) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(model, UserDto.class);
        usersService.createUser(userDto);

        CreateUserResponseModel responseModel = mapper.map(userDto, CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authentication(@RequestBody AuthenticationRequestModel model) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(model.getEmail(), model.getPassword()));
        final UserDetails user = usersService.loadUserByUsername(model.getEmail());

        if(user != null) {
            return ResponseEntity.ok(jwtUtil.generateToken(user));
        }

        return ResponseEntity.badRequest().body("Some error :(");
    }
}
