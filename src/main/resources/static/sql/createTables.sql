CREATE TABLE CHESS_USERS (
    ID NUMBER(10) PRIMARY KEY,
    LOGIN VARCHAR2(50) NOT NULL,
    PASSWORD VARCHAR2(50) NOT NULL,
    SEX VARCHAR2(10) CHECK (SEX IN('male','female')) NOT NULL,
    WINS NUMBER(10),
    DEFEATS NUMBER(10),
    DRAWS NUMBER(10)
);
CREATE TABLE GAME_SESSIONS (
  ID NUMBER(10) PRIMARY KEY,
  ID_1 NUMBER(10) REFERENCES CHESS_USERS(ID),
  ID_2 NUMBER(10) REFERENCES CHESS_USERS(ID),
  RESULT NUMBER(1) CHECK (RESULT >= 0 AND RESULT <=2),
  LOGS VARCHAR2(4000)
);
CREATE SEQUENCE CHESS_USERS_S NOCACHE ORDER;
CREATE OR REPLACE TRIGGER CHESS_USERS_T
 BEFORE INSERT ON CHESS_USERS
 FOR EACH ROW
 BEGIN
 SELECT CHESS_USERS_S.NEXTVAL
 INTO :new.id FROM DUAL;
 END; 
/ 
CREATE SEQUENCE GAME_SESSIONS_S NOCACHE ORDER;
CREATE OR REPLACE TRIGGER GAME_SESSIONS_T
 BEFORE INSERT ON GAME_SESSIONS
 FOR EACH ROW
 BEGIN
 SELECT GAME_SESSIONS_S.NEXTVAL
 INTO :new.id FROM DUAL;
 END; 
/ 