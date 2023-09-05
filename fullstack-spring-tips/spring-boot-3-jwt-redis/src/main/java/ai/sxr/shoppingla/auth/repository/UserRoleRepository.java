package ai.sxr.shoppingla.auth.repository;

import ai.sxr.shoppingla.auth.model.RolePermission.Role;
import ai.sxr.shoppingla.auth.model.RolePermission.UserRole;
import ai.sxr.shoppingla.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    public Set<UserRole> findAllByUserAndActive(User user, boolean active);
    public UserRole findByUserAndRole(User user, Role role);
}
