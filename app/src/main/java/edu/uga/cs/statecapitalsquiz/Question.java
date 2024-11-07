package edu.uga.cs.statecapitalsquiz;

import androidx.annotation.NonNull;

/**
 * The object reference of an entry in the questions table used to hold values until
 * they can be stored in the table.
 */
public class Question {
    private long id;
    private String state;
    private String capital;
    private String extra1;
    private String extra2;
    private String selectedAnswer;


    public Question() {
        this.id = -1; //negative until persisted in db
        this.state = null;
        this.capital = null;
        this.extra1 = null;
        this.extra2 = null;
        this.selectedAnswer = null;
    } //constructor

    public Question(String state, String capital, String extra1, String extra2) {
        this.id = -1; //negative until persisted in db
        this.state = state;
        this.capital = capital;
        this.extra1 = extra1;
        this.extra2 = extra2;
    } //constructor

    public long getId() { return id; }

    public void setId(long id) {this.id = id;}

    public String getState() {
        return state;
    }

    public void setState(String state) { this.state = state; }

    public String getCapital() { return capital; }

    public void setCapital(String capital) { this.capital = capital; }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(String extra1) { this.extra1 = extra1; }

    public String getExtra2() { return extra2; }

    public void setExtra2(String extra2) { this.extra2 = extra2; }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    @NonNull
    public String toString() {
       return id + ": " + state + ", " + capital + ", " + extra1 + ", " + extra2;
    }//toString

}//Question
