create table question
(
    question_id serial primary key,
    title       varchar(100) NOT NULL,
    text        varchar(200) NOT NULL,
    survey_id   INT          NOT NULL,
    answerOne   varchar(200),
    answerTwo   varchar(200),
    answerThree varchar(200),
    foreign key (survey_id) references survey (survey_id)
)













