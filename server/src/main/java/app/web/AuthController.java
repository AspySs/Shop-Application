package app.web;

import app.entity.User;
import app.repository.UserRepository;
import app.security.jwt.JwtTokenProvider;
import app.web.requests.AuthRequest;
import app.web.requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> signIn(@RequestBody AuthRequest request) {
        String login = request.getLogin();
        String token = jwtTokenProvider.createToken(login, userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by login"))
                .getRoles());

        Map<Object, Object> model = new HashMap<>();
        model.put("login", login);
        model.put("token", token);

        return ResponseEntity.ok(model);
    }
    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterRequest request) {
        userRepository.save(new User(request.getLogin(), request.getPassword(), request.getRoles()));
    }

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
