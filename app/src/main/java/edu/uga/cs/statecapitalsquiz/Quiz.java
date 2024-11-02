package edu.uga.cs.statecapitalsquiz;

import androidx.annotation.NonNull;

public class Quiz {

    private long id;
    private long question1;
    private long question2;
    private long question3;
    private long question4;
    private long question5;
    private long question6;
    private long score;
    private String date;


    public Quiz() {
        this.id = -1; //negative until persisted in db
        this.question1 = -1;
        this.question2 = -1;
        this.question3 = -1;
        this.question4 = -1;
        this.question5 = -1;
        this.question6 = -1;
        this.score = 0;
        this.date = null;
    } //constructor

    public Quiz(long question1, long question2, long question3, long question4,
                long question5, long question6, long score, String date) {
        this.id = -1; //negative until persisted in db
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
        this.question6 = question6;
        this.score = score;
        this.date = date;
    } //constructor


    public long getId() { return id; }

    public void setId(long id) {this.id = id;}

    public long getQuestion1() { return question1; }

    public void setQuestion1(long question1) { this.question1 = question1; }

    public long getQuestion2() { return question2; }

    public void setQuestion2(long question2) { this.question2 = question2; }

    public long getQuestion3() { return question3; }

    public void setQuestion3(long question3) { this.question3 = question3; }

    public long getQuestion4() { return question4; }

    public void setQuestion4(long question4) { this.question4 = question4; }

    public long getQuestion5() { return question5; }

    public void setQuestion5(long question5) { this.question5 = question5; }

    public long getQuestion6() { return question6; }

    public void setQuestion6(long question6) { this.question6 = question6; }

    public long getScore() { return score; }

    public void setScore(long score) { this.score = score; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }



    @NonNull
    public String toString() {
        return id + ": " + question1 + ", " + question2 + ", " + question3 + ", " + question4
                + question5 + ", " + question6 + ", " + score + ", " + date;
    }//toString
} //Quiz
