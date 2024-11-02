package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionFragment extends Fragment {
    private int questionNum;
    private static List<Question> questions;

    public QuestionFragment() {

    }

    public static QuestionFragment newInstance(int questionNum, List<Question> q) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt("questionNum", questionNum);
        fragment.setArguments(args);
        questions = q;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionNum = getArguments().getInt("questionNum");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.question);
        String text = "Question  " + (questionNum + 1) + "\n\nThe capital of "
            + questions.get(questionNum).getState() + " is: ";
        tv.setText(text);
        RadioButton c1 = view.findViewById(R.id.choice1);
        RadioButton c2 = view.findViewById(R.id.choice2);
        RadioButton c3 = view.findViewById(R.id.choice3);

        List<String> answers = new ArrayList<String>();
        answers.add(questions.get(questionNum).getCapital());
        answers.add(questions.get(questionNum).getExtra1());
        answers.add(questions.get(questionNum).getExtra2());

        Collections.shuffle(answers);
        c1.setText(answers.get(0));
        c2.setText(answers.get(1));
        c3.setText(answers.get(2));
    }

    public static int getNumOfQuestions() {
        if (questions != null) {
            return questions.size();
        } else {
            return 6;
        }
    }

}
