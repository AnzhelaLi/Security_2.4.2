package org.example.service;

import org.example.dao.RoleDao;
import org.example.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role saveRole(Role role) {
        return roleDao.saveRole(role);
    }

    //метод фильтрует null-значения с checkbox, затем по имени роли находит роль(сущность),
    //обновляет и добавляет в Set<Role> rolesForUpdate
    @Override
    public Set<Role> rolesFromCheckbox(String[] listOfRolesNameFromCheckbox) {
        Set<Role> rolesForUpdate = new HashSet<>();
        for (int r = 0; r < listOfRolesNameFromCheckbox.length; r++) {
            if (listOfRolesNameFromCheckbox[r] != null) {
                rolesForUpdate.add(roleDao.updateRole(roleDao.findRoleByRoleName(listOfRolesNameFromCheckbox[r])));
            }
        }
        return rolesForUpdate;
    }

    @Override
    public Role findRoleByRoleName(String role) {
        return roleDao.findRoleByRoleName(role);
    }

    @Override
    public List<Role> allRoles() {
        return roleDao.allRoles();
    }

    @Override
    public List<Role> findRoleByUsername(String username) {
        return roleDao.findRoleByUsername(username);
    }
}
