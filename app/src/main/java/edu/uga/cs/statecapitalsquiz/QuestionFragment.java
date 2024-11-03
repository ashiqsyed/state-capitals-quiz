package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionFragment extends Fragment {
    private int questionNum;
    private static List<Question> questions;
    private String selectedAnswer;
    public final String TAG = "QuestionFragment";
    private static int numQuestionsAnswered;
    private static int numQuestionsCorrect;
    private TextView date;
    private TextView score;

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
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (numQuestionsAnswered == 5) {
//            return inflater.inflate(R.layout.fragment_results, container, false);
            Log.d(TAG, "check");
            LinearLayout layout = new LinearLayout(getActivity());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getActivity().getResources().getDisplayMetrics());
            layout.setOrientation(LinearLayout.VERTICAL);


            TextView resultsText = new TextView(getActivity());
            int font = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 28, getActivity().getResources().getDisplayMetrics());
            resultsText.setTextSize(font);
            resultsText.setText("Results");
            resultsText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(resultsText);

            int font2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getActivity().getResources().getDisplayMetrics());

            TextView scoreText = new TextView(getActivity());
            scoreText.setTextSize(font2);
            scoreText.setText("Your score is " + numQuestionsCorrect + "/6");
            layout.addView(scoreText);

            TextView dateText = new TextView(getActivity());
            dateText.setText("Quiz completed on " + Calendar.getInstance().getTime().toString());
            dateText.setTextSize(font2);
            layout.addView(dateText);
            return layout;

        }
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (questionNum < 6) {
            TextView tv = view.findViewById(R.id.question);
            String text = "Question  " + (questionNum + 1) + "\n\nThe capital of "
                    + questions.get(questionNum).getState() + " is: ";
            if (tv != null) {
                tv.setText(text);
            }
            date = view.findViewById(R.id.date);
            score = view.findViewById(R.id.score);

            RadioButton c1 = view.findViewById(R.id.choice1);
            RadioButton c2 = view.findViewById(R.id.choice2);
            RadioButton c3 = view.findViewById(R.id.choice3);

            List<String> answers = new ArrayList<String>();
            answers.add(questions.get(questionNum).getCapital());
            answers.add(questions.get(questionNum).getExtra1());
            answers.add(questions.get(questionNum).getExtra2());

            Collections.shuffle(answers);

            if (c1 != null && c2 != null && c3 != null) {
                c1.setText(answers.get(0));
                c2.setText(answers.get(1));
                c3.setText(answers.get(2));
            }

            RadioGroup radioGroup = view.findViewById(R.id.radioGroup2);
            if (radioGroup != null) {
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (i != -1) {
                            RadioButton button = view.findViewById(i);
                            if (button != null) {
                                selectedAnswer = button.getText().toString();
                            }
                        }
                    }
                });
        }


        Log.d(TAG, "questionNum: " + questionNum);




        }



    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "numQuestionsAnswered is " + numQuestionsAnswered);
        if (selectedAnswer.equals(questions.get(questionNum).getCapital())) {
            Log.d(TAG, selectedAnswer + " is the capital.");
            numQuestionsCorrect++;
            Log.d(TAG, "Score: " + numQuestionsCorrect);

        } else {
            Log.d(TAG, selectedAnswer + " is not the capital.");
        }
        numQuestionsAnswered++;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.d(TAG, "onDestroy() callback");
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.d(TAG, "onStop() callback");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Log.d(TAG, "onDestroyView() callback");
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        Log.d(TAG, "onDetach callback");
    }

}
