package ai.sxr.shoppingla.auth.controller;

import ai.sxr.shoppingla.utils.GenericResponse;
import ai.sxr.shoppingla.auth.dto.rolePermission.PermissionDto;
import ai.sxr.shoppingla.auth.service.PermissionService;
import ai.sxr.shoppingla.auth.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PermissionController {
    private final RolePermissionService rolePermissionService;
    private final PermissionService permissionService;

    @PostMapping(path = "/permission/save")
    public GenericResponse savePermission(@RequestBody PermissionDto request) {
        return permissionService.savePermission(request);
    }

    @GetMapping(path = "/permission/get")
    public List<PermissionDto> getPermissions() {
        return permissionService.getPermissions();
    }

    @GetMapping("/user-permission/get-by-jpa/{userId}")
    public List<PermissionDto> getUserPermissionByJPA(@PathVariable Long userId) {
        return permissionService.getUserPermissionsThroughJPA(userId);
    }

    @GetMapping("/user-permission/get-by-native-query/{userId}")
    public List<PermissionDto> getUserPermissionByNativeQuery(@PathVariable Long userId) {
        return permissionService.getUserPermissionsThroughNativeQuery(userId);
    }
}
