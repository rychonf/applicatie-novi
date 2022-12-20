INSERT INTO users (username, password, enabled, type_of_subscription, email)
VALUES ('rych', '$2a$09$xPHYNdG7Gf5bO1XfkX/aruqu/zpIF4XG875s2CNNgoRcHf7e97bkG', true, 'a', 'rf@hotmail.com'),
('henk', '$2a$12$pbHsDSreoLnqf0QTzl4qkuAif3Y2lx7WeErvlOCvzGDXVb9.ic5hy', true,'y', 'djhenk@hotmail.com'),
('tim', '$2a$12$g5xAiVMJlYhiNzFre/ybSOWOjN1sshhraUWdim7NHej.tSOeDL736', true, 'y', 'harderstyles@hotmail.com');

INSERT INTO authorities (id, username, authority)
VALUES (30010, 'rych', 'ROLE_ADMIN'),
        (30011, 'henk', 'ROLE_DJ'),
        (30012, 'tim', 'ROLE_ORGANISATION');

INSERT INTO organisation (id, name, organized_events)
VALUES (1002, 'Thunder Dome', 512);

INSERT INTO dj (id, dj_name, music_specialty, price_per_hour)
VALUES (1002, 'Deadly Guns', 'Hardcore, Raw-style' , 100.00);

INSERT INTO message (id, message, final_date)
VALUES (1002, 'I would like to hire Deadly Guns for 1 hour on 2022-12-31', '2022-12-31');

/*
    Bearer Tokens:
    rych (admin):
    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyeWNoIiwiZXhwIjoxNjY5NTU3MDU0LCJpYXQiOjE2NjgwODU4MjV9.EFWjPfWgx7yraL66qQvvGv_Ydo_vQWCXZLvxGI2VOyU

    henk (dj):
    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZW5rIiwiZXhwIjoxNjY5NTU2ODc4LCJpYXQiOjE2NjgwODU2NDl9.p4NQxZQSq84eYs_ysXV1kEvAC1mQeSDx_bxOVvK9AT8

    tim (organisation):
    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aW0iLCJleHAiOjE2Njk1NTc5NzAsImlhdCI6MTY2ODA4Njc0MX0.NWZhtUc-Ta7dVKCdwk1XM3aV4ZWcCUaDVsOj4xPTWMM

    */

