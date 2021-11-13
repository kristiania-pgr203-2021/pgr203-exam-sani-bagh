create table answer (
                        answer_id serial,
                        answer_text varchar(200) NOT NULL,
                        question_id INT NOT NULL,
                        user_id INT NOT NULL,
                        survey_id INT NOT NULL,
                        primary key (answer_id),
                        foreign key(question_id) references question(question_id),
                        foreign key(survey_id) references survey(survey_id),
                        foreign key(user_id) references surveyU(user_id)


)
