create schema if not exists app1;
create table app1.dummies (
  id            bigint not null       auto_increment           ,
  created       timestamp not null    default current_timestamp,
  updated       timestamp not null    default current_timestamp,
  disabled      smallint not null     default 0                ,
  title         varchar(256) not null                          ,
  note          varchar(256),                                   
  primary key(id)
);
