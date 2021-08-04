insert into users (name, surname, workplace, age, salary, username, password)
values ('Ivan', 'Ivanov', 'fortuna', 50, 120000, 'ivi50', 'passw');
insert into users (name, surname, workplace, age, salary, username, password)
values ('John', 'Johnson', 'autopark', 33, 65000, 'JJ33', 'parol');
insert into roles (role) values ('ADMIN');
insert into roles (role) values ('USER');
insert into user_role (user_id, role_id) select user_id, role_id from users user,

                                                                      roles roles where user.username = 'JJ33' and roles.role = 'USER';
insert into user_role (user_id, role_id) select user_id, role_id from users user,

                                                                      roles roles where user.username = 'ivi50' and roles.role = 'ADMIN';
insert into user_role (user_id, role_id) select user_id, role_id from users user,

                                                                      roles roles where user.username = 'ivi50' and roles.role = 'USER';