package no.kristiania.survey;



public class Answer {
    private long answerId;
    private String answerText;
    private long question_ID;





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
