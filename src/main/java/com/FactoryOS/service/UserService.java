package com.FactoryOS.service;


import com.FactoryOS.common.exception.ApiException;
import com.FactoryOS.dto.RegisterRequest;
import com.FactoryOS.model.Organization;
import com.FactoryOS.model.User;
import com.FactoryOS.model.enums.Role;
import com.FactoryOS.model.enums.UserStatus;
import com.FactoryOS.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createOwner(RegisterRequest request, Organization organization) {
        User user = User.builder()
                .organization(organization)
                .name(request.getOwnerName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.OWNER)
                .status(UserStatus.ACTIVE)
                .build();

        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found", 404));
    }
}
