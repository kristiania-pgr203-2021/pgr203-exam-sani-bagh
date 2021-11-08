CREATE TABLE SURVEY
(
    survey_id SERIAL,
    title VARCHAR(100) NOT NULL,
    question_id integer ,
    answer_id integer,
    primary key (survey_id),
    foreign key (question_id) references question(question_id),
    foreign key (answer_id) references answerAlternatives(answer_id)
)