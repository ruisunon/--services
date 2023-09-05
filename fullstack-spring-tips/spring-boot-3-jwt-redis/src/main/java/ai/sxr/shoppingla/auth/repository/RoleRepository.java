package ai.sxr.shoppingla.auth.repository;

import ai.sxr.shoppingla.auth.model.RolePermission.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
