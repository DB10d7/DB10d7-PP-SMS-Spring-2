package com.packetprep.system.config;


import com.packetprep.system.repository.UserRepository;
import com.packetprep.system.security.JwtAuthenticationFilter;

import com.packetprep.system.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;


//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/signup").permitAll()
                .antMatchers("/api/auth/signupAdmin").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/auth/accountVerification/{token}").permitAll()
                .antMatchers("/api/auth/forgotPassword/{username}").permitAll()
                .antMatchers("/api/auth/resetPassword").permitAll()
                .antMatchers("/api/user/image/upload").permitAll()
                .antMatchers("/api/auth/refresh/token").authenticated()
                .antMatchers("/api/auth/get/currentUser").authenticated()
                .antMatchers("/api/auth/updateProfile").authenticated()
                .antMatchers("/api/user/image/update").authenticated()
                .antMatchers("/api/user/image/get/{username}").authenticated()
                .antMatchers("/api/auth/logout").permitAll()
                .antMatchers("/api/auth/get/trainersList").hasAnyAuthority("ADMIN","TRAINER","SUPER-ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/auth/**").hasAnyAuthority("ADMIN","TRAINER","SUPER-ADMIN")
                .antMatchers("/api/auth/**").hasAnyAuthority("ADMIN","TRAINER","SUPER-ADMIN")
                .antMatchers( HttpMethod.POST,"/api/days/addStudent").hasAnyAuthority("ADMIN","TRAINER","SUPER-ADMIN")
                .antMatchers("/api/days/studentsNotPresent").hasAnyAuthority("ADMIN","TRAINER","SUPER-ADMIN")
                .antMatchers( HttpMethod.GET,"/api/days/by-batch/{name}").hasAnyAuthority("ADMIN","TRAINER","SUPER-ADMIN","STUDENT")
                .antMatchers( HttpMethod.DELETE,"/api/days/**").hasAnyAuthority("TRAINER","SUPER-ADMIN","ADMIN")
                .antMatchers( HttpMethod.GET,"/api/days/**").hasAnyAuthority("TRAINER","SUPER-ADMIN","ADMIN")
                .antMatchers(HttpMethod.GET,"/api/students/**").hasAnyAuthority("STUDENT","ADMIN","TRAINER","SUPER-ADMIN")
                .antMatchers( HttpMethod.GET,"/api/batch/{batchName}").hasAnyAuthority("TRAINER","SUPER-ADMIN","ADMIN")
                .antMatchers("/api/batch/**").hasAnyAuthority("TRAINER","SUPER-ADMIN","ADMIN")
                .antMatchers( HttpMethod.POST,"/api/days/**").hasAnyAuthority("TRAINER","SUPER-ADMIN")
                .antMatchers( "/api/students/**").hasAnyAuthority("ADMIN","TRAINER","SUPER-ADMIN")
                .antMatchers( "/api/role/").hasAuthority("SUPER-ADMIN");

        httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

      /*  httpSecurity.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/signup").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/students/").permitAll()
                .antMatchers( "/api/students/**").hasRole("ADMIN")
                .antMatchers( "/api/batch/**").hasRole("ADMIN")
                .antMatchers( "/api/days/**").hasRole("ADMIN")
                .antMatchers( "/api/role/").hasRole("ADMIN")
                .antMatchers("/api/auth/**").hasRole("ADMIN");

             //   .permitAll()

             //   .permitAll();

            /*    .antMatchers(HttpMethod.GET, "/api/batch/")
                .permitAll();
                .antMatchers(HttpMethod.GET, "/api/subreddit")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/**")
                .permitAll()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**")
                .permitAll()

                .anyRequest()
                .authenticated();
        httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class); */
    }

  /*  @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    } */

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
