create table child_photo(
	id varchar(255) primary key,
	datecreated timestamp not null,
	datemodified timestamp null,
	content oid not null,
	md5 varchar(255) not null
);

alter table	person add column child_photo varchar(255) references child_photo(id);
