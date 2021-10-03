package com.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private String id;
    @NotEmpty
    private String idCustomer;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private List<String> roles;
    private int state;
}
