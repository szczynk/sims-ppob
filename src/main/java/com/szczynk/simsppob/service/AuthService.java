package com.szczynk.simsppob.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szczynk.simsppob.model.Balance;
import com.szczynk.simsppob.model.User;
import com.szczynk.simsppob.model.request.LoginRequest;
import com.szczynk.simsppob.model.request.RegisterRequest;
import com.szczynk.simsppob.model.response.LoginResponse;
import com.szczynk.simsppob.repository.BalanceRepository;
import com.szczynk.simsppob.repository.UserRepository;
import com.szczynk.simsppob.security.jwt.JwtUtils;

@Service
public class AuthService {

    private UserRepository userRepository;
    private BalanceRepository balanceRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            BalanceRepository balanceRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.balanceRepository = balanceRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public void register(RegisterRequest request) {
        if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setProfileImage("https://api.dicebear.com/7.x/lorelei/svg?seed=" + request.getFirstName());

        this.userRepository.save(user);

        Balance balance = new Balance();
        balance.setBalanceAmount(0L);
        balance.setUserId(user.getId());

        this.balanceRepository.save(balance);
    }

    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtUtils.generateToken(userDetails);

        return LoginResponse
                .builder()
                .token(token)
                .build();
    }
}
