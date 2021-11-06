package com.packetprep.system.service;

import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Role;
import com.packetprep.system.Model.User;
import com.packetprep.system.Model.VerificationToken;
import com.packetprep.system.dto.*;
import com.packetprep.system.exception.BatchNotFoundException;
import com.packetprep.system.exception.RoleNotFoundException;
import com.packetprep.system.exception.SpringPPSystemException;
import com.packetprep.system.exception.StudentNotFoundException;
import com.packetprep.system.mapper.StudentMapper;
import com.packetprep.system.repository.BatchRepository;
import com.packetprep.system.repository.RoleRepository;
import com.packetprep.system.repository.UserRepository;
import com.packetprep.system.repository.VerificationTokenRepository;
import com.packetprep.system.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final RoleRepository roleRepository;
    private final BatchRepository batchRepository;
    private final StudentMapper studentMapper;


    public void signupAdmin(RegisterRequest registerRequest) {
        User user = new User();
        Role role = roleRepository.findByRoleName(registerRequest.getRole())
                .orElseThrow(() -> new RoleNotFoundException(registerRequest.getRole()));
        user.setUsername(registerRequest.getUsername());
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(true);
        userRepository.save(user);

        // String token = generateVerificationToken(user);
      /*  iyvyyiyvivyiyiyivyliyliylyumailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));j9uh7j;9yujt6hmt,hiu.kuk */
    }
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        Role role = roleRepository.findByRoleName(registerRequest.getRole())
                .orElseThrow(() -> new RoleNotFoundException(registerRequest.getRole()));
        Batch batch = batchRepository.findByName(registerRequest.getBatch())
                .orElseThrow(() -> new BatchNotFoundException(registerRequest.getBatch()));
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setRole(role);
        user.setBatch(batch);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(true);
        userRepository.save(user);

       // String token = generateVerificationToken(user);
      /*  iyvyyiyvivyiyiyivyliyliylyumailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));j9uh7j;9yujt6hmt,hiu.kuk */
    }
    @Transactional
    public List<StudentResponse> showAllUser() {
        List<User> students = userRepository.findAll();
        return students.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(loginRequest.getUsername()));
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .role(user.getRole().getRoleName())
                .build();
    }
    @Transactional
    public StudentResponse getSingleUser(String Username) {
        User user = userRepository.findByUsername(Username).orElseThrow(() -> new StudentNotFoundException(Username));
        return studentMapper.mapFromStudentToDto(user);
    }
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringPPSystemException("Invalid Token")));
    }
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringPPSystemException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }
    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public void update(RegisterRequest registerRequest) {
        User user = userRepository.findByUsername(registerRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(registerRequest.getUsername()));
        Role role = roleRepository.findByRoleName(registerRequest.getRole())
                .orElseThrow(() -> new RoleNotFoundException(registerRequest.getRole()));
        Batch batch = batchRepository.findByName(registerRequest.getBatch())
                .orElseThrow(() -> new BatchNotFoundException(registerRequest.getBatch()));
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setRole(role);
        user.setBatch(batch);
       // user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setPassword(registerRequest.getPassword());
        user.setCreated(Instant.now());
        user.setEnabled(true);
        userRepository.save(user);

    }
    public void delete( String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        userRepository.deleteById(user.getUserId());
    }
    public void deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found - "));
        userRepository.deleteById(id);
    }
}
