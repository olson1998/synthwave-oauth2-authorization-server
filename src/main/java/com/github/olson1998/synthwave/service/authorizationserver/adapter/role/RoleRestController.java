package com.github.olson1998.synthwave.service.authorizationserver.adapter.role;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role.Role;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteRoleResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.rest.stereotype.DeleteUserRoleBindingResponse;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/role")
public class RoleRestController {

    private final RoleRepository roleRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/save", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public Collection<? extends Role> saveRoles(@RequestBody Collection<Role> roleCollection) {
        return roleRepository.saveAll(roleCollection);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/binding/user/{userId}/save", consumes = APPLICATION_JSON_VALUE)
    public void saveRoleBounds(@PathVariable("userId") Long userId, @RequestBody Collection<Role> roleCollection) {
        roleRepository.saveUserRoles(roleCollection, userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/delete", produces = APPLICATION_JSON_VALUE)
    public DeleteRoleResponse deleteRoles(@RequestParam("RLID") Collection<Long> idCollection) {
        return roleRepository.deleteRoles(idCollection);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "/binding/user/{userId}/delete", consumes = APPLICATION_JSON_VALUE)
    public DeleteUserRoleBindingResponse deleteUserRoles(@PathVariable("userId") Long userId, @RequestParam("RLID") Collection<Long> idCollection) {
        return roleRepository.deleteUserRoleBounds(userId, idCollection);
    }

}
