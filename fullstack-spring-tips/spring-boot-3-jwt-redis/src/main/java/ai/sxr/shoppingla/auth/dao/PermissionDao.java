package ai.sxr.shoppingla.auth.dao;

import ai.sxr.shoppingla.auth.dto.rolePermission.PermissionDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PermissionDao {
    private final EntityManager em;

    public List<PermissionDto> getUserPermissionsThroughNativeQuery(Long userId) {
        String whereClause = " WHERE 1=1 AND ur.active=true and rp.active=true";
        whereClause += " AND ur.user_id='"+userId+"'";

        String nativeQuery = "select distinct p.id, p.active, p.name, p.path, p.sort_order_id," +
                " p.parent_id, p.title from permission p " +
                "left join role_permission rp on p.id = rp.permission_id " +
                "left join user_role ur on rp.role_id = ur.role_id ";
        nativeQuery += whereClause;
        Query query = em.createNativeQuery(nativeQuery);
        List<Object[]> list = query.getResultList();
        List<PermissionDto> permissionList = new ArrayList<>();
        for (Object[] obj: list) {
            PermissionDto dto = new PermissionDto();
            dto.setId((Long) obj[0]);
            dto.setActive((Boolean) obj[1]);
            dto.setName((String) obj[2]);
            dto.setPath((String) obj[3]);
            dto.setSortOrderId((Integer) obj[4]);
            dto.setParentId((Long) obj[5]);
            dto.setTitle((String) obj[6]);

            permissionList.add(dto);
        }
        return permissionList;
    }
}
