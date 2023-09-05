package ai.sxr.shoppingla.auth.dto.rolePermission;

import java.util.List;

public record SaveSingleUserRolesRequest(Long userId, List<RoleDto> roleList) {
}
