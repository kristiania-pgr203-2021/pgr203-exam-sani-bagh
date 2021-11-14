package no.kristiania.http;

import no.kristiania.survey.Answer;
import no.kristiania.survey.AnswerDao;
import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListAllAnswersController implements HttpController {

    private QuestionDao questionDao;
    private AnswerDao answerDao;

    public ListAllAnswersController(AnswerDao answerDao, QuestionDao questionDao) {
        this.answerDao=answerDao;
        this.questionDao=questionDao;
    }


    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {

        List<Answer> answerList = answerDao.listAll();
        List<Question> questionList = questionDao.listAll();
        String messageBody = "";

        for (Question question : questionList){
            messageBody += "<h1>" + question.getTitle() + "</h1>" +
                    "<h3>" + question.getText() + "</h3>" +
                    "<ul>";
            for (Answer answer : answerList){
                if (question.getQuestionId() == answer.getQuestion_ID()){
                    messageBody += "<li>" + answer.getAnswerText() + "</li>";
                }
            }
            messageBody += " </ul>";
        }



        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }

}
/*
    List<Answer> answerList = answerDao.listAll();
    List<Question> questionList = questionDao.listAll();
    String responseText = "";

        for (Question q : questionList){
                responseText += "<h1>" + q.getTitle() + "</h1>" +
                "<h3>" + q.getText() + "</h3>" +
                "<ul>";
                for (Answer a : answerList){
                if (q.getId() == a.getQuestion_id()){
                responseText += "<li>" + a.getAnswer() + "</li>";
                }
                }
                responseText += " </ul>";
                }

 */
/*
        String messageBody = "";
        for (Answer answer : answerDao.listAll()) {
        long id = answer.getAnswerId();
        messageBody += "<h4>" + id + ":" + answer.getAnswerText() + "</h4>" + "\n" + "<h4> Question: " + answer.getQuestion_ID() + "</h4>";

 */
