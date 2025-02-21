package com.secor.eatnow;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dishes")
@Getter @Setter
public class Dish {

    @Id
    public String dish_id;
    public String name;
    public String description;
    public String restro_id;

}
