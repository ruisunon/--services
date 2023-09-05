package ai.sxr.shoppingla.auth.repository;

import ai.sxr.shoppingla.auth.model.RolePermission.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(value = """
            select distinct p
                from Permission p
            left join RolePermission rp on p.id = rp.permission.id
            left join UserRole ur on rp.role = ur.role
            where ur.active=true and rp.active=true
            and ur.user.id = :userId
            """)
    List<Permission> findAllPermissionByUserId(Long userId);
}
