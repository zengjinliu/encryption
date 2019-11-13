-- Create table
create table TEST_BATCH
(
  seq_id      NUMBER(19) not null,
  name        VARCHAR2(128),
  age         NUMBER(10),
  create_date TIMESTAMP(6)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns
comment on column TEST_BATCH.seq_id
  is '主键';
comment on column TEST_BATCH.name
  is '名字';
comment on column TEST_BATCH.age
  is '年龄';
comment on column TEST_BATCH.create_date
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints
alter table TEST_BATCH
  add constraint PK_TEST_BATCH primary key (SEQ_ID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );



-- Create table
create table PERSON_INFO
(
  seq_id      NUMBER(10) not null,
  user_id     VARCHAR2(64) not null,
  person_id   NUMBER(10),
  user_name   VARCHAR2(50),
  phone       VARCHAR2(128),
  weixin_id   VARCHAR2(128),
  position    VARCHAR2(128),
  avatar_url  VARCHAR2(500),
  create_date TIMESTAMP(6)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate indexes
create unique index PER_PERSON_ID_INDEX on PERSON_INFO (PERSON_ID)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints
alter table PERSON_INFO
  add primary key (SEQ_ID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );




-- Create table
create table PERSON
(
  seq_id        NUMBER(10) not null,
  user_id       VARCHAR2(64) not null,
  pw            VARCHAR2(500),
  org_id        NUMBER(10),
  dep_id        NUMBER(10),
  role_id       NUMBER(10),
  role_name     VARCHAR2(500),
  assist_role   VARCHAR2(1000),
  register_date TIMESTAMP(6),
  person_state  NUMBER(1),
  is_reserved   NUMBER(1),
  unionid       VARCHAR2(500),
  login_type    VARCHAR2(100)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate indexes
create index PERSON_DEP__INDEX on PERSON (DEP_ID)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index PERSON_ORG_DEP_INDEX on PERSON (ORG_ID, DEP_ID)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index PERSON_REG_DATE_INDEX on PERSON (REGISTER_DATE)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index PERSON_STATE_INDEX on PERSON (PERSON_STATE)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create unique index PERSON_UNIONID_INDEX on PERSON (UNIONID)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index PERSON_USER_ID_INDEX on PERSON (USER_ID)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints
alter table PERSON
  add primary key (SEQ_ID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
