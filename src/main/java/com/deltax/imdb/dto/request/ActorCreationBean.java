package com.deltax.imdb.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class ActorCreationBean {

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._ ]+[a-zA-Z0-9_.]$",
            message = "Actor name should contain minimum 3 characters")
    @NotBlank(message = "Actor name cannot be empty or null")
    private String name;

    @NotBlank(message = "Please enter gender,cannot be empty")
    private String sex;

    private Date dob;

    private String bio;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
