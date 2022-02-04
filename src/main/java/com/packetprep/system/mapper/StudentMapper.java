package com.packetprep.system.mapper;

import com.packetprep.system.Model.User;
import com.packetprep.system.dto.StudentResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public class StudentMapper {

//    @Autowired
//    private ImageService imageService;

  /*  public Student mapFromDtoToStudent(StudentDto studentDto, Batch batch) {
        Student student = new Student();
        student.setStudentName(studentDto.getStudentName());
        student.setPassword(studentDto.getPassword());
        student.setStudentEmail(studentDto.getStudentEmail());
        student.setName(studentDto.getName());
        student.setFatherName(studentDto.getFatherName());
        student.setNumber(studentDto.getNumber());
        student.setBatch(batch);
        student.setAge(studentDto.getAge());
        student.setPassOutYear(studentDto.getPassOutYear());
      //  student.setDays();
        student.setCreatedOn(Instant.now());
        student.setUpdatedOn(Instant.now());
        student.setRole(studentDto.getRole());
        return student;
    }
  public User mapFromDtoToStudent(RegisterRequest registerRequest){
      User user = new User();
      user.setUsername(registerRequest.getUsername());
      user.setEmail(registerRequest.getEmail());
      user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
      user.setCreated(Instant.now());
      user.setEnabled(true);
      return user;
  } */

    public StudentResponse mapFromStudentToDto(User user) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(user.getUserId());
        studentResponse.setUsername(user.getUsername());
        studentResponse.setUname(user.getUname());
        studentResponse.setName(user.getName());
        studentResponse.setEmail(user.getEmail());
        studentResponse.setRole(user.getRole().getRoleName());
        studentResponse.setSurname(user.getSurname());
        studentResponse.setState(user.getState());
        studentResponse.setCity(user.getCity());
        studentResponse.setStatus(user.getStatus());
        studentResponse.setBirthDate(user.getBirthDate());
        studentResponse.setCollegeName(user.getCollegeName());
        studentResponse.setUniversity(user.getUniversityName());
        studentResponse.setGender(user.getGender());
        studentResponse.setTenthMarks(user.getTenthMarks());
        studentResponse.setTwelfthMarks(user.getTwelfthMarks());
        studentResponse.setGraduationMarks(user.getGraduationMarks());
        studentResponse.setBatch(user.getBatch().getName());
        studentResponse.setYearOfPassing(user.getYearOfPassing());
        studentResponse.setNumber(user.getNumber());
        studentResponse.setGraduation(user.getGraduation());
        studentResponse.setGraduationBranch(user.getGraduationBranch());

//        studentResponse.setFName(user.getFName());
//        studentResponse.setFNumber(user.getFNumber());
//        studentResponse.setCenter(user.getCenter());
//        studentResponse.setComment(user.getComment());
//        studentResponse.setUid(user.getUid());
//        studentResponse.setAddress(user.getAddress());
//        studentResponse.setJDate(user.getJDate());

        return studentResponse;
    }
//    public StudentResponse mapFromStudentToDtoStudentDetails(User user) {
//        StudentResponse studentResponse = new StudentResponse();
//        studentResponse.setId(user.getUserId());
//        studentResponse.setUsername(user.getUsername());
//        studentResponse.setName(user.getName());
//        studentResponse.setEmail(user.getEmail());
//        studentResponse.setRole(user.getRole().getRoleName());
//        studentResponse.setPassword(user.getPassword());
//        studentResponse.setBatch(user.getBatch().getName());
//        // studentResponse.setFile((MultipartFile) imageService.getImage(user));
//        return studentResponse;
//    }
   /* public AuthenticationResponse mapFromStudentToDto(User user) {
        AuthenticationResponse studentDto = new AuthenticationResponse();
        studentDto.setUsername(user.getUsername());
        studentDto.setStudentName(student.getStudentName());
        studentDto.setStudentEmail(student.getStudentEmail());
        studentDto.setName(student.getName());
        studentDto.setFatherName(student.getFatherName());
        studentDto.setBatchName(student.getBatch().getName());
        studentDto.setAge(student.getAge());
        studentDto.setNumber(student.getNumber());
        studentDto.setPassOutYear(student.getPassOutYear());
        return studentDto;
    } */
}
