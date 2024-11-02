package edu.uga.cs.statecapitalsquiz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuestionsPagerAdapter extends FragmentStateAdapter {
    public QuestionsPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public Fragment createFragment(int numQuestion) {
        return QuestionFragment.newInstance(numQuestion);
    }

    public int getItemCount() {
        return QuestionFragment.getNumOfQuestions();
    }

}
