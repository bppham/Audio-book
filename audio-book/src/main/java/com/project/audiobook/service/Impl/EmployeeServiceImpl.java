package com.project.audiobook.service.Impl;

import com.project.audiobook.dto.EmployeeDTO;
import com.project.audiobook.entity.Employee;
import com.project.audiobook.entity.Role;
import com.project.audiobook.mapper.EmployeeMapper;
import com.project.audiobook.repository.EmployeeRepository;
import com.project.audiobook.repository.RoleRepository;
import com.project.audiobook.service.EmployeeService;
import com.project.audiobook.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO, MultipartFile avatarFile) {
        if(employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String defaultPassword = employeeDTO.getPhoneNumber();

        String avatarUrl = (avatarFile != null) ?fileStorageService.storeFile(avatarFile) : null;
        employeeDTO.setAvatar(avatarUrl);

        Set<Role> roles = employeeDTO.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setPassword(passwordEncoder.encode(defaultPassword));
        employee.setRoles(roles);
        employeeRepository.save(employee);
        return employeeMapper.toDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = employees.stream()
                .map(employeeMapper::toDTO)
                .collect(Collectors.toList());
        return employeeDTOS;
    }

    @Override
    public void deleteEmployee(Long id) {
        if(!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        } else {
            employeeRepository.deleteById(id);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return employeeMapper.toDTO(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO, MultipartFile avatarFile) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Update image
        if(avatarFile != null && !avatarFile.isEmpty()) {
            String avatarUrl = fileStorageService.storeFile(avatarFile);
            employeeDTO.setAvatar(avatarUrl);
            System.out.println("Have new image: " + avatarUrl);
        } else {
            employeeDTO.setAvatar(existingEmployee.getAvatar());
            System.out.println("Old image");
        }

        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employeeRepository.save(existingEmployee);

        return employeeMapper.toDTO(existingEmployee);
    }
}
