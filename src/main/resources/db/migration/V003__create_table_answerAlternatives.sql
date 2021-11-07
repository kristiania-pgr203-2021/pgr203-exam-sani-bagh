create table answeralternatives (
    answer_id serial,
    answer_text varchar(200) not null,
    question_id int,
     primary key(answer_id),
     constraint fk_question
                                foreign key(question_id)
                                references question(question_id)
)


