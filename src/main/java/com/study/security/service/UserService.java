package com.study.security.service;

import com.study.security.dto.token.TokenDTO;
import com.study.security.dto.user.UserRequestDTO;
import com.study.security.model.User;
import com.study.security.repository.UserRepository;
import com.study.security.security.jwt.TokenJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenJwtService jwtService;

    @Transactional
    public void create(UserRequestDTO dto) {
        final String passwordEncoded = passwordEncoder.encode(dto.getPassword());
        final User user = User
                .builder()
                .login(dto.getLogin())
                .password(passwordEncoded)
                .admin(true)
                .build();
        userRepository.save(user);
    }

    public TokenDTO login(UserRequestDTO dto) {
        try {
             final UserDetails userDetails = authenticate(dto);
            return TokenDTO.builder()
                    .login(userDetails.getUsername())
                    .token(jwtService.buildToken(dto))
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    public UserDetails authenticate(UserRequestDTO dto) {
        final UserDetails userDetails = loadUserByUsername(dto.getLogin());
        final boolean isPasswordCorrect = passwordEncoder.matches(dto.getPassword(), userDetails.getPassword());
        if (!isPasswordCorrect) {
            throw new UsernameNotFoundException("Wrong password");
        }
        return userDetails;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String[] roles = user.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}
