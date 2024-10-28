package edu.uga.cs.statecapitalsquiz;

public class Question {
    private String[] answers;
    private String correctAnswer;
    private long id;
    private String selectedAnswer;

    public Question(String[] answers, String correctAnswer, String selectedAnswer) {
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.id = -1; //negative until persisted in db
        this.selectedAnswer = selectedAnswer;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
