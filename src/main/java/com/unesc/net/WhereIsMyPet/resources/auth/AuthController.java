package com.unesc.net.WhereIsMyPet.resources.auth;

import com.google.common.base.Verify;
import com.unesc.net.WhereIsMyPet.auth.*;
import com.unesc.net.WhereIsMyPet.entity.user.Usuario;
import com.unesc.net.WhereIsMyPet.repository.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@RestController
@RequestMapping(value = "auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "login")
    public ResponseEntity<UserAccess> login(@RequestBody Login login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login.email(), login.senha());

        Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Usuario user = (Usuario) authenticate.getPrincipal();
        String token = tokenService.gerar(user);

        return ResponseEntity.ok(new UserAccess(new UsuarioRetorno(user.getId(), user.getNome(), user.getEmail()), token));
    }

    @PostMapping(value = "register")
    public ResponseEntity<Usuario> register(@RequestBody Register register) {
        if (this.userRepository.findUserByEmail(register.email()).isPresent()) {
            throw new ValidationException(String.format("Email %s já registrado", register.email()));
        }
        Usuario user = this.converUserFromRegister(register);
        user = this.userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    private Usuario converUserFromRegister(Register register) {
        return Usuario.builder()
                .senha(this.bCryptPasswordEncoder.encode(register.senha()))
                .email(register.email())
                .nome(register.nome())
                .build();
    }

    @PostMapping(value = "verify")
    public ResponseEntity<UserAccess> verify() {
        Usuario authentication = (Usuario) SecurityContextHolder.getContext().getAuthentication();;
        return this.login(new Login(authentication.getEmail(), authentication.getPassword()));
    }

}
