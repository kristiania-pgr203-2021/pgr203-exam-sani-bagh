create table userAnswer (
    user_answer_id serial,
    user_id integer ,
    survey_id integer,
    question_id integer,
    answer_id integer,
    primary key (user_answer_id),
    foreign key (user_id) references surveyUser(user_id),
    foreign key (survey_id) references survey(survey_id),
    foreign key (question_id) references question(question_id),
    foreign key (answer_id) references answerAlternatives(answer_id)
)