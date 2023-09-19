package ru.aasmc.graphql.server.resolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.aasmc.graphql.server.domain.Employee;
import ru.aasmc.graphql.server.domain.EmployeeInput;
import ru.aasmc.graphql.server.error.GraphQLServerError;
import ru.aasmc.graphql.server.repository.EmployeeRepository;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository repository;

    @QueryMapping
    public Iterable<Employee> employees() {
        return repository.findAll();
    }

    @QueryMapping
    public Employee employee(@Argument Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            return new GraphQLServerError("Employee with ID = " + id + " not found", 404);
        });
    }

    @QueryMapping
    public Employee employeeError(@Argument Integer id) {
        throw new GraphQLServerError("Employee with ID = " + id + " not found", 404);
    }

    @MutationMapping
    public Employee newEmployee(@Argument EmployeeInput employee) {
        var toSave = Employee.builder()
                .id(null)
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .age(employee.getAge())
                .salary(employee.getSalary())
                .position(employee.getPosition())
                .passport(employee.getPassport() == null ? employee.getDefaultPassport() : employee.getPassport())
                .build();
        return repository.save(toSave);
    }

}
