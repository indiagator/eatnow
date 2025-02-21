package com.secor.eatnow;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usercredentials")
@Getter @Setter
public class UserCredential {

    @Id
    public String username;
    public String password;


}

