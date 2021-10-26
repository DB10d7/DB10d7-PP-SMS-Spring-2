package com.packetprep.system.service;
import com.packetprep.system.Model.*;
import com.packetprep.system.dto.*;
import com.packetprep.system.exception.BatchNotFoundException;

import com.packetprep.system.exception.DayNotFoundException;
import com.packetprep.system.exception.SpringPPSystemException;
import com.packetprep.system.exception.StudentNotFoundException;
import com.packetprep.system.mapper.DayMapper;
import com.packetprep.system.mapper.StudentMapper;
import com.packetprep.system.repository.*;
import com.packetprep.system.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class StudentService {


    private final BatchRepository batchRepository;

    private final StudentMapper studentMapper;
    private final DayRepository dayRepository;
    private final DayMapper dayMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

  /*  public void signup(StudentDto studentDto) {
        String batchName="Default";
        Batch batch = batchRepository.findByName(batchName)
                .orElseThrow(() -> new BatchNotFoundException(batchName));
        Student student = studentMapper.mapFromDtoToStudent(studentDto, batch);

        studentRepository.save(student);

        // String token = generateVerificationToken(user);
      /*  iyvyyiyvivyiyiyivyliyliylyumailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));j9uh7j;9yujt6hmt,hiu.kuk
    }*/
 /*   public AuthenticationResponse login(StudentLoginDto studentLoginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(studentLoginDto.getStudentName(),
                studentLoginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(studentLoginDto.getStudentName())
                .build();
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
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringPPSystemException("Invalid Token")));
    }
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String studentName = verificationToken.getStudent().getStudentName();
        Student student = studentRepository.findByStudentName(studentName).orElseThrow(() -> new SpringPPSystemException("Student not found with name - " + studentName));
       // student.setEnabled(true);
        studentRepository.save(student);
    }
   /* private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    } */
  /*  @Transactional(readOnly = true)
    public Student getCurrentStudent() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return studentRepository.findByStudentName(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }
    /*   @Transactional
       public void save(StudentDto studentDto) {
           Batch batch = batchRepository.findByName(studentDto.getBatchName())
                   .orElseThrow(() -> new BatchNotFoundException(studentDto.getBatchName()));
           Student student = studentMapper.mapFromDtoToStudent(studentDto, batch);
           studentRepository.save(student);
       } */
    @Transactional
    public List<AuthenticationResponse> showAllStudent() {
        List<User> students = userRepository.findAll();
        return students.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
    @Transactional
    public AuthenticationResponse readSingleStudent(String studentName) {
        User student = userRepository.findByUsername(studentName).orElseThrow(() -> new StudentNotFoundException(studentName));
        return studentMapper.mapFromStudentToDto(student);
    }
    @Transactional(readOnly = true)
    public List<AuthenticationResponse>getStudentsByBatch(String batchName) {
        Batch batch = batchRepository.findByName(batchName)
                .orElseThrow(() -> new BatchNotFoundException(batchName));
        List<User> students = userRepository.findAllByBatch(batch);
        return students.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
    @Transactional(readOnly = true)
    public List<AuthenticationResponse> getStudentsByDay(String dayName) {
        Day day = dayRepository.findByDayName(dayName)
                .orElseThrow(() -> new DayNotFoundException(dayName));
        List<User> users = userRepository.findAllByDay(day);
        return users.stream().map(studentMapper::mapFromStudentToDto).collect(toList());
    }
    @Transactional(readOnly = true)
    public List<DayResponse> getDaysByStudent(String studentName) {
        User student = userRepository.findByUsername(studentName)
                .orElseThrow(() -> new StudentNotFoundException(studentName));
        List<Day> days = dayRepository.findByUser(student);
        return days.stream().map(dayMapper::mapFromDayToDto).collect(toList());
    }
}
