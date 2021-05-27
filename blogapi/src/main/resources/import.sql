--this script initiates db for h2 db (used in test profile)
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'john@domain.com', 'John', 'Steward')
insert into user (account_status, email, first_name) values ('NEW', 'brian@domain.com', 'Brian')

insert into user (id, account_status, email, first_name) values (9,'CONFIRMED', 'author@domain.com', 'Brian')
insert into user (id, account_status, email, first_name) values (10,'CONFIRMED', 'liking@domain.com', 'Brian')
insert into blog_post (id, entry, user_id) values (1, 'przyklad', 9)
insert into like_post (id, post_id, user_id) values (1, 1, 10)


insert into user (id, account_status, email, first_name) values (11,'REMOVED', 'delted@domain.com', 'SAS')
insert into blog_post (id, entry, user_id) values (2, 'usuniety', 11)

insert into user (id, account_status, email, first_name) values (13,'CONFIRMED', 'newauthor@domain.com', 'Adi')
insert into user (id, account_status, email, first_name) values (12,'CONFIRMED', 'git@domain.com', 'Mati')
insert into blog_post (id, entry, user_id) values (3, 'post z 3 lajkami', 13)
insert into blog_post (id, entry, user_id) values (4, 'post 2', 13)

insert into like_post (id, post_id, user_id) values (2, 3, 9)
insert into like_post (id, post_id, user_id) values (3, 3, 10)
insert into like_post (id, post_id, user_id) values (4, 3, 12)