package ru.aasmc.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.aasmc.client.dto.Employee;
import ru.aasmc.client.dto.EmployeeInput;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final HttpGraphQlClient client;

    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeInput dto) {
        Mono<Employee> entity = client.documentName("createEmployee")
                .variable("dto", dto)
                .retrieve("newEmployee")
                .toEntity(Employee.class);
        return entity.block();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable("id") Integer id) {
        Mono<Employee> entity = client.documentName("employeeNoError")
                .variable("id", id)
                .retrieve("employee")
                .toEntity(Employee.class);
        return entity.block();
    }

    @GetMapping("/error/{id}")
    public Employee getEmployeeError(@PathVariable("id") Integer id) {
        Mono<Employee> entity = client.documentName("employeeError")
                .variable("id", id)
                .retrieve("employee")
                .toEntity(Employee.class);
        return entity.block();
    }

    @GetMapping("/list")
    public List<Employee> getAllEmployees() {
        return client.documentName("employees")
                .retrieve("employees")
                .toEntityList(Employee.class).block();
    }

}
