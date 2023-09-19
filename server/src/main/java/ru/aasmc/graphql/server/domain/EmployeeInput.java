package ru.aasmc.graphql.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInput {
    private String firstName;
    private String lastName;
    private String position;
    private int salary;
    private int age;
    private Passport passport;

    @JsonIgnore
    public Passport getDefaultPassport() {
        Passport p = new Passport();
        p.setCode(100);
        p.setNumber(100100);
        return p;
    }
}
