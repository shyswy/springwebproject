drop table if exists member CASCADE;
create table member
(
 id bigint generated by default as identity,   //자동으로 아이디값 채워줌
 name varchar(255),
 primary key (id)
);