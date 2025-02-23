package com.project.audiobook.service;

import com.project.audiobook.dto.request.Employee.EmployeeCreationRequest;
import com.project.audiobook.dto.request.Employee.EmployeeUpdationRequest;
import com.project.audiobook.dto.response.EmployeeResponse;
import com.project.audiobook.entity.Employee;
import com.project.audiobook.entity.Role;
import com.project.audiobook.exception.AppException;
import com.project.audiobook.exception.ErrorCode;
import com.project.audiobook.mapper.EmployeeMapper;
import com.project.audiobook.repository.EmployeeRepository;
import com.project.audiobook.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService {
    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;
    RoleRepository roleRepository;

    FileStorageService fileStorageService;

    public EmployeeResponse createEmployee(EmployeeCreationRequest request, MultipartFile avatarFile) throws IOException {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMPLOYEE_EMAIL_EXISTED);
        }
        // Password
        request.setPassword(request.getPhoneNumber());

        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)))
                .collect(Collectors.toSet());

        Employee employee = employeeMapper.toEmployee(request);
        employee.setRoles(roles);

        // **Lưu avatar nếu có**
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String avatarFileName = fileStorageService.storeAvatar(avatarFile);
            employee.setAvatar("avatars/" + avatarFileName); // Lưu đường dẫn tương đối
        }

        employee = employeeRepository.save(employee);
        return employeeMapper.toEmployeeResponse(employee);
    }

    public EmployeeResponse getEmployee(Long id){
        return employeeMapper.toEmployeeResponse(employeeRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND)));
    }

    public void deleteEmployee(Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
        employeeRepository.deleteById(id);
    }

    public List<EmployeeResponse> getAllEmployees(){
        return employeeRepository.findAll().stream().map(employeeMapper::toEmployeeResponse).collect(Collectors.toList());
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeUpdationRequest request,
                                           MultipartFile avatarFile) throws IOException {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        System.out.println(request.getRoles());

        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        System.out.println("Roles from DB:");
        roles.forEach(role -> System.out.println(" - " + role.getId() + " | " + role.getName()));

        employee.setRoles(roles);

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(request.getPhoneNumber());

        // Nếu có file avatar mới, lưu nó
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String avatarFileName = fileStorageService.storeAvatar(avatarFile);
            employee.setAvatar(avatarFileName);
        }

        employee = employeeRepository.save(employee);
        return employeeMapper.toEmployeeResponse(employee);
    }


}
