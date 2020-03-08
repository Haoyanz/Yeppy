package yeppy.service.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequestModel {
    private String username;
    private char[] password;

    public RegisterRequestModel() {

    }

    @JsonCreator
    public RegisterRequestModel(@JsonProperty(value = "username", required = true)String email, @JsonProperty(value = "password", required = true)char[] password) {
        this.username = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}