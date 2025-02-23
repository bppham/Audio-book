package com.project.audiobook.dto.request.Employee;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeUpdationRequest {
    private String name;
    private String phoneNumber;
    private String email;
    private String avatar;
    private Set<String> roles;
}
