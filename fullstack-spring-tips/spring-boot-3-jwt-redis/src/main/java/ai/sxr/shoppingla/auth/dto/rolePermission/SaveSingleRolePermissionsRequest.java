package ai.sxr.shoppingla.auth.dto.rolePermission;

import java.util.List;

public record SaveSingleRolePermissionsRequest (Long roleId, List<PermissionDto> permissionList) {
}
