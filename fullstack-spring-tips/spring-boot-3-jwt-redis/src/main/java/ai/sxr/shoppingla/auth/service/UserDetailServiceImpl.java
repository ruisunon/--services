package ai.sxr.shoppingla.auth.service;

import ai.sxr.shoppingla.auth.repository.UserRepository;
import ai.sxr.shoppingla.auth.dto.UserDto;
import ai.sxr.shoppingla.auth.dto.custom.AuthorityDto;
import ai.sxr.shoppingla.auth.dto.custom.JwtUserDto;
import ai.sxr.shoppingla.auth.dto.rolePermission.PermissionDto;
import ai.sxr.shoppingla.auth.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService, GroupManager {

    private final UserRepository userRepository;
    private final PermissionService permissionService;

    @Override
    public JwtUserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username).get();
            if (user == null) {
                throw new UsernameNotFoundException("");
            } else {
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(user, userDto);
                List<AuthorityDto> authorityDtoList = new ArrayList<>();
                List<PermissionDto> permissionDtoList = permissionService.getUserPermissionsThroughJPA(user.getId());

//                for (UserRole dto:
//                     user.getUserRoles()) {
//                    String userRole = dto.getRole().getName();
//                    authorityDtoList.add(new AuthorityDto(userRole));
//                }

                for (PermissionDto permissionDto:
                     permissionDtoList) {
                    AuthorityDto dto = new AuthorityDto(permissionDto.getName());
                    authorityDtoList.add(dto);
                }
                JwtUserDto jwtUserDto = new JwtUserDto(
                        userDto,
                        authorityDtoList
                );
                return jwtUserDto;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<String> findAllGroups() {
        return null;
    }

    @Override
    public List<String> findUsersInGroup(String groupName) {
        return null;
    }

    @Override
    public void createGroup(String groupName, List<GrantedAuthority> authorities) {

    }

    @Override
    public void deleteGroup(String groupName) {

    }

    @Override
    public void renameGroup(String oldName, String newName) {

    }

    @Override
    public void addUserToGroup(String username, String group) {

    }

    @Override
    public void removeUserFromGroup(String username, String groupName) {

    }

    @Override
    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
        return null;
    }

    @Override
    public void addGroupAuthority(String groupName, GrantedAuthority authority) {

    }

    @Override
    public void removeGroupAuthority(String groupName, GrantedAuthority authority) {

    }
}
