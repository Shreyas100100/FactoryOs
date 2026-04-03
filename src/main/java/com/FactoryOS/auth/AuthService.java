package com.FactoryOS.auth;

import com.FactoryOS.common.exception.ApiException;
import com.FactoryOS.dto.AuthResponse;
import com.FactoryOS.dto.LoginRequest;
import com.FactoryOS.dto.RegisterRequest;
import com.FactoryOS.model.Organization;
import com.FactoryOS.model.User;
import com.FactoryOS.model.enums.UserStatus;
import com.FactoryOS.service.OrganizationService;
import com.FactoryOS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final OrganizationService organizationService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        if (organizationService.existsByGstNumber(request.getGstNumber())) {
            throw new ApiException("Organization with this GST number already exists", 400);
        }
        if (userService.existsByEmail(request.getEmail())) {
            throw new ApiException("User with this email already exists", 400);
        }
        Organization org = organizationService.create(request);
        User user = userService.createOwner(request, org);
        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .orgId(org.getId())
                .role(user.getRole().name())
                .build();
    }

    public AuthResponse login(LoginRequest request){
        User user  = userService.findByEmail(request.getEmail());

        if(user.getStatus() != UserStatus.ACTIVE){
            throw new ApiException("Account is not Active !",409);
        }
        if(!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
            throw new ApiException("Invalid Password !",401);
        }

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .orgId(user.getOrganization().getId())
                .role(user.getRole().name())
                .build();



    }

}
