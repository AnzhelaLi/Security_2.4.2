package org.example.service;

import org.example.model.Role;

import java.util.List;
import java.util.Set;


public interface RoleService {

    Role saveRole(Role role);

    Set <Role> rolesFromCheckbox(String[] listOfRolesNameFromCheckbox);

    Role findRoleByRoleName(String role);

    List<Role> allRoles();

    List<Role> findRoleByUsername(String username);

}
