create table answerAlternatives (
    answer_id serial primary key,
    answer_text varchar(200) not null,
    questionID int references question(question_id)
)