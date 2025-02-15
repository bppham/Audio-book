package com.project.audiobook.mapper;

import com.project.audiobook.dto.EmployeeDTO;
import com.project.audiobook.entity.Employee;
import com.project.audiobook.entity.Role;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EmployeeMapper {
    public EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getName(),
                employee.getPhoneNumber(),
                employee.getEmail(),
                employee.getAvatar(),
                employee.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
        );
    }

    public Employee toEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAvatar(employeeDTO.getAvatar());
        return employee;
    }

}
