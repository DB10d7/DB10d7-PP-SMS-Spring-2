package com.packetprep.system.service;

import com.packetprep.system.Model.*;
import com.packetprep.system.dto.*;
import com.packetprep.system.exception.*;
import com.packetprep.system.mapper.StudentMapper;
import com.packetprep.system.repository.*;
import com.packetprep.system.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
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
            user.setUname(registerRequest.getUname());
            user.setEmail(registerRequest.getEmail());
            user.setName(registerRequest.getName());
            user.setRole(role);
            user.setBatch(batch);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setCreated(Instant.now());
            user.setEnabled(false);

            /* Start Complete User Part */

            user.setSurname(registerRequest.getSurname());
            user.setStatus(registerRequest.getStatus());
            user.setTenthMarks(registerRequest.getTenthMarks());
            user.setTwelfthMarks(registerRequest.getTwelfthMarks());
            user.setGraduationMarks(registerRequest.getGraduationMarks());
            user.setYearOfPassing(registerRequest.getYearOfPassing());
            user.setState(registerRequest.getState());
            user.setCity(registerRequest.getCity());
            user.setNumber(registerRequest.getNumber());
            user.setCollegeName(registerRequest.getCollegeName());
            user.setUniversityName(registerRequest.getUniversity());
            user.setBirthDate(registerRequest.getBirthDate());
            user.setGender(registerRequest.getGender());
            user.setGraduation(registerRequest.getGraduation());
            user.setGraduationBranch(registerRequest.getGraduationBranch());

