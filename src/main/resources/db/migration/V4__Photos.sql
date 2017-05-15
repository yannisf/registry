CREATE TABLE CHILD_PHOTO (
    id character varying(255) NOT NULL primary key,
    datecreated timestamp without time zone,
    datemodified timestamp without time zone,
    child character varying(255) NOT NULL REFERENCES person (id),
    MD5 character varying(32) NOT NULL,
    CONTENT oid not null
);
