CREATE TABLE ADDRESS (
	ID VARCHAR(255) NOT NULL,
	DATECREATED TIMESTAMP,
	DATEMODIFIED TIMESTAMP,
	CITY VARCHAR(255),
	NEIGHBOURHOOD VARCHAR(255),
	POSTALCODE VARCHAR(255),
	STREETNAME VARCHAR(255),
	STREETNUMBER VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE CHILD_GROUP (
	ID VARCHAR(255) NOT NULL,
	DATECREATED TIMESTAMP,
	DATEMODIFIED TIMESTAMP,
	MEMBERS INTEGER,
	CLASSROOM_ID VARCHAR(255) NOT NULL,
	TERM_ID VARCHAR(255) NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE CHILD_GUARDIAN (
	ID VARCHAR(255) NOT NULL,
	DATECREATED TIMESTAMP,
	DATEMODIFIED TIMESTAMP,
	CHILD_ID VARCHAR(255),
	GUARDIAN_ID VARCHAR(255),
	NOTES VARCHAR(255),
	PICKUP BOOLEAN NOT NULL,
	TYPE VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE CLASSROOM (
	ID VARCHAR(255) NOT NULL,
	DATECREATED TIMESTAMP,
	DATEMODIFIED TIMESTAMP,
	NAME VARCHAR(255),
	SCHOOL_ID VARCHAR(255) NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE PERSON (
	DTYPE VARCHAR(31) NOT NULL,
	ID VARCHAR(255) NOT NULL,
	DATECREATED TIMESTAMP,
	DATEMODIFIED TIMESTAMP,
	ADDRESS_ID VARCHAR(255),
	DATEOFBIRTH TIMESTAMP,
	FIRSTNAME VARCHAR(255),
	GENRE VARCHAR(255),
	LASTNAME VARCHAR(255),
	NATIONALITY VARCHAR(255),
	NOTES VARCHAR(255),
	CALLNAME VARCHAR(255),
	CHILD_GROUP_ID VARCHAR(255),
	LEVEL VARCHAR(255),
	EMAIL VARCHAR(255),
	PROFESSION VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE PERSON_TELEPHONE (
	PERSON_ID VARCHAR(255) NOT NULL,
	TELEPHONES_ID VARCHAR(255) NOT NULL
);

CREATE TABLE SCHOOL (
	ID VARCHAR(255) NOT NULL,
	DATECREATED TIMESTAMP,
	DATEMODIFIED TIMESTAMP,
	NAME VARCHAR(255),
	PRIMARY KEY (ID)
);

CREATE TABLE TELEPHONE (
	ID VARCHAR(255) NOT NULL,
	DATECREATED TIMESTAMP,
	DATEMODIFIED TIMESTAMP,
	NUMBER VARCHAR(255) NOT NULL,
	TYPE VARCHAR(255) NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE TERM (
	ID VARCHAR(255) NOT NULL,
	DATECREATED TIMESTAMP,
	DATEMODIFIED TIMESTAMP,
	NAME VARCHAR(255),
	PRIMARY KEY (ID)
);

ALTER TABLE CHILD_GROUP
	ADD FOREIGN KEY (CLASSROOM_ID) 
	REFERENCES CLASSROOM (ID);

ALTER TABLE CHILD_GROUP
	ADD FOREIGN KEY (TERM_ID) 
	REFERENCES TERM (ID);



ALTER TABLE CLASSROOM
	ADD FOREIGN KEY (SCHOOL_ID) 
	REFERENCES SCHOOL (ID);



ALTER TABLE PERSON
	ADD FOREIGN KEY (ADDRESS_ID) 
	REFERENCES ADDRESS (ID);

ALTER TABLE PERSON
	ADD FOREIGN KEY (CHILD_GROUP_ID) 
	REFERENCES CHILD_GROUP (ID);



ALTER TABLE PERSON_TELEPHONE
	ADD FOREIGN KEY (PERSON_ID) 
	REFERENCES PERSON (ID);

ALTER TABLE PERSON_TELEPHONE
	ADD FOREIGN KEY (TELEPHONES_ID) 
	REFERENCES TELEPHONE (ID);



CREATE INDEX SYS_IDX_11480 ON CHILD_GROUP (CLASSROOM_ID);

CREATE INDEX SYS_IDX_11485 ON CHILD_GROUP (TERM_ID);

CREATE INDEX SYS_IDX_11490 ON CLASSROOM (SCHOOL_ID);

CREATE INDEX SYS_IDX_11494 ON PERSON (ADDRESS_ID);

CREATE INDEX SYS_IDX_11498 ON PERSON (CHILD_GROUP_ID);

CREATE INDEX SYS_IDX_11502 ON PERSON_TELEPHONE (TELEPHONES_ID);

CREATE INDEX SYS_IDX_11506 ON PERSON_TELEPHONE (PERSON_ID);

CREATE UNIQUE INDEX SYS_IDX_SYS_PK_11424_11425 ON ADDRESS (ID);

CREATE UNIQUE INDEX SYS_IDX_SYS_PK_11430_11431 ON CHILD_GROUP (ID);

CREATE UNIQUE INDEX SYS_IDX_SYS_PK_11437_11438 ON CHILD_GUARDIAN (ID);

CREATE UNIQUE INDEX SYS_IDX_SYS_PK_11443_11444 ON CLASSROOM (ID);

CREATE UNIQUE INDEX SYS_IDX_SYS_PK_11449_11450 ON PERSON (ID);

CREATE UNIQUE INDEX SYS_IDX_SYS_PK_11459_11460 ON SCHOOL (ID);

CREATE UNIQUE INDEX SYS_IDX_SYS_PK_11465_11466 ON TELEPHONE (ID);

CREATE UNIQUE INDEX SYS_IDX_SYS_PK_11471_11472 ON TERM (ID);

CREATE UNIQUE INDEX SYS_IDX_UK_DTXUUVKQ579EYQRPVLP998W1D_11474 ON CHILD_GUARDIAN (CHILD_ID,GUARDIAN_ID);

CREATE UNIQUE INDEX SYS_IDX_UK_IYA4Y7CORURTM2Y1LF7J8P11L_11477 ON PERSON_TELEPHONE (TELEPHONES_ID);
