CREATE TABLE child_photo (
	id varchar(255) NOT NULL,
	datecreated timestamp NULL,
	datemodified timestamp NULL,
	content oid NULL,
	CONSTRAINT child_photo_pkey PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
) ;

alter table person add column photo_id varchar(255) references child_photo(id);