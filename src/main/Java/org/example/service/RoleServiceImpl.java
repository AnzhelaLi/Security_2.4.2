package org.example.service;

import org.example.dao.RoleDao;
import org.example.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    @Override
    public Role updateRole(Role updatedRole) {
        return roleDao.updateRole(updatedRole);
    }

    @Override
    public Role findByRole(String role) {
        return roleDao.findByRole(role);
    }
}