//            user.setFName(registerRequest.getFName());
//            user.setFNumber(registerRequest.getFNumber());
//            user.setAddress(registerRequest.getAddress());
//            user.setJDate(registerRequest.getJDate());
//            user.setCenter(registerRequest.getCenter());
//            user.setComment(registerRequest.getComment());
//            user.setUid(registerRequest.getUid());

            /* End of complete User Part */

            userRepository.save(user);

            String token = generateVerificationToken(user);
            MailRequest mailRequest = new MailRequest();
            mailRequest.setTo(registerRequest.getEmail());
            mailRequest.setName(registerRequest.getUsername());
            mailRequest.setToken(token);
            mailRequest.setSubject("Account Activation Email");
            mailService.sendEmailForActivation(mailRequest);
            return "User Registered Successfully";
        }
    }
    public String uploadDataFromApi(RegisterRequest registerRequest) throws IOException {
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
            user.setUname(registerRequest.getUname());
            user.setEmail(registerRequest.getEmail());
            user.setName(registerRequest.getName());
            user.setRole(role);
            user.setBatch(batch);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setCreated(Instant.now());
            user.setEnabled(true);

            /* Start Complete User Part */

            user.setSurname(registerRequest.getSurname());
            user.setStatus(registerRequest.getStatus());
            user.setTenthMarks(registerRequest.getTenthMarks());
            user.setTwelfthMarks(registerRequest.getTwelfthMarks());
            user.setGraduationMarks(registerRequest.getGraduationMarks());
            user.setYearOfPassing(registerRequest.getYearOfPassing());
            user.setState(registerRequest.getState());
            user.setCity(registerRequest.getCity());
            user.setNumber(registerRequest.getNumber());
            user.setCollegeName(registerRequest.getCollegeName());
            user.setUniversityName(registerRequest.getUniversity());
            user.setBirthDate(registerRequest.getBirthDate());
            user.setGender(registerRequest.getGender());
            user.setGraduation(registerRequest.getGraduation());
            user.setGraduationBranch(registerRequest.getGraduationBranch());

//            user.setFName(registerRequest.getFName());
//            user.setFNumber(registerRequest.getFNumber());
//            user.setAddress(registerRequest.getAddress());
//            user.setJDate(registerRequest.getJDate());
//            user.setCenter(registerRequest.getCenter());
//            user.setComment(registerRequest.getComment());
//            user.setUid(registerRequest.getUid());

            /* End of complete User Part */

            userRepository.save(user);
            return "User Registered Successfully";
//            String token = generateVerificationToken(user);
//            MailRequest mailRequest = new MailRequest();
//            mailRequest.setTo(registerRequest.getEmail());
//            mailRequest.setName(registerRequest.getUsername());
//            mailRequest.setToken(token);
//            mailRequest.setSubject("Account Activation Email");
//            mailService.sendEmailForActivation(mailRequest);
//            return "User Registered Successfully";
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
        System.out.print("hello");

        User user = userRepository.findByUsername(registerRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(registerRequest.getUsername()));
        Role role = roleRepository.findByRoleName(registerRequest.getRole())
                .orElseThrow(() -> new RoleNotFoundException(registerRequest.getRole()));
        Batch batch = batchRepository.findByName(registerRequest.getBatch())
                .orElseThrow(() -> new BatchNotFoundException(registerRequest.getBatch()));
        user.setUsername(registerRequest.getUsername());
        user.setUname(registerRequest.getUname());
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setRole(role);
        user.setBatch(batch);

        /* Start Complete User Part */

        user.setSurname(registerRequest.getSurname());
        user.setStatus(registerRequest.getStatus());
        user.setTenthMarks(registerRequest.getTenthMarks());
        user.setTwelfthMarks(registerRequest.getTwelfthMarks());
        user.setGraduationMarks(registerRequest.getGraduationMarks());
        user.setYearOfPassing(registerRequest.getYearOfPassing());
        user.setState(registerRequest.getState());
        user.setCity(registerRequest.getCity());
        user.setNumber(registerRequest.getNumber());
        user.setCollegeName(registerRequest.getCollegeName());
        user.setUniversityName(registerRequest.getUniversity());
        user.setBirthDate(registerRequest.getBirthDate());
        user.setGender(registerRequest.getGender());
        user.setGraduationBranch(registerRequest.getGraduationBranch());
        user.setGraduation(registerRequest.getGraduation());

//        user.setFName(registerRequest.getFName());
//        user.setFNumber(registerRequest.getFNumber());
//        user.setAddress(registerRequest.getAddress());
//        user.setJDate(registerRequest.getJDate());
//        user.setCenter(registerRequest.getCenter());
//        user.setComment(registerRequest.getComment());
//        user.setUid(registerRequest.getUid());

        /* End of complete User Part */

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
//    @EventListener(ApplicationReadyEvent.class)
    public String forgotPassword(String username){
        try{
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
            String token = generatePasswordResetToken(user);
            MailRequest mailRequest = new MailRequest();
            mailRequest.setTo(user.getEmail());
            mailRequest.setName(user.getUsername());
            mailRequest.setToken(token);
            mailRequest.setSubject("Reset Password Email");
            mailService.sendEmailForForgetPassword(mailRequest);
            return "Reset-Password link successfully sent to your registered email address";
        }catch(Exception UsernameNotFoundException){
            return "UserName not registered with US. Please verify the username";
        }
    }
    public String uploadExcel(String excelFile){

        JSONArray excel = new JSONArray(excelFile);

        for(int i=0;i<excel.length();i++){
           JSONObject jsonExcel= excel.getJSONObject(i);

            String email= (jsonExcel.has("email"))?jsonExcel.getString("email"):"";
            String username = jsonExcel.getString("username");
            String uname= (jsonExcel.has("uname"))?jsonExcel.getString("uname"):"";
            String name= (jsonExcel.has("name"))?jsonExcel.getString("name"):"";

            String ubatch = jsonExcel.getString("batch");
            String urole= jsonExcel.getString("role");
            String surname = (jsonExcel.has("surname"))?jsonExcel.getString("surname"):"";
            String collegeName =  (jsonExcel.has("collegeName"))?jsonExcel.getString("collegeName"):"";
            String university =  (jsonExcel.has("university"))?jsonExcel.getString("university"):"";
            String state =  (jsonExcel.has("state"))?jsonExcel.getString("state"):"";
            String city =  (jsonExcel.has("city"))?jsonExcel.getString("city"):"";
            String gender =  (jsonExcel.has("gender"))?jsonExcel.getString("gender"):"";
            String yearOfPassing = (jsonExcel.has("yearOfPassing"))?""+jsonExcel.getString("gender"):"";
            String tenthMarks = (jsonExcel.has("tenthMarks"))?""+ jsonExcel.getInt("tenthMarks"):"";
            String twelfthMarks = (jsonExcel.has("twelfthMarks"))?""+ jsonExcel.getInt("twelfthMarks"):"";
            String graduationMarks = (jsonExcel.has("graduationMarks"))?""+ jsonExcel.getInt("graduationMarks"):"";
            String number = (jsonExcel.has("number"))?""+ jsonExcel.getLong("number"):"";
            String status =  (jsonExcel.has("status"))?jsonExcel.getString("status"):"";
      //      String birthDate = jsonExcel.getString("birthDate");
            String graduation =  (jsonExcel.has("graduation"))?jsonExcel.getString("status"):"";
            String graduationBranch =  (jsonExcel.has("graduationBranch"))?jsonExcel.getString("graduationBranch"):"";

            Role role = roleRepository.findByRoleName(urole)
                    .orElseThrow(() -> new RoleNotFoundException(urole));
            Batch batch = batchRepository.findByName(ubatch)
                    .orElseThrow(() -> new BatchNotFoundException(ubatch));
            try{
                User request = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username));
                request.setBatch(batch);
                request.setEmail(email);
                request.setUsername(username);
                request.setUname(uname);
                request.setName(name);

                request.setRole(role);
                request.setSurname(surname);
                request.setCollegeName(collegeName);
                request.setUniversityName(university);
                request.setState(state);
                request.setCity(city);
                request.setGender(gender);
                request.setYearOfPassing(yearOfPassing);
                request.setTenthMarks(tenthMarks);
                request.setTwelfthMarks(twelfthMarks);
                request.setGraduationMarks(graduationMarks);
                request.setNumber(number);
                request.setStatus(status);
       //         request.setBirthDate(birthDate);
                request.setGraduation(graduation);
                request.setGraduationBranch(graduationBranch);
                userRepository.save(request);
            }catch(Exception UsernameNotFoundException){
                User request = new User();
                request.setBatch(batch);
                request.setEmail(email);
                request.setUsername(username);
                request.setUname(uname);
                request.setName(name);
                request.setPassword(passwordEncoder.encode("PacketPrep"));
                request.setRole(role);
                request.setSurname("NA");
                request.setCollegeName(collegeName);
                request.setUniversityName(university);
                request.setState(state);
                request.setCity(city);
                request.setGender(gender);
                request.setYearOfPassing(yearOfPassing);
                request.setTenthMarks(tenthMarks);
                request.setTwelfthMarks(twelfthMarks);
                request.setGraduationMarks(graduationMarks);
                request.setEnabled(true);
                request.setNumber(number);
                request.setStatus(status);
        //        request.setBirthDate(birthDate);
                request.setGraduation(graduation);
                request.setGraduationBranch(graduationBranch);
                userRepository.save(request);
            }

        }
        return "Uploaded";
    }
}
