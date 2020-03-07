INSERT INTO t_user (USR_ID, USR_ADRESS, USR_BIRTHDATE, USR_EMAIL, USR_LASTNAME, USR_FIRSTNAME, USR_LOGIN,USR_PASSWORD, USR_PHONE)
VALUES
(1, 'sfax', '1999-01-01 00:00:00', 'bilel.jallouli@sifast.com',  'admin', 'admin','superadmin', '$2a$10$DWl1srWXMn8dyI3ieh4Z5.vlsD7QqavULLtLDKVzRRy/CavYv9TxO', '52351651'),
(2, 'sfax', '1999-01-01 00:00:00', 'ahmed.dammak.stg@sifast.com',  'admin', 'admin','admin', '$2a$10$AIUufK8g6EFhBcumRRV2L.AQNz3Bjp7oDQVFiO5JJMBFZQ6x2/R/2', '22970552'),
(3, 'sfax', '1999-01-01 00:00:00', 'guest@sifast.com','guest', 'guest','guest', '$2a$10$AIUufK8g6EFhBcumRRV2L.AQNz3Bjp7oDQVFiO5JJMBFZQ6x2/R/2', '22970552');

INSERT INTO t_role (ROL_ID, ROL_DESIGNATION,ROL_DESCRIPTION) VALUES
(1, 'ROLE_SUPER_ADMIN','all authorities for this role'),
(2, 'ROLE_ADMIN','random role without generation report'),
(3, 'ROLE_GUEST','limited role for guest user ');

INSERT INTO t_authority (AUTH_ID, AUTH_DESIGNATION, AUTH_DESCRIPTION) VALUES
(1, 'AUTH_ROLE_CREATE','create role authority'),
(2, 'AUTH_ROLE_UPDATE','update role authority'),
(3, 'AUTH_ROLE_DELETE','delete role authority'),
(4, 'AUTH_USER_CREATE','create user authority'),
(5, 'AUTH_USER_UPDATE','upade user authority'),
(6, 'AUTH_USER_DELETE','delete user authority'),
(7, 'AUTH_GENERATE_REPORT_EXEC','generate report authority'),
(8, 'AUTH_SWAGGER_EXEC','swagger documentation authority'),
(9, 'AUTH_EMAIL_SETTING_UPDATE','email setting authority'),
(10, 'AUTH_ROLE_READ','Read role management'),
(11, 'AUTH_USER_READ','Read user management'),
(12, 'AUTH_ACTIONS_TRACK_READ','Read actions is tracking'),
(13, 'AUTH_LOGIN_LOGOUT_TRACK_READ','Read login logout is tracking'),
(14, 'AUTH_AUTHORITY_READ','Read auhority management'),
(15, 'AUTH_AUTHORITY_CREATE','create auhority'),
(16, 'AUTH_AUTHORITY_UPDATE','update auhority'),
(17, 'AUTH_AUTHORITY_DELETE','delete auhority'),
(18, 'AUTH_LOGGER_MANAGE','log management auhority'),
(19, 'AUTH_AUTHORITY_DELETE_DATABASE','delete database');

INSERT INTO tj_user_role (USR_ID,ROL_ID) VALUES
(1, 1),(2, 2),(3,3);

INSERT INTO tj_auth_role (ROL_ID, AUTH_ID) VALUES
(1, 1),(1, 2),(1, 3),(1, 4),(1, 5),(1, 6),(1, 7),(1, 8),(1, 9),(1, 10),(1, 11),(1, 12),(1,18),(1,19), 
(2, 1),(2, 2),(2, 3),(2, 4),(2, 5),(2, 6),(2, 7),(2, 8),(2, 9),(2, 10),(2, 11),(2, 12),(2,18),(2,19);