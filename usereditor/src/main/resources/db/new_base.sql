CREATE TABLE users
(
   user_id integer, 
   email character varying(40), 
   timezone character varying(20), 
   create_date timestamp with time zone,
   update_date timestamp with time zone,
   name character varying(40), 
   password character varying(40), 
    PRIMARY KEY (user_id), 
    UNIQUE (email)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE users OWNER TO test;


CREATE TABLE user_roles
(
   role_id integer, 
   role character varying(40), 
   user_id integer, 
    PRIMARY KEY (role_id), 
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE NO ACTION ON DELETE CASCADE
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE user_roles OWNER TO test;

insert into users values(1, 'editor@mail.ru', 'Europe/Moscow', now(), now(), 'editor', 'Ed1tor');
insert into user_roles values(1, 'ROLE_EDITOR', 1);
insert into user_roles values(2, 'ROLE_USER', 1);

insert into users values(2, 'user@mail.ru', 'Europe/Moscow', now(), now(), 'user', 'User0k');
insert into user_roles values(3, 'ROLE_USER', 2);