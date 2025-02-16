package com.project.audiobook.dto;

import com.project.audiobook.entity.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String avatar;
    private Set<String> roles;
}
