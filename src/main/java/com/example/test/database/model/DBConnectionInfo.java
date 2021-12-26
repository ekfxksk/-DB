package com.example.test.database.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DBConnectionInfo {
    private String driverClassname;
    private String url;
    private String username;
    private String password;
}
