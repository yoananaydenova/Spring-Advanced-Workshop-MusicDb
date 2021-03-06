package com.yoanan.musicdb.service.impl;

import com.yoanan.musicdb.model.entity.UserEntity;
import com.yoanan.musicdb.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MusicDBUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public MusicDBUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " was not found!"));


        return mapToUserDetails(userEntity);
    }

    private UserDetails mapToUserDetails(UserEntity userEntity) {

        List<GrantedAuthority> authorities =
                userEntity
                        .getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                        .collect(Collectors.toList());


        UserDetails result = new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities
                );

        return result;
    }
}
