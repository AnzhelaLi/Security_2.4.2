package org.example.model;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.Collection;

import java.util.HashSet;
import java.util.Set;
// Для того, чтобы в дальнейшим использовать класс User в Spring Security, он должен реализовывать интерфейс UserDetails.
// UserDetails можно представить, как адаптер между БД пользователей и тем что требуется Spring Security внутри SecurityContextHolder


@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 20, message = "Name should be min 2 max 20 characters")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 20, message = "Surname should be min 2 max 20 characters")
    @Column(name = "surname")
    private String surname;

    @Column(name = "workplace")
    private String workplace;

    @Min(value = 10, message = "Age should be min 10")
    @Column(name = "age")
    private int age;

    @Column(name = "salary")
    private int salary;

    @NotEmpty(message = "Username should not be empty")
    @Size(min = 4, max = 8, message = "Username should be min 4 max 8 characters")
    @Column(name = "username", unique = true)
    private String username; // уникальное значение

    @NotEmpty(message = "Password should not be empty")
    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String name, String surname, String workplace, int age, int salary, String password,
                String username, Set<Role> roles) {

        this.name = name;
        this.surname = surname;
        this.workplace = workplace;
        this.age = age;
        this.salary = salary;
        this.password = password;
        this.username = username;
        this.roles = roles;
    }

    public User(String name, String surname, String workplace, int age, int salary, String username, String password) {

        this.name = name;
        this.surname = surname;
        this.workplace = workplace;
        this.age = age;
        this.salary = salary;
        this.username = username;
        this.password = password;

    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);

    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
