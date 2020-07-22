-- Default schema is APP
SET SCHEMA U;

-- Entities Tables
CREATE TABLE APP_USER
(
    ID        INTEGER,
    EMAIL VARCHAR(100),
    PASSWORD  VARCHAR(100),
    ACTIVE    BOOLEAN,
    FIRST_NAME  VARCHAR(100),
    LAST_NAME  VARCHAR(100),
    CONSTRAINT PK__USER__ID PRIMARY KEY (ID)
);

CREATE TABLE APP_PROFILE
(
    ID           INTEGER,
    PROFILE_NAME VARCHAR(100),
    CONSTRAINT PK__PROFILE__ID PRIMARY KEY (ID)
);

CREATE TABLE APP_PRIVILEGE
(
    ID        INTEGER,
    PRIVILEGE VARCHAR(100),
    CONSTRAINT PK__PRIVILEGE__ID PRIMARY KEY (ID)
);

-- Join Tables
CREATE TABLE APP_USER_PROFILE
(
    USER_ID    INTEGER NOT NULL
        CONSTRAINT FK__USER_PROFILE__USER REFERENCES APP_USER (ID) ON DELETE CASCADE,
    PROFILE_ID INTEGER NOT NULL
        CONSTRAINT FK__USER_PROFILE__PROFILE REFERENCES APP_PROFILE (ID) ON DELETE CASCADE,
    CONSTRAINT PK__USER_PROFILE__USER_ID__PROFILE_ID PRIMARY KEY (USER_ID, PROFILE_ID)
);

CREATE TABLE APP_PROFILE_PRIVILEGE
(
    PROFILE_ID INTEGER NOT NULL
        CONSTRAINT FK__PROFILE_PRIVILEGE__PROFILE REFERENCES APP_PROFILE (ID) ON DELETE CASCADE,
    OP_ID      INTEGER NOT NULL
        CONSTRAINT FK__PROFILE_PRIVILEGE__PRIVILEGE REFERENCES APP_PRIVILEGE (ID) ON DELETE CASCADE,
    CONSTRAINT PK__PROFILE_PRIVILEGE__PROFILE_ID__OP_ID PRIMARY KEY (PROFILE_ID, OP_ID)
);



-- Sequence Tables
CREATE TABLE APP_SEQ_GENERATOR
(
    SEQ_NAME  VARCHAR(100) NOT NULL,
    SEQ_VALUE INTEGER      NOT NULL
);

-- Must have data
INSERT INTO APP_SEQ_GENERATOR
VALUES ('PROFILE_SEQ_PK', 1),
       ('USER_SEQ_PK', 1);
