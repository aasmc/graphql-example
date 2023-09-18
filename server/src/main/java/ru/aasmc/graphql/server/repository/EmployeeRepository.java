package ru.aasmc.graphql.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aasmc.graphql.server.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
