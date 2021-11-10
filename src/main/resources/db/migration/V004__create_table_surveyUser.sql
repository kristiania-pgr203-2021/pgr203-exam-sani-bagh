create table surveyUser(
    user_id serial,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    email varchar(200) not null,
    primary key(user_id)
)