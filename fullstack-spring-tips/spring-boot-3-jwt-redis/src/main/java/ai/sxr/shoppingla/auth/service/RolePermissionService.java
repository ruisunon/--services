package ai.sxr.shoppingla.auth.service;

import ai.sxr.shoppingla.auth.dto.rolePermission.*;
import ai.sxr.shoppingla.auth.repository.*;
import ai.sxr.shoppingla.utils.GenericResponse;
import ai.sxr.shoppingla.utils.ResponseCode;
import ai.sxr.shoppingla.auth.model.RolePermission.Permission;
import ai.sxr.shoppingla.auth.model.RolePermission.Role;
import ai.sxr.shoppingla.auth.model.RolePermission.RolePermission;
import ai.sxr.shoppingla.auth.model.RolePermission.UserRole;
import ai.sxr.shoppingla.auth.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public GenericResponse saveRole(RoleDto request) {
        Role role = new Role();
        BeanUtils.copyProperties(request, role);
        try{
            roleRepository.save(role);
            return new GenericResponse();
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service error");
        }
    }

    public List<RoleDto> getRoles() {
        List<Role> roleList = roleRepository.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role:
             roleList) {
            RoleDto dto = new RoleDto();
            BeanUtils.copyProperties(role, dto);
            roleDtoList.add(dto);
        }
        return roleDtoList;
    }

    public GenericResponse savePermission(PermissionDto request) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(request, permission);

        if (request.getParentId() != null) {
            if (!permissionRepository.findById(request.getParentId()).isPresent()) {
                return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "No parent menu found");
            }
            permission.setParentId(permissionRepository.findById(request.getParentId()).get());
        }
        try{
            permissionRepository.save(permission);
            return new GenericResponse();
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service error");
        }
    }

    public List<PermissionDto> getPermissions() {
        List<Permission> permissionList = permissionRepository.findAll();
        List<PermissionDto> permissionDtoList = new ArrayList<>();
        for (Permission permission:
                permissionList) {
            PermissionDto dto = new PermissionDto();
            BeanUtils.copyProperties(permission, dto);
            permissionDtoList.add(dto);
        }
        return permissionDtoList;
    }

    @Transactional
    public GenericResponse saveUserRole(List<UserRoleDto> request) {
        if (request.size() > 0) {
            Optional<User> user = userRepository.findById(request.get(0).getUserId());
            if (user.isPresent()) {
                for (UserRoleDto dto :
                        request) {
                    UserRole userRole = new UserRole();
                    BeanUtils.copyProperties(dto, userRole);
                    userRole.setUser(user.get());
                    userRole.setRole(roleRepository.findById(dto.getRoleId()).get());
                }
                return new GenericResponse();
            } else {
                return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
            }
        } else {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
        }
    }

    @Transactional
    public GenericResponse saveRolePermission(SaveRolePermissionRequest request) {
        try {
            if (request != null && request.rolePermissionDtoList != null && request.rolePermissionDtoList.size() > 0) {
                Optional<Role> role = roleRepository.findById(request.rolePermissionDtoList.get(0).getRoleId());
                if (role.isPresent()) {
                    for (RolePermissionDto dto :
                            request.rolePermissionDtoList) {
                        RolePermission rolePermission = new RolePermission();

                        Optional<Permission> permissionEntity = permissionRepository.findById(dto.getPermissionId());
                        if (permissionEntity.isPresent()) {
                            rolePermission = rolePermissionRepository.findByRoleAndPermission(role.get(), permissionEntity.get());
                            if (rolePermission == null) {
                                rolePermission = new RolePermission();
                                rolePermission.setRole(role.get());
                                rolePermission.setPermission(permissionEntity.get());
                            }
                        } else {
                            return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong role id");
                        }

//                        BeanUtils.copyProperties(dto, rolePermission);

                        rolePermission.setActive(dto.isActive());
                        rolePermissionRepository.save(rolePermission);
                    }
                    return new GenericResponse();
                } else {
                    return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Role not found");
                }
            } else {
                return new GenericResponse(ResponseCode.REQUIRED_PARAMETER_MISSING.getCode(), "Request Empty");
            }
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
        }
    }

    public List<RolePermissionDto> getRolePermissions(Long roleId) {
        try {
            Set<RolePermission> rolePermissionList = rolePermissionRepository.
                    findAllByRoleAndActive(roleRepository.findById(roleId).get(), true);
            List<RolePermissionDto> rolePermissionDtoList = new ArrayList<>();
            for (RolePermission dto:
                    rolePermissionList) {
                RolePermissionDto userRoleDto = new RolePermissionDto();
                BeanUtils.copyProperties(dto, userRoleDto);
                userRoleDto.setRoleId(dto.getRole().getId());
                userRoleDto.setPermissionId(dto.getPermission().getId());

                rolePermissionDtoList.add(userRoleDto);
            }
            return rolePermissionDtoList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public GenericResponse saveSingleRolePermissions(SaveSingleRolePermissionsRequest request) {
        try {
            if (request != null && request.roleId() != null &&
                    request.permissionList() != null && request.permissionList().size() > 0) {
                Optional<Role> roleEntity = roleRepository.findById(request.roleId());
                for (PermissionDto dto :
                        request.permissionList()) {
                    RolePermission rolePermission = new RolePermission();

                    Optional<Permission> permissionEntity = permissionRepository.findById(dto.getId());
                    if (roleEntity.isPresent() && permissionEntity.isPresent()) {
                        rolePermission = rolePermissionRepository.findByRoleAndPermission(roleEntity.get(), permissionEntity.get());
                        if (rolePermission == null) {
                            rolePermission = new RolePermission();
                            rolePermission.setRole(roleEntity.get());
                            rolePermission.setPermission(permissionEntity.get());
                        }
                    } else {
                        return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong role id");
                    }

                    rolePermission.setActive(dto.isActive());
                    rolePermissionRepository.save(rolePermission);
                }
                return new GenericResponse();
            } else {
                return new GenericResponse(ResponseCode.REQUIRED_PARAMETER_MISSING.getCode(), "Request Empty");
            }
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
        }
    }
}
