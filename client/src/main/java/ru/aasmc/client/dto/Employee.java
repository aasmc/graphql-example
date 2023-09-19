package ru.aasmc.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private String firstName;
    private String lastName;
    private String position;
    private int salary;
    private int age;
    private Passport passport;
}
