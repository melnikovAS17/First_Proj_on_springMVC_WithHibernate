package ru.melnikov.projOnSpringMVC.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Person {
    private int id;
    @NotEmpty(message = "Name shouldn't be empty")
    @Pattern(regexp = "[A-Z]\\w+ [A-z]\\w+", message = "Author's name should be correct: Mikhail Mikhalkov")
    private String name;
    @NotEmpty(message = "data of birth shouldn't be empty")
    @Pattern(regexp = "^(0[1-9]|1\\d|2\\d|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$", message = "data od birth should be correct: dd.mm.year 12.12.2012")
    private String dateOfBirth;

   /* public Person(String name, String dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }
    public Person(){}*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
