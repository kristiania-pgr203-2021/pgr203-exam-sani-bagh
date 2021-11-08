                  create table SurveyUser (
                      surveyUser_id serial,
                      first_name varchar(100) not null,
                      last_name varchar(100) not null,
                      email varchar(150) not null,
                      primary key (SurveyUser_id)
                  )