//package com.packetprep.system.repository;
//
//import com.packetprep.system.Model.Image;
//import com.packetprep.system.Model.StudentAadhar;
//import com.packetprep.system.Model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface StudentAadharRepository extends JpaRepository<StudentAadhar, Long> {
//    Optional<StudentAadhar> findById(Long id);
//
//    Optional<StudentAadhar> findByUser(User user);
//
//    @Override
//    void delete(StudentAadhar entity);
//}
