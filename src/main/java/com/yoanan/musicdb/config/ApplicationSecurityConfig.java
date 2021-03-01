package com.yoanan.musicdb.config;

import com.yoanan.musicdb.service.impl.MusicDBUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MusicDBUserService musicDBUserService;
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(MusicDBUserService musicDBUserService, PasswordEncoder passwordEncoder) {
        this.musicDBUserService = musicDBUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                // Allow access to static resources to anyone
                .antMatchers("/js/**", "/css/**", "/img/**").permitAll()
                // Allow access to index, user login and registration to anyone
                .antMatchers("/", "/users/login", "/users/register" ).permitAll()
                // Protect all other pages
                .antMatchers("/**").authenticated()
                // Configure login with HTML form
                .and()
                // Our login page will be served by the controller with mapping /users/login
                .formLogin().loginPage("/users/login")
                // The name of the user name input field of OUR login form is username (optional)
                .usernameParameter("username")
                // The name of the user password input field of OUR login form is password (optional)
                .passwordParameter("password")
                // On login success redirect here
                .defaultSuccessUrl("/home")
                // On login failure redirect here
                .failureForwardUrl("/users/login-error"); //todo errors for login
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
        .userDetailsService(musicDBUserService)
                .passwordEncoder(passwordEncoder);

    }
}
