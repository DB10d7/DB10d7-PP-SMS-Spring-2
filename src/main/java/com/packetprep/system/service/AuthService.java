package com.packetprep.system.service;

import com.packetprep.system.Model.*;
import com.packetprep.system.dto.*;
import com.packetprep.system.exception.*;
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

import java.io.IOException;
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
    private final StudentService studentService;
    private final DayRepository dayRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;


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
    public String signup(RegisterRequest registerRequest) throws IOException {
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
//            imageService.uplaodImage(user, registerRequest.getFile());
            String token = generateVerificationToken(user);
            MailRequest mailRequest = new MailRequest();
            mailRequest.setTo(registerRequest.getEmail());
            mailRequest.setName(registerRequest.getUsername());
            mailRequest.setToken(token);
            mailRequest.setSubject("Account Activation Email");
            mailService.sendEmail(mailRequest);
//            mailService.sendMail(new NotificationEmail("Please Activate your Account",
//                    user.getEmail(), "Thank you for signing up to Packet-Prep, " +
//                    "please click on the below url to activate your account : " +
//                    "http://localhost:4200/account-activation/" + token));
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
    public List<StudentResponse> showTrainers() {
        List<User> users = userRepository.findAll();
        List<User> trainers = new ArrayList<>();
        for(User trainer: users){
            if(trainer.getRole().getRoleName().equalsIgnoreCase("TRAINER")){
                trainers.add(trainer);
            }
        }
        return trainers.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
    @Transactional
    public List<StudentResponse> getDefaultRoleUsers() {
        List<User> users = userRepository.findAll();
        List<User> defaultUser = new ArrayList<>();
        for(User dUser: users){
            if((dUser.getRole().getRoleName().equalsIgnoreCase("DEFAULT")) && (dUser.isEnabled() == true)){
                defaultUser.add(dUser);
            }
        }
        return defaultUser.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
    @Transactional
    public List<StudentResponse> getUnverifiedUser() {
        List<User> users = userRepository.findAll();
        List<User> unVUser = new ArrayList<>();
        for(User dUser: users){
            if(dUser.isEnabled() == false){
                unVUser.add(dUser);
            }
        }
        return unVUser.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
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
        List<VerificationToken> vts = verificationTokenRepository.findByUser(user);
        if(vts.isEmpty() == false){
            for(VerificationToken vt: vts){
                verificationTokenRepository.delete(vt);
            }
        }
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
//        user.setPassword(registerRequest.getPassword());
        user.setCreated(Instant.now());
        user.setEnabled(true);
        userRepository.save(user);

    }
    public void updateProfile(RegisterRequest registerRequest) {
        User user = userRepository.findByUsername(getCurrentUser().getUsername())
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
//        user.setPassword(registerRequest.getPassword());
//        user.setCreated(Instant.now());
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
        List<DayResponse> days = studentService.getDaysByStudent(user.getUsername());
        if(days.isEmpty() == false){
            for(DayResponse dy: days){
                Day day = dayRepository.findByName(dy.getDayName())
                        .orElseThrow(() -> new DayNotFoundException(dy.getDayName()));
                day.getUser().remove(user);
            }
        }
        List<VerificationToken> vts = verificationTokenRepository.findByUser(user);
        if(vts.isEmpty() == false){
            for(VerificationToken vt: vts){
                verificationTokenRepository.delete(vt);
            }
        }
        List<PasswordResetToken> prts = passwordResetTokenRepository.findByUser(user);
        if(prts.isEmpty() == false){
            for(PasswordResetToken prt: prts){
                passwordResetTokenRepository.delete(prt);
            }
        }
        try{
            Image img = imageRepository.findByUser(user).orElseThrow(() -> new ImageNotFoundException("Image not found with user name - " + user.getName()));
            imageRepository.delete(img);
            userRepository.deleteById(id);
        }catch(Exception ImageNotFoundException){
            userRepository.deleteById(id);
        }



    }
    public String verifyUserForPasswordReset(ForgotPasswordRequest forgotPasswordRequest){
        try{
            Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(forgotPasswordRequest.getToken());
            fetchUserAndResetPassword(passwordResetToken.orElseThrow(() -> new SpringPPSystemException("Invalid Token")), forgotPasswordRequest.getPassword());
            return "Reset-Password Successful";
        }catch(Exception SpringPPSystemException){
            return "Reset-Password Un-Successful! Please provide valid token to reset your password";
        }

    }
    private void fetchUserAndResetPassword(PasswordResetToken passwordResetToken, String password) {
        String username = passwordResetToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with name - " + username));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        List<PasswordResetToken> prts = passwordResetTokenRepository.findByUser(user);
        if(prts.isEmpty() == false){
            for(PasswordResetToken prt: prts){
                passwordResetTokenRepository.delete(prt);
            }
        }
    }
    public String forgotPassword(String username){
        try{
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
            String token = generatePasswordResetToken(user);
//            mailService.sendMail(new NotificationEmail("Please Reset your Password",
//                    user.getEmail(), "Thank you for signing up to Packet-Prep, " +
//                    "please click on the below url to reset your password : " +
//                    "http://localhost:4200/reset-Password/" + token));
            return "Reset-Password link successfully sent to your registered email address";
        }catch(Exception UsernameNotFoundException){
            return "UserName not registered with US. Please verify the username";
        }
    }
//    public String forgotPassword(String username){
//        try{
//            User user = userRepository.findByUsername(username)
//                    .orElseThrow(() -> new UsernameNotFoundException(username));
//            String token = generatePasswordResetToken(user);
//            mailService.sendMail(new NotificationEmail("Please Reset your Password",
//                    user.getEmail(), token));
//            return "Reset-Password link successfully sent to your registered email address";
//        }catch(Exception UsernameNotFoundException){
//            return "UserName not registered with US. Please verify the username";
//        }
//    }
}
