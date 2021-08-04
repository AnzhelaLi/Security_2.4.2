package org.example.service;

import org.example.model.Role;


public interface RoleService {

    Role saveRole(Role role);

    Role updateRole(Role updatedRole);

    Role findByRole(String role);
}
