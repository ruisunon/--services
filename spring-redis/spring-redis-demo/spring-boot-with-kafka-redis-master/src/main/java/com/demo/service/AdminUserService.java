package com.demo.service;

import com.demo.model.AdminUser;
import com.demo.model.Jobs;
import com.demo.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminUserService {
    private final String ADMINUSER_CACHE = "EMPLOYER";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, AdminUser> hashOperations;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    // Save operation.
    public void save(final AdminUser adminUser) {
        adminUserRepository.save(adminUser);
    }

    public void saveToCache(final AdminUser adminUser) {
        hashOperations.put(ADMINUSER_CACHE, adminUser.getAdmin_id().toString(), adminUser);
    }

    public AdminUser getAdminUser(Long id) {
        AdminUser adminUser = null;
        try {
            adminUser = this.adminUserRepository.findById(id)
                    .orElseThrow(() -> new Exception("AdminUser Data not found for this id :: " + id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminUser;
    }

    // Find by data id operation.
    public AdminUser findById(final String id) {
        AdminUser adminUser = (AdminUser) hashOperations.get(ADMINUSER_CACHE, id);
        if (adminUser!=null) return adminUser;
        else {
            AdminUser adminUser2 = this.getAdminUser(Long.valueOf(id));
            saveToCache(adminUser2);
            return adminUser2;
        }
    }

    // Find all data operation.
    public Map<String, AdminUser> findAll() {
        List<AdminUser> adminUsers = adminUserRepository.findAll();
        adminUsers.forEach((adminUsers1 -> hashOperations.put(ADMINUSER_CACHE, adminUsers1.getAdmin_id().toString(), adminUsers1)));
        return hashOperations.entries(ADMINUSER_CACHE);
    }

//    public void delete(String id) {
//        hashOperations.delete(ADMINUSER_CACHE, id);
//    }

    public AdminUser delete(String id){
        hashOperations.delete(ADMINUSER_CACHE, id);
        AdminUser adminUser = getAdminUser(Long.parseLong(id));
        this.adminUserRepository.delete(adminUser);
        return adminUser;
    }

    public AdminUser updateCategory(Long id, AdminUser adminUser){
        AdminUser adminUserEntity = null;
        try {
            adminUserEntity = this.adminUserRepository.findById(id)
                    .orElseThrow(() -> new Exception("AdminUser not found for this id :: " + id));
            adminUserEntity.setPassword(adminUser.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminUserEntity;
    }
}
