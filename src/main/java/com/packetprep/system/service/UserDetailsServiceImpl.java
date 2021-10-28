package com.packetprep.system.service;

import com.packetprep.system.Model.User;
import com.packetprep.system.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetailsServiceImpl(){};

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {

       Optional<User>  userOptional = userRepository.findByUsername(username);
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found with username : " + username));
       /* Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found with username : " + username)); */

      //  return new MyUserDetails(user);
       return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities(user.getRole().getRoleName(),user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role, User user) {

        @NotBlank(message = "Role is required") String roles = user.getRole().getRoleName();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roles));
        return authorities;
      //  return singletonList(new SimpleGrantedAuthority(role));
    }
/*
   @Transactional(readOnly = true)
    public UserDetails loadStudentByStudentName(String studentName) {
        Optional<Student> userOptional = studentRepository.findByStudentName(studentName);
        Student student = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found with username : " + studentName));

        return new org.springframework.security
                .core.userdetails.User(student.getStudentName(), student.getPassword(),
                student.isEnabled(), true, true,
                true, getAuthorities("Student"));
    } */
}
