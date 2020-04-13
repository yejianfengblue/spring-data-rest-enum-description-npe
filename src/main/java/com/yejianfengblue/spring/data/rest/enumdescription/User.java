package com.yejianfengblue.spring.data.rest.enumdescription;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    Long id;

    @Description("User name")
    String name;

    @Description("User gender, MALE or FEMALE")
    Gender gender;
}
