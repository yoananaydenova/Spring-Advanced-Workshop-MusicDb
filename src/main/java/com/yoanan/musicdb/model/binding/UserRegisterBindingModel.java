package com.yoanan.musicdb.model.binding;

import com.yoanan.musicdb.model.validators.FieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldMatch(
      first = "password",
        second = "confirmPassword"
)
public class UserRegisterBindingModel {

    private String username;
    private String email;
    private String fullName;
    private String password;
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    @NotEmpty
    @Size(min = 3)
    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @NotEmpty
    @Email
    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @NotEmpty
    @Size(min = 3)
    public String getFullName() {
        return fullName;
    }

    public UserRegisterBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @NotEmpty
    @Size(min = 5, max = 20)
    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @NotEmpty
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
