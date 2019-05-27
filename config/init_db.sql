create table resumes
(
    uuid text not null
        constraint resume_pk
            primary key,
    full_name text not null
);

create table if not exists contacts
(
    resume_uuid text not null
        constraint fk_contacts_resume_uuid
            references resumes
            on update cascade on delete cascade,
    type text not null,
    value text not null
);

create unique index if not exists contacts_resume_uuid_type_uindex
    on contacts (resume_uuid, type);

create table if not exists sections
(
    resume_uuid text not null
        constraint fk_sections_resume_uuid
            references resumes
            on update cascade on delete cascade,
    type text not null,
    value text not null
);

create unique index if not exists sections_resume_uuid_type_uindex
    on sections (resume_uuid, type);