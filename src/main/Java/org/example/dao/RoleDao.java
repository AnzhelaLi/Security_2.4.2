package org.example.dao;

import org.example.model.Role;


public interface RoleDao {

    Role findByRole(String role);

    Role saveRole(Role role);

    Role updateRole(Role updatedRole);
}
