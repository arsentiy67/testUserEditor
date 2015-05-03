CREATE SEQUENCE users_id_seq;
ALTER TABLE users_id_seq OWNER TO test;
CREATE SEQUENCE user_roles_id_seq;
ALTER TABLE user_roles_id_seq OWNER TO test;
CREATE SEQUENCE user_address_id_seq;
ALTER TABLE user_address_id_seq OWNER TO test;

CREATE TABLE users
(
   user_id integer, 
   email character varying(40), 
   timezone character varying(20), 
   create_date timestamp with time zone,
   update_date timestamp with time zone,
   name character varying(40), 
   password character varying(60), 
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
   user_role_id integer, 
   user_id integer,
   role character varying(40), 
    PRIMARY KEY (user_role_id), 
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE NO ACTION ON DELETE CASCADE
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE user_roles OWNER TO test;

CREATE TABLE user_address
(
  user_address_id integer NOT NULL,
  user_id integer,
  country character varying(40),
  city character varying(40), 
  CONSTRAINT user_address_pkey PRIMARY KEY (user_address_id ),
  CONSTRAINT user_address_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES users (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_address OWNER TO test;

--password: Ed1tor
insert into users values(nextval('users_id_seq'), 'editor@mail.ru', 'Europe/Moscow', now(), now(), 'editor', '$2a$10$.yTOb7MAR5KhSLoT1ZD1kO.6v.fRBxWsqELbcpmnjdL3SAynm87Rm');
insert into user_roles values(nextval('user_roles_id_seq'), 1, 'ROLE_EDITOR');
insert into user_roles values(nextval('user_roles_id_seq'), 1, 'ROLE_USER');
insert into user_address values(nextval('user_address_id_seq'), 1, 'Russia', 'St. Petersburg');


--password: User0k
insert into users values(nextval('users_id_seq'), 'user@mail.ru', 'Europe/Athens', now(), now(), 'user', '$2a$10$fAZXjksgvpspbMyqlz3S3.C7wn9L.Ow8eN1XQ9XnZVYOO1iu/1RTO');
insert into user_roles values(nextval('user_roles_id_seq'), 2, 'ROLE_USER');
insert into user_address values(nextval('user_address_id_seq'), 2, 'Greece', 'Athens');