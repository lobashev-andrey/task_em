package com.example.task_em.security;

import com.example.task_em.entity.RefreshToken;
import com.example.task_em.entity.User;
import com.example.task_em.exception.RefreshTokenException;
import com.example.task_em.repository.UserRepository;
import com.example.task_em.security.jwt.JwtUtils;
import com.example.task_em.service.RefreshTokenService;
import com.example.task_em.security.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthResponse authenticateUser(LoginRequest loginRequest) {     // отвечает за логин пользователя и занесение его в контекст Spring Security
        // а также за отдачу access- и refresh-токена клиенту
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(  // выполняем аутентификацию
                                loginRequest.getUsername(),                                                                          // проверяя входные данные на имеющиеся в системе
                                loginRequest.getPassword()
                        ));

        System.out.println("************* authenticateUser " + loginRequest.getUsername() + " " + loginRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);   // если успешно, то результат заносится в SCH, который управляет данными текущего потока

        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();  // получаем детали идентифицированного пользователя

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());  // создаем новый рефреш-токен

        return AuthResponse.builder()                            // и возвращаем респонс с информацией о об аутентификации и данных пользователя
                .id(userDetails.getId())
                .token(jwtUtils.generateJwtToken(userDetails))   // в том числе токен доступа  и
                .refreshToken(refreshToken.getToken())           //             токен обновления
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }

    public void register(CreateUserRequest createUserRequest) {    // сохранение пользователя в БД
        var user = User.builder()
                .username(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))   // !  пароль кодируем
                .roles(createUserRequest.getRoles())            // почему-то он в отдельную строку вынес  ??????????????
                .build();
        userRepository.save(user);
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByRefreshToken(requestRefreshToken)       // ищем токен в redis
                .map(refreshTokenService::checkRefreshToken)                     // проверяем валидность
                .map(RefreshToken::getUser_id)                                    // ищем пользователя и генерируем для него новые:
                .map(userId ->
                        {
                            User tokenOwner = userRepository.findById(userId).orElseThrow(() ->
                                    new RefreshTokenException("Exception trying to get token for userId: " + userId));
                            String token = jwtUtils.generateTokenFromUsername(tokenOwner.getUsername());                     // access-токен

                            return new RefreshTokenResponse(token,
                                    refreshTokenService.createRefreshToken(userId).getToken());                              // refresh-токен
                        }                                                                                                    // и возвращаем их ему
                )
                .orElseThrow(() -> new RefreshTokenException(requestRefreshToken, "Refresh token not found"));
    }

    public void logout() {                                                                                 // просто удаление рефреш-токена
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentPrincipal instanceof AppUserDetails userDetails) {
            Long userId = userDetails.getId();

            refreshTokenService.deleteByUserId(userId);
        }
    }
}

