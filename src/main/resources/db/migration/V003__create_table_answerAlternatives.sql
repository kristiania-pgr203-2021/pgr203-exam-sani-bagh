create table answerAlternatives (
    answer_id serial,
    answer_text varchar(200) not null,
    question_ID int,
     primary key(answer_id),
     constraint fk_question
                                foreign key(question_ID)
                                references question(question_id)
)


