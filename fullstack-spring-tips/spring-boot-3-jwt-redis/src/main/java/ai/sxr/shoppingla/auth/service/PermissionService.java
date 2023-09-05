package ai.sxr.shoppingla.auth.service;

import ai.sxr.shoppingla.auth.repository.PermissionRepository;
import ai.sxr.shoppingla.utils.GenericResponse;
import ai.sxr.shoppingla.utils.ResponseCode;
import ai.sxr.shoppingla.auth.dao.PermissionDao;
import ai.sxr.shoppingla.auth.dto.rolePermission.PermissionDto;
import ai.sxr.shoppingla.auth.model.RolePermission.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;
    private final PermissionDao permissionDao;

    public GenericResponse savePermission(PermissionDto request) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(request, permission);

        try {
            if (request.getParentId() != null) {
                if (!permissionRepository.findById(request.getParentId()).isPresent()) {
                    return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "No parent menu found");
                }
                permission.setParentId(permissionRepository.findById(request.getParentId()).get());
            }
            permissionRepository.save(permission);

            return new GenericResponse();
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service error");
        }
    }

    public List<PermissionDto> getPermissions() {
        List<Permission> permissionList = permissionRepository.findAll();
        List<PermissionDto> permissionDtoList = new ArrayList<>();
        for (Permission permission :
                permissionList) {
            PermissionDto dto = new PermissionDto();
            BeanUtils.copyProperties(permission, dto);
            permissionDtoList.add(dto);
        }
        return permissionDtoList;
    }

    public List<PermissionDto> getUserPermissionsThroughJPA(Long userId) {
        try {
            System.out.println("Starting to fetch user permissions through JPA Methods" +
                    " at "+new Date().toInstant());
            List<PermissionDto> permissionDtoList = new ArrayList<>();
//            Map<Long, PermissionDto> permissionMap = new HashMap<>();
//            List<UserRoleDto> userRoleDtoList = roleService.getUserRoles(userId);
//            for (UserRoleDto userRoleDto:
//                    userRoleDtoList) {
//                List<RolePermissionDto> rolePermissionDtoList = rolePermissionService.getRolePermissions(userRoleDto.getRoleId());
//                for (RolePermissionDto rolePermissionDto:
//                        rolePermissionDtoList) {
//                    PermissionDto permissionDto = new PermissionDto();
//                    Permission permission = permissionRepository.findById(rolePermissionDto.getPermissionId()).get();
//
//                    if (!permissionMap.containsKey(permission.getId())) {
//                        BeanUtils.copyProperties(permission, permissionDto);
//                        if (permission.getParentId() != null && permission.getParentId().getId() != null) {
//                            permissionDto.setParentId(permission.getParentId().getId());
//                        }
//                        permissionMap.put(permissionDto.getId(), permissionDto);
//                        permissionDtoList.add(permissionDto);
//                    }
//                }
//            }
            List<Permission> permissionEntityList = permissionRepository.findAllPermissionByUserId(userId);
            for (Permission permissionEntity:
                    permissionEntityList) {
                PermissionDto dto = new PermissionDto();
                BeanUtils.copyProperties(permissionEntity, dto);
                permissionDtoList.add(dto);
            }
            System.out.println("Ended fetching user permissions through JPA Methods" +
                    " at "+new Date().toInstant());

            return permissionDtoList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<PermissionDto> getUserPermissionsThroughNativeQuery(Long userId) {
        try {
            System.out.println("Starting to fetch user permissions through Native Query" +
                    " at "+new Date().toInstant());
            List<PermissionDto> list = permissionDao.getUserPermissionsThroughNativeQuery(userId);
            System.out.println("Ended fetching user permissions through Native Query" +
                    " at "+new Date().toInstant());
            return list;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }
}
