create table resumes
(
    uuid text not null
        constraint resume_pk
            primary key,
    full_name text not null
);

