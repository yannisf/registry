CREATE TABLE public.child_photo (
	datecreated timestamp NOT NULL,
	datemodified timestamp NULL,
	content oid NOT NULL,
	md5 varchar(255) NOT NULL,
	child_id varchar(255) NOT NULL,
	CONSTRAINT child_photo_pkey PRIMARY KEY (child_id),
	CONSTRAINT fk_799347t3j6k4kn0oyaa8o4fpo FOREIGN KEY (child_id) REFERENCES person(id)
)
