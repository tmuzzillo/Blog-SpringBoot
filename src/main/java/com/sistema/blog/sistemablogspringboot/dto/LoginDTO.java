package com.sistema.blog.sistemablogspringboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDTO {

    private String usernameOrEmail;
    private String password;

}
