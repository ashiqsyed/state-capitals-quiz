package edu.uga.cs.statecapitalsquiz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class QuestionsPagerAdapter extends FragmentStateAdapter {
    private List<Question> questions;
    private int numQuestionsAnswered;
    private int numQuestionsCorrect;

    public QuestionsPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, List<Question> q, int numQuestionsAnswered, int numQuestionsCorrect) {
        super(fragmentManager, lifecycle);
        this.questions = q;
        this.numQuestionsAnswered = numQuestionsAnswered;
        this.numQuestionsCorrect = numQuestionsCorrect;
    }

    public Fragment createFragment(int numQuestion) {
        return QuestionFragment.newInstance(numQuestion, questions, numQuestionsAnswered, numQuestionsCorrect);
    }

    public int getItemCount() {
        return QuestionFragment.getNumOfQuestions();
    }

}
