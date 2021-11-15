create table answer (
                        answer_id serial,
                        answer_text varchar(200) NOT NULL,
                        question_id INT NOT NULL,
                        primary key (answer_id),
                        foreign key(question_id) references question(question_id)



)
