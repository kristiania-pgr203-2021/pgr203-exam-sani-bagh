create table question (
    question_id serial primary key,
    title varchar(100) not null,
    text varchar(500) not null
)