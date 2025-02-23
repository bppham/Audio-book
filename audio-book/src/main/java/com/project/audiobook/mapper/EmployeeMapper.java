package com.project.audiobook.mapper;

import com.project.audiobook.dto.request.Employee.EmployeeCreationRequest;
import com.project.audiobook.dto.request.Employee.EmployeeUpdationRequest;
import com.project.audiobook.dto.response.EmployeeResponse;
import com.project.audiobook.entity.Employee;
import com.project.audiobook.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleNames")
    EmployeeResponse toEmployeeResponse(Employee employee);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleEntities")
    Employee toEmployee(EmployeeCreationRequest request);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleEntities")
    Employee toEmployeeFromUpdation(EmployeeUpdationRequest request);

    @Named("mapRoleNames")
    default Set<String> mapRoleNames(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    @Named("mapRoleEntities")
    default Set<Role> mapRoleEntities(Set<String> roleNames) {
        return roleNames.stream().map(name -> {
            Role role = new Role();
            role.setName(name);
            return role;
        }).collect(Collectors.toSet());
    }

    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleEntities")
    void updateEmployeeFromRequest(EmployeeUpdationRequest request, @MappingTarget Employee employee);
}
