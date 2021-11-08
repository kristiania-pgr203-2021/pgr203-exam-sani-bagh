create table answerAlternatives (
                                    answer_id serial,
                                    answer_text varchar(200) not null,
                                    question_id integer ,
                                    primary key (answer_id),
                                    foreign key(question_id) references question(question_id)

)


