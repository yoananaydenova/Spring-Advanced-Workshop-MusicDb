package com.yoanan.musicdb.service.impl;

import com.yoanan.musicdb.model.entity.UserEntity;
import com.yoanan.musicdb.model.entity.UserRoleEntity;
import com.yoanan.musicdb.model.entity.enums.UserRole;
import com.yoanan.musicdb.model.service.UserRegisterServiceModel;
import com.yoanan.musicdb.repository.UserRepository;
import com.yoanan.musicdb.repository.UserRoleRepository;
import com.yoanan.musicdb.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final MusicDBUserService musicDBUserService;

    public UserServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, MusicDBUserService musicDBUserService) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.musicDBUserService = musicDBUserService;
    }

    @Override
    public void seedUsers() {

        if (userRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRole.ADMIN);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRole.USER);

            userRoleRepository.saveAll(List.of(adminRole, userRole));

            UserEntity admin = new UserEntity()
                    .setUsername("admin")
                    .setFullName("Admin Adminov")
                    .setPassword(passwordEncoder.encode("admin"))
                    .setRoles(List.of(adminRole, userRole));

            UserEntity user = new UserEntity()
                    .setUsername("user")
                    .setFullName("User Userov")
                    .setPassword(passwordEncoder.encode("user"))
                    .setRoles(List.of(userRole));

            userRepository.saveAll(List.of(admin, user));
        }


    }

    @Override
    public void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel) {

        // REGISTER

        UserEntity newUser = modelMapper.map(userRegisterServiceModel, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));

        UserRoleEntity userRoleEntity = userRoleRepository.findByRole(UserRole.USER)
                .orElseThrow(() -> new IllegalStateException("USER role not found! Please seed the roles!"));

        newUser.addRole(userRoleEntity);

        newUser = userRepository.save(newUser);

        // LOGIN

        UserDetails principal = musicDBUserService.loadUserByUsername(newUser.getUsername());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        principal,
                        newUser.getPassword(),
                        principal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Override
    public boolean usernameExists(String username) {
       return userRepository.findByUsername(username).isPresent();
    }
}
