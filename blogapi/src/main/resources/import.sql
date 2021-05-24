--this script initiates db for h2 db (used in test profile)
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'john@domain.com', 'John', 'Steward')
insert into user (account_status, email, first_name) values ('NEW', 'brian@domain.com', 'Brian')

insert into user (id, account_status, email, first_name) values (9,'CONFIRMED', 'author@domain.com', 'Brian')
insert into user (id, account_status, email, first_name) values (10,'CONFIRMED', 'liking@domain.com', 'Brian')
insert into blog_post (id, entry, user_id) values (1, 'przyklad', 9)
insert into like_post (id, post_id, user_id) values (1, 1, 10)