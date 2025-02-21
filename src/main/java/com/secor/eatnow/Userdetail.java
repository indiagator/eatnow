package com.secor.eatnow;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userdetails")
@Getter @Setter
public class Userdetail {

    @Id
    public String username;
    public String usertype; // CONSUMER, RESTRO, DELIVERY
    public String fullname;

}
