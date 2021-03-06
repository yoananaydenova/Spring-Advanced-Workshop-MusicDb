package com.yoanan.musicdb.service;

import com.yoanan.musicdb.model.service.UserRegisterServiceModel;

public interface UserService {

    void seedUsers();

    void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel);

    boolean usernameExists(String username);
}
