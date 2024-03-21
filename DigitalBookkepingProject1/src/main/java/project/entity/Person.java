package project.entity;

import jakarta.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message = "Need to provide fullname")
    @Size(min = 2, max = 30, message = "Full name`s length should be between 2 and 30 characters")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Pattern of Writing name should be like {Name Surname}")
    private String fullName;
    @Min(value = 0, message = "birthday should be more than 0")
    private int birthday;

    public Person() {
    }

    public Person(int id, String fullName, int birthday) {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }
}
