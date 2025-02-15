package com.project.audiobook.service;

import com.project.audiobook.dto.EmployeeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO, MultipartFile avatarFile);
    List<EmployeeDTO> getAllEmployees();
    void deleteEmployee(Long id);
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO, MultipartFile avatarFile);
}
