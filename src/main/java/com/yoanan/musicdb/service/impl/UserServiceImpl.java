package com.yoanan.musicdb.service.impl;

import com.yoanan.musicdb.model.entity.UserEntity;
import com.yoanan.musicdb.model.entity.UserRoleEntity;
import com.yoanan.musicdb.model.entity.enums.UserRole;
import com.yoanan.musicdb.repository.UserRepository;
import com.yoanan.musicdb.repository.UserRoleRepository;
import com.yoanan.musicdb.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void seedUsers() {

        if(userRepository.count()==0){
            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);

            userRoleRepository.saveAll(List.of(adminRole, userRole));

            UserEntity admin = new UserEntity()
                    .setName("admin")
                    .setPassword(passwordEncoder.encode("admin"))
                    .setRoles(List.of(adminRole, userRole));

            UserEntity user = new UserEntity()
                    .setName("user")
                    .setPassword(passwordEncoder.encode("user"))
                    .setRoles(List.of(userRole));

            userRepository.saveAll(List.of(admin, user));
        }



    }
}
