package com.mobica.rnd.parking.parkingbe.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;


    @Pattern(regexp = Patterns.userNameAndSurnamePattern, message = "{msg.validation.user.usernameandsurname.pattern}")
    @Size(max = 255, message = "{msg.validation.user.usernameandsurname.size}")
    @NotNull(message = "{msg.validation.user.usernameandsurname.notnull}")
    private String userNameAndSurname;

    @Size(min = 4, max = 4, message = "{msg.validation.user.forlettershortcut.size}")
    @NotNull(message = "{msg.validation.user.forlettershortcut.notnull}")
    private String fourLetterMobicaShortcut;

    @Size(max = 100, message = "{msg.validation.user.useremailadrdress.size}")
    @Pattern(regexp = Patterns.userEmailPattern, message = "{msg.validation.user.useremailadrdress.pattern}")
    @NotNull(message = "{msg.validation.user.useremailadrdress.notnull}")
    private String userEmailAddress;


    public User(String userNameAndSurname, String fourLetterMobicaShortcut, String userEmailAddress) {
        this.userNameAndSurname = userNameAndSurname;
        this.fourLetterMobicaShortcut = fourLetterMobicaShortcut;
        this.userEmailAddress = userEmailAddress;
    }

}

