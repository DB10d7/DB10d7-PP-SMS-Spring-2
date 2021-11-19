package com.packetprep.system.service;

import com.packetprep.system.Model.*;
import com.packetprep.system.dto.*;
import com.packetprep.system.exception.BatchNotFoundException;
import com.packetprep.system.exception.RoleNotFoundException;
import com.packetprep.system.exception.SpringPPSystemException;
import com.packetprep.system.exception.StudentNotFoundException;
import com.packetprep.system.mapper.StudentMapper;
import com.packetprep.system.repository.*;
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
import java.util.ArrayList;
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
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final RoleRepository roleRepository;
    private final BatchRepository batchRepository;
    private final StudentMapper studentMapper;
    private final MailService mailService;


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
      /*  mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token)); */
    }
    public String signup(RegisterRequest registerRequest) {
        User user = new User();
        try{
            User prevUser = userRepository.findByUsername(registerRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException(registerRequest.getUsername()));
            return "UserName Already Taken";
        }catch (Exception UsernameNotFoundException) {
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
            user.setEnabled(false);
            userRepository.save(user);
            String token = generateVerificationToken(user);
            mailService.sendMail(new NotificationEmail("Please Activate your Account",
                    user.getEmail(), "Thank you for signing up to Packet-Prep, " +
                    "please click on the below url to activate your account : " +
                    "http://localhost:4200/account-activation/" + token));
            return "User Registered Successfully";
        }
    }
    @Transactional
    public List<StudentResponse> showAllUser() {
        List<User> students = userRepository.findAll();
        return students.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
    @Transactional
    public List<StudentResponse> getAllEmployees() {
        List<User> users = userRepository.findAll();
        List<User> employees = new ArrayList<>();
        for(User employee: users){
            if((employee.getBatch().getName().equalsIgnoreCase("PacketPrep-Team")) && ((!employee.getRole().getRoleName().equalsIgnoreCase("STUDENT") && (!employee.getRole().getRoleName().equalsIgnoreCase("DEFAULT")) ))){
                employees.add(employee);
            }
        }
        return employees.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
    @Transactional
    public List<StudentResponse> showOfficeEmployee() {
        List<User> users = userRepository.findAll();
        List<User> officeEmployee = new ArrayList<>();
        for(User employee: users){
            if(employee.getRole().getRoleName().equalsIgnoreCase("DEFAULT")){
                officeEmployee.add(employee);
            }
        }
        return officeEmployee.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
    @Transactional
    public List<StudentResponse> getDefaultRoleUsers() {
        List<User> users = userRepository.findAll();
        List<User> officeEmployee = new ArrayList<>();
        for(User employee: users){
            if(employee.getRole().getRoleName().equalsIgnoreCase("DEFAULT")){
                officeEmployee.add(employee);
            }
        }
        return officeEmployee.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
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
    public StudentResponse getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        User user= userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
        return studentMapper.mapFromStudentToDto(user);
    }
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringPPSystemException("Invalid Token")));
    }
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));
       // System.out.println(user);
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
    private String generatePasswordResetToken(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);

        passwordResetTokenRepository.save(passwordResetToken);
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
    public void verifyUserForPasswordReset(ForgotPasswordRequest forgotPasswordRequest){
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(forgotPasswordRequest.getToken());
        fetchUserAndResetPassword(passwordResetToken.orElseThrow(() -> new SpringPPSystemException("Invalid Token")), forgotPasswordRequest.getPassword());
    }
    private void fetchUserAndResetPassword(PasswordResetToken passwordResetToken, String password) {
        String username = passwordResetToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));
        // System.out.println(user);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
    public void forgotPassword(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        String token = generatePasswordResetToken(user);
        mailService.sendMail(new NotificationEmail("Please Reset your Password",
                user.getEmail(), "Thank you for signing up to Packet-Prep, " +
                "please click on the below url to reset your password : " +
                "http://localhost:4200/reset-Password/" + token));
    }
}
