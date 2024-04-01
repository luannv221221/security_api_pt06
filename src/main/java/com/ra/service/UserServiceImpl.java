package com.ra.service;

import com.ra.model.dto.request.UserLoginDTO;
import com.ra.model.dto.request.UserRequestDTO;
import com.ra.model.dto.response.UserLoginResponseDTO;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import com.ra.repository.RoleRepository;
import com.ra.repository.UserRepository;
import com.ra.security.jwt.JwtProvider;
import com.ra.security.user_principal.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public UserResponseDTO register(UserRequestDTO requestDTO) {
        Set<Role> roles = new HashSet<>();
        // lấy về role của USER
        Role role = roleRepository.findById(2L).orElse(null);
        roles.add(role);
        User user = User.builder()
                .userName(requestDTO.getUserName()).
                fullName(requestDTO.getFullName()).
                password(new BCryptPasswordEncoder().encode(requestDTO.getPassword())).
                status(requestDTO.getStatus()).
                roles(roles).
                build();

        User userEntity = userRepository.save(user);

        return UserResponseDTO.builder().fullName(userEntity.getFullName())
                .userName(userEntity.getUserName()).status(userEntity.getStatus())
                .build();
    }

    @Override
    public UserLoginResponseDTO login(UserLoginDTO userLoginDTO) {
        Authentication authentication;
        authentication = authenticationProvider.
                authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUserName(),userLoginDTO.getPassword()));
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return UserLoginResponseDTO.builder().
                token(jwtProvider.generateToken(userPrinciple)).userName(userPrinciple.getUsername()).fullName(userPrinciple.getUser().getFullName())
                .build();
    }
}
