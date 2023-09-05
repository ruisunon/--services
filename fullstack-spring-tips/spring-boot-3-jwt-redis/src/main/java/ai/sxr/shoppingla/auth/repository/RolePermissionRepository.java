package ai.sxr.shoppingla.auth.repository;

import ai.sxr.shoppingla.auth.model.RolePermission.Permission;
import ai.sxr.shoppingla.auth.model.RolePermission.Role;
import ai.sxr.shoppingla.auth.model.RolePermission.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    public Set<RolePermission> findAllByRoleAndActive(Role role, boolean active);
    public RolePermission findByRoleAndPermission(Role role, Permission permission);
}
