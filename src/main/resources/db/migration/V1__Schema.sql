CREATE TABLE address (
    id character varying(255) NOT NULL,
    datecreated timestamp without time zone,
    datemodified timestamp without time zone,
    city character varying(255),
    neighbourhood character varying(255),
    postalcode character varying(255),
    streetname character varying(255),
    streetnumber character varying(255)
);

CREATE TABLE cgroup (
    id character varying(255) NOT NULL,
    datecreated timestamp without time zone,
    datemodified timestamp without time zone,
    members integer,
    name character varying(255),
    department_id character varying(255) NOT NULL
);

CREATE TABLE department (
    id character varying(255) NOT NULL,
    datecreated timestamp without time zone,
    datemodified timestamp without time zone,
    name character varying(255),
    school_id character varying(255) NOT NULL
);

CREATE TABLE person (
    dtype character varying(31) NOT NULL,
    id character varying(255) NOT NULL,
    datecreated timestamp without time zone,
    datemodified timestamp without time zone,
    dateofbirth timestamp without time zone,
    firstname character varying(255),
    genre character varying(255),
    lastname character varying(255),
    nationality character varying(255),
    notes character varying(255),
    callname character varying(255),
    preschool_level character varying(255),
    email character varying(255),
    profession character varying(255),
    address_id character varying(255),
    group_id character varying(255)
);

CREATE TABLE person_telephone (
    person_id character varying(255) NOT NULL,
    telephones_id character varying(255) NOT NULL
);

CREATE TABLE relationship (
    id character varying(255) NOT NULL,
    datecreated timestamp without time zone,
    datemodified timestamp without time zone,
    notes character varying(255),
    pickup boolean NOT NULL,
    type character varying(255),
    child_id character varying(255) NOT NULL,
    guardian_id character varying(255) NOT NULL
);

CREATE TABLE school (
    id character varying(255) NOT NULL,
    datecreated timestamp without time zone,
    datemodified timestamp without time zone,
    name character varying(255)
);

CREATE TABLE telephone (
    id character varying(255) NOT NULL,
    datecreated timestamp without time zone,
    datemodified timestamp without time zone,
    num character varying(255) NOT NULL,
    type character varying(255) NOT NULL
);

ALTER TABLE ONLY address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);

ALTER TABLE ONLY cgroup
    ADD CONSTRAINT cgroup_pkey PRIMARY KEY (id);

ALTER TABLE ONLY department
    ADD CONSTRAINT department_pkey PRIMARY KEY (id);

ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);

ALTER TABLE ONLY relationship
    ADD CONSTRAINT relationship_pkey PRIMARY KEY (id);

ALTER TABLE ONLY school
    ADD CONSTRAINT school_pkey PRIMARY KEY (id);

ALTER TABLE ONLY telephone
    ADD CONSTRAINT telephone_pkey PRIMARY KEY (id);

ALTER TABLE ONLY person_telephone
    ADD CONSTRAINT uk_iya4y7corurtm2y1lf7j8p11l UNIQUE (telephones_id);

ALTER TABLE ONLY relationship
    ADD CONSTRAINT uk_ogykh9xrtq1g03vdv83jphp7s UNIQUE (guardian_id);

ALTER TABLE ONLY department
    ADD CONSTRAINT fk_1sceqtw632py1jkn13if2olft FOREIGN KEY (school_id) REFERENCES school(id);

ALTER TABLE ONLY person
    ADD CONSTRAINT fk_7j2gl0o2biopkiearvd8v7abe FOREIGN KEY (group_id) REFERENCES cgroup(id);

ALTER TABLE ONLY relationship
    ADD CONSTRAINT fk_bpwxw3lpld7mv03r30ryuy0wp FOREIGN KEY (child_id) REFERENCES person(id);

ALTER TABLE ONLY cgroup
    ADD CONSTRAINT fk_c95t3x7impgy3jwss4nu5uton FOREIGN KEY (department_id) REFERENCES department(id);

ALTER TABLE ONLY person_telephone
    ADD CONSTRAINT fk_iya4y7corurtm2y1lf7j8p11l FOREIGN KEY (telephones_id) REFERENCES telephone(id);

ALTER TABLE ONLY person_telephone
    ADD CONSTRAINT fk_k0nqv58h7r4nkbc7l7d5lioj2 FOREIGN KEY (person_id) REFERENCES person(id);

ALTER TABLE ONLY relationship
    ADD CONSTRAINT fk_ogykh9xrtq1g03vdv83jphp7s FOREIGN KEY (guardian_id) REFERENCES person(id);

ALTER TABLE ONLY person
    ADD CONSTRAINT fk_tagx64iglr1dxpalbgothv83r FOREIGN KEY (address_id) REFERENCES address(id);
