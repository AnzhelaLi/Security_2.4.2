package org.example.dao;

import org.example.model.Role;
import org.example.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role saveRole(Role role) {
        entityManager.persist(role);
        return role;
    }

    @Transactional
    @Override
    public Role updateRole(Role updatedRole) {

        return entityManager.merge(updatedRole);
    }

    @Override
    public Role findRoleByRoleName(String role) {
        try {
            return entityManager.
                    createQuery("from Role u where u.role = :role", Role.class).
                    setParameter("role", role).getSingleResult();
        } catch (NoResultException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Role> findRoleByUsername(String username) {
        try {
            return entityManager.
                    createQuery("from Role as role INNER JOIN fetch User as user WHERE user.username=:username", Role.class).
                    setParameter("username", username).getResultList();
        } catch (NoResultException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Role> allRoles() {
        try {
            return entityManager.createQuery("from Role", Role.class)
                    .getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteRole(String role) {
        entityManager.remove(findRoleByRoleName(role));
        entityManager.flush();
        entityManager.clear();
    }
}
