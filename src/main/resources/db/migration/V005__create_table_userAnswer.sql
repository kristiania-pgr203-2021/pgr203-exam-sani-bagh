create table userAnswer (
    userID int references user(user_id),
    questionID int references question(question_id),
    answerID int references answerAlternatives(answer_id),
    survey_id int references survey(survey_id);
)


