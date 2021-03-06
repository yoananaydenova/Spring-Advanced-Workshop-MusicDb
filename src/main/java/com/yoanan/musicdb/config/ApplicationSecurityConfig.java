package com.yoanan.musicdb.config;

import com.yoanan.musicdb.service.impl.MusicDBUserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                //.antMatchers("/js/**", "/css/**", "/images/**").permitAll()
                // Allow access to index, user login and registration to anyone
                .antMatchers("/", "/users/login", "/users/register" ).permitAll()
                // Protect all other pages
                .antMatchers("/**").authenticated()
                // Configure login with HTML form
                .and()
                // Our login page will be served by the controller with mapping /users/login
                .formLogin().loginPage("/users/login")
                // The name of the user name input field of OUR login form is username (optional)
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                // The name of the user password input field of OUR login form is password (optional)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                // On login success redirect here
                .defaultSuccessUrl("/home")
                // On login failure redirect here
                .failureForwardUrl("/users/login-error")
                .and()
                .logout()
                // Which endpoint performs logout, e.g. http://localhost:8080/logout (! This should be POST request!)
                .logoutUrl("/logout")
                // Where to land after logout
                .logoutSuccessUrl("/")
                // Remove the session from the server
                .invalidateHttpSession(true)
                // Delete the session cookie
                .deleteCookies("JSESSIONID"); //bye!!!
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
        .userDetailsService(musicDBUserService)
                .passwordEncoder(passwordEncoder);

    }
}
