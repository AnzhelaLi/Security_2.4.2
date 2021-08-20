package org.example.service;

import org.example.dao.RoleDao;
import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.model.Role;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserDao userDao = new UserDaoImpl();
    private RoleDao roleDao;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    public UserServiceImpl() {

    }

    @Transactional
    @Override
    public User justSaveUser(User user) {  //метод для addInitData(), который заполняет базу данных при
        return userDao.justSaveUser(user); //для метода Init - при старте приложения
    }

    @Override
    public User registerUser(User user, Set<Role> rolesFromCheckbox) { //метод для сохранения новых юзеров
        User userFromDb = userDao.getUserByName(user.getUsername());
        //Role role = roleDao.findRoleByRoleName("ROLE_USER");

        if (userFromDb == null) {
           // user.setRoles(new HashSet<>(Arrays.asList(role)));
            user.setRoles(rolesFromCheckbox);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.justSaveUser(user);
        } else {
            try {
                throw new Exception("User already exists");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Transactional
    @Override
    public User updateUser(User user, Set<Role> roles) { //метод для редактирования user-данных

        //user.setRoles(new HashSet<>(Arrays.asList(roleDao.findByRole("ROLE_USER"))));
        user.setRoles(roles); //роли должны быть после merge(updateRole)
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.updateUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> usersList() {
        return userDao.usersList();
    }

    @Transactional
    @Override
    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                grantedAuthorities);
    }

    @Override
    @Transactional
    public void addInitData() { //заполнение базы данных при запуске приложения, @PostConstruct
        //добавление пользователей - admin, user
        User admin = new User("Ivan", "Ivanov", "Kinopark", 33, 64000, "ivan", passwordEncoder.encode("password"));
        User user = new User("Ivana", "Ivanova", "Kinopark", 36, 60000, "ivanka", passwordEncoder.encode("kinopark123"));

        userDao.justSaveUser(admin);
        userDao.justSaveUser(user);
        //добавление ролей
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        roleDao.saveRole(roleAdmin);
        roleDao.saveRole(roleUser);
        //для того, чтобы заполнить joinTable "users_roles (назначить роли пользователям)
        admin.addRole(roleAdmin);
        admin.addRole(roleUser);
        user.addRole(roleUser);

        roleAdmin.addUser(admin);
        roleUser.addUser(admin);
        roleUser.addUser(user);
    }
}
