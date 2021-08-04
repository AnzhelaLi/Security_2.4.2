package org.example.dao;

import org.example.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
        entityManager.merge(updatedRole);
        return updatedRole;
    }

    @Override
    public Role findByRole(String role) {
        try {
            return entityManager.
                    createQuery("from Role u where u.role = :role", Role.class).
                    setParameter("role", role).getSingleResult();
        } catch (NoResultException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
