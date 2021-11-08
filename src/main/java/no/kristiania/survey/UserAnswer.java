package no.kristiania.survey;

public class UserAnswer {
    private long userAnswerId;
    private long userId;
    private long surveyId;
    private long questionId;
    private long answerID;

    public long getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(long userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getAnswerID() {
        return answerID;
    }

    public void setAnswerID(long answerID) {
        this.answerID = answerID;
    }
}
