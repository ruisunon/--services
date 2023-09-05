package com.demo.controllers;

import com.demo.model.AdminUser;
import com.demo.service.AdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/redis/adminUser")
public class AdminUserController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private AdminUserService adminUserService;

    // Url - http://localhost:9000/api/redis/adminUser
    @PostMapping
    @PreAuthorize("hasAuthority('read:admin-messages')")
    public String save(@RequestBody final AdminUser adminUser) {
        LOG.info("Saving the new adminUser data to the redis.");
        adminUserService.save(adminUser);
        return "Successfully added. AdminUser data with id= " + adminUser.getAdmin_id();
    }

    // Url - http://localhost:9000/api/redis/adminUser/getall
    @GetMapping("/getall")
    @PreAuthorize("hasAuthority('read:admin-messages')")
    public Map<String, AdminUser> findAll() {
        LOG.info("Fetching all adminUser data from the redis.");
        final Map<String, AdminUser> AdminUserMap = adminUserService.findAll();
        // Todo - If developers like they can sort the map (optional).
        return AdminUserMap;
    }

    // Url - http://localhost:9000/api/redis/admiUser/get/<admiUser_id>
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('read:admin-messages')")
    public AdminUser findById(@PathVariable("id") final String id) {
        LOG.info("Fetching adminUser with id= " + id);
        return adminUserService.findById(id);
    }

    // Url - http://localhost:9000/api/redis/admiUser/<adminUser_id>
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('read:admin-messages')")
    public Map<String, AdminUser> delete(@PathVariable("id") final String id) {
        adminUserService.delete(id);
        // Returning the all employee data (post the deleted one).
        return findAll();
    }

    // Url - http://localhost:9000/api/redis/admiUser/put/<adminUser_id>
    @PutMapping("/put/{id}")
    @PreAuthorize("hasAuthority('read:admin-messages')")
    public void updateProduct(@PathVariable(value = "id") Long id, @RequestBody AdminUser adminUser){
        adminUserService.updateCategory(id, adminUser);
    }
}
