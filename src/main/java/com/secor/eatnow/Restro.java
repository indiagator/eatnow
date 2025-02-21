package com.secor.eatnow;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "restros")
@Setter @Getter
public class Restro {

    @Id
    public String restro_id;
    public String name;
    public String address; // Address should be in terms of geolocation but OK for now
    public String owner;

}
