package no.kristiania.survey;



public class Answer {
    private long answerId;
    private String answerText;
    private long question_ID;
    private long survey_ID;
    private long user_ID;


    public long getSurvey_ID() {
        return survey_ID;
    }

    public void setSurvey_ID(long survey_ID) {
        this.survey_ID = survey_ID;
    }

    public long getUser_ID(long user_id) {
        return user_ID;
    }

    public void setUser_ID(long user_ID) {
        this.user_ID = user_ID;
    }

    public long getQuestion_ID() {
        return question_ID;
    }

    public void setQuestion_ID(long question_ID) {
        this.question_ID = question_ID;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

}
