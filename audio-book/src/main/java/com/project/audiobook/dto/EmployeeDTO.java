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
    private String name;
    private String phoneNumber;
    private String email;
    private String avatar;
    private Set<String> roles;
}
