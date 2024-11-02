package edu.uga.cs.statecapitalsquiz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class QuestionsPagerAdapter extends FragmentStateAdapter {
    private List<Question> questions;

    public QuestionsPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle, List<Question> q) {
        super(fragmentManager, lifecycle);
        this.questions = q;
    }

    public Fragment createFragment(int numQuestion) {
        return QuestionFragment.newInstance(numQuestion, questions);
    }

    public int getItemCount() {
        return QuestionFragment.getNumOfQuestions();
    }

}
