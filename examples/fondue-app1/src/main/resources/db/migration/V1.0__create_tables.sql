create schema if not exists app1;
create table app1.books (
  id           bigint auto_increment                           , -- ID
  created      timestamp not null     default current_timestamp, -- Created timestamp
  updated      timestamp not null     default current_timestamp, -- Updated timestamp
  disabled     smallint               default 0                , -- Disabled
  title        varchar(256) not null                           , -- Title
  author       varchar(256)                                    , -- Author
  book_number  varchar(32)                                     , -- BookNumber
  summary      varchar(1024)                                   , -- Summary
  note         varchar(1024)                                   , -- Note
  primary key(id)
);
create table app1.remarks (
  id           bigint auto_increment                           , -- ID
  created      timestamp not null     default current_timestamp, -- Created timestamp
  updated      timestamp not null     default current_timestamp, -- Updated timestamp
  disabled     smallint               default 0                , -- Disabled
  user_id      varchar(256) not null                           , -- UserId
  user_name    varchar(256)                                    , -- UserName
  remark       varchar(1024)                                   , -- Remark
  primary key(id)
);
